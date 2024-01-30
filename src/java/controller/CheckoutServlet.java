package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.cart.Cart;
import model.customer.Customer;
import model.order.OrderDAO;

import java.io.IOException;

@WebServlet(name = "CheckoutServlet", value = "/checkout")
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Cart cart = null;
        Object o = session.getAttribute("cart");
        if (o != null) {
            cart = (Cart) o;
        } else {
            cart = new Cart();
        }

        Customer customer = null;
        Object a = session.getAttribute("account");
        if (a != null) {
            customer = (Customer) a;
            OrderDAO orderDAO = new OrderDAO();
            orderDAO.addOrder(customer, cart);
            session.removeAttribute("cart");
            session.setAttribute("size", 0);
            response.sendRedirect("product.jsp");
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Cart cart = null;
        Object o = session.getAttribute("cart");
        if (o != null) {
            cart = (Cart) o;
        } else {
            cart = new Cart();
        }

        Customer customer = null;
        Object a = session.getAttribute("account");
        if (a != null) {
            customer = (Customer) a;
            OrderDAO orderDAO = new OrderDAO();
            orderDAO.addOrder(customer, cart);
            session.removeAttribute("cart");
            session.setAttribute("size", 0);
            response.sendRedirect("product.jsp");
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}