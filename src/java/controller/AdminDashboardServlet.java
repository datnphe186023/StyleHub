package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.customer.Customer;
import model.customer.CustomerDAO;
import model.order.Order;
import model.order.OrderDAO;
import model.product.ProductDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminServlet", value = "/admin")
public class AdminDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        try{
            Customer customer = (Customer) session.getAttribute("account");
            if (customer.isAdmin()) {
                ProductDAO productDAO = new ProductDAO();
                CustomerDAO customerDAO = new CustomerDAO();
                OrderDAO orderDAO = new OrderDAO();
                int totalProduct = productDAO.countTotalProduct();
                int totalUser = customerDAO.getAllCustomer().size();
                int totalOrder = orderDAO.getNumberOfOrdersByMonth(java.time.LocalDate.now().toString());
                int totalLowStockProduct = productDAO.countLowStockProduct();
                List<Order> todayOrder = orderDAO.getOrdersByDate(java.time.LocalDate.now().toString());
                request.setAttribute("totalProduct", totalProduct);
                request.setAttribute("totalUser", totalUser);
                request.setAttribute("totalOrder", totalOrder);
                request.setAttribute("totalLowStockProduct", totalLowStockProduct);
                request.setAttribute("todayOrder", todayOrder);
                request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
            } else {
                response.sendRedirect("404.jsp");
            }
        }catch (Exception e){
            System.out.println("AdminServlet" + e);
            response.sendRedirect("404.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}