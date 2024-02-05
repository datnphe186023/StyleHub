package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.cart.Cart;
import model.cart.CartDAO;
import model.customer.Customer;
import model.order.OrderDAO;

import java.io.IOException;

@WebServlet(name = "CheckoutServlet", value = "/checkout")
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Cart cart = (Cart) session.getAttribute("cart");
        Customer customer = (Customer) session.getAttribute("account");
        OrderDAO orderDAO = new OrderDAO();
        orderDAO.addOrder(customer, cart);
        CartDAO cartDAO = new CartDAO();
        cartDAO.removeCart(customer.getId());
        session.removeAttribute("cart");
        session.setAttribute("size", 0);
        response.sendRedirect("all-product.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}