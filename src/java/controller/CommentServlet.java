package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.customer.Customer;
import model.customer.CustomerDAO;

import java.io.IOException;

@WebServlet(name = "CommentServlet", value = "/comment")
public class CommentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String comment = request.getParameter("comment");
        String productIdRaw = request.getParameter("productId");
        String orderIdRaw = request.getParameter("orderId");
        String reviewRaw = request.getParameter("review");
        try {
            int productId = Integer.parseInt(productIdRaw);
            int review = Integer.parseInt(reviewRaw);
            int orderId = Integer.parseInt(orderIdRaw);
            CustomerDAO customerDAO = new CustomerDAO();
            HttpSession session = request.getSession();
            Customer customer = null;
            if (session.getAttribute("account") == null) {
                response.sendRedirect("account");
            } else {
                customer = (Customer) session.getAttribute("account");
                customerDAO.addComment(customer.getId(), productId, review, comment, orderId);
                response.sendRedirect("account?action=order-list");
            }
        } catch (Exception e) {
            System.out.println("add comment " + e);
        }
    }
}