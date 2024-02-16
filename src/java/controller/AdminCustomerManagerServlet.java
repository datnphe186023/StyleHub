package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.customer.Customer;
import model.customer.CustomerDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminCustomerManagerServlet", value = "/customermanager")
public class AdminCustomerManagerServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        try{
            HttpSession session = request.getSession();
            Customer customer = (Customer) session.getAttribute("account");
            String action = request.getParameter("action");
            if (customer.isAdmin()) {
                if (action == null) {
                    CustomerDAO customerDAO = new CustomerDAO();
                    List<Customer> customerList = customerDAO.getAllCustomer();
                    request.setAttribute("customerList", customerList);
                    request.getRequestDispatcher("admin/customer.jsp").forward(request, response);
                }
                else if (action.equals("update")) {
                    String customerIdRaw = request.getParameter("customerId");
                    String roleRaw = request.getParameter("permission");
                    int id = Integer.parseInt(customerIdRaw);
                    int role = Integer.parseInt(roleRaw);
                    CustomerDAO customerDAO = new CustomerDAO();
                    customerDAO.setAdmin(id, role);
                    response.sendRedirect("customermanager");
                }
            } else {
                response.sendRedirect("404.jsp");
            }
        }catch (Exception e){
            System.out.println("customer manager " + e);
            response.sendRedirect("404.jsp");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}