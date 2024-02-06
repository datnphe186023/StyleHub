package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.customer.Customer;
import model.customer.CustomerDAO;

import java.io.IOException;

@WebServlet(name = "AccountServlet", value = "/account")
public class AccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession(true);
        Customer customer = null;
        if (session.getAttribute("account") == null) {
            if (action=="register") {

            } else {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            customer = (Customer) session.getAttribute("account");
            if (customer.isAdmin()) {
                request.setAttribute("action", action);
                request.getRequestDispatcher("admin").forward(request,response);
            }
            if (action==null) {
                request.getRequestDispatcher("accountDetail.jsp").forward(request,response);
            } else if (action.equals("register")) {
                request.getRequestDispatcher("register.jsp").forward(request,response);
            } else if (action.equals("address-list")) {
                request.getRequestDispatcher("addresses").forward(request,response);
            }
        }
    }

    @Override
    //this is for updating account information
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newName = request.getParameter("full-name");
        String newPhone = request.getParameter("phone");
        String newEmail = request.getParameter("email");
        String newGender = request.getParameter("gender");
        String newBirthday = request.getParameter("birthday");
        CustomerDAO customerDAO = new CustomerDAO();
        HttpSession session = request.getSession(true);
        try {
            Customer customer = (Customer) session.getAttribute("account");
            Boolean result = customerDAO.updateCustomer(customer.getId(), newName, newPhone, newEmail, newGender, newBirthday);
            Customer updatedCustomer = customerDAO.getCustomerById(customer.getId());
            session.setAttribute("account", updatedCustomer);
            if (result) {
                request.setAttribute("result", "Update Successful");
            } else {
                request.setAttribute("result", "Update Failed");
            }
            request.getRequestDispatcher("accountDetail.jsp").forward(request, response);
        } catch (Exception e){
            response.sendRedirect("404.jsp");
        }
    }
}