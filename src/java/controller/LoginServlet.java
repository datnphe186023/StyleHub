package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.cart.Cart;
import model.cart.CartDAO;
import model.customer.Customer;
import model.customer.CustomerDAO;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (session.getAttribute("cart") != null){
            CartDAO cartDAO = new CartDAO();
            cartDAO.addCart((Cart) session.getAttribute("cart"), (Customer) session.getAttribute("account"));
        }

        session.removeAttribute("account");
        session.removeAttribute("cart");
        session.removeAttribute("size");
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        CustomerDAO customerDAO = new CustomerDAO();
        try {
            Customer customer = customerDAO.getAccount(user, pass);
            if (customer == null) {
                String error = "username " + user + " doesn't exist";
                request.setAttribute("error", error);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                HttpSession session = request.getSession(true);
                session.setAttribute("account", customer);
                CartDAO cartDAO = new CartDAO();
                Cart cart = cartDAO.get(customer.getId());
                cartDAO.removeCart(customer.getId());
                session.setAttribute("cart", cart);
                session.setAttribute("size", cart.getItems().size());
                response.sendRedirect("index.jsp");
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}