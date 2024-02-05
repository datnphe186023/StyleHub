package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Cart", value = "/cart")
//this servlet is used to check if the user has logined or not,
// if they do, let them add the item into their cart,
// if not, forward them to login page
public class CartLoginCheck extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //when user add product to cart, force them to login first
        HttpSession session = request.getSession(true);
        if(session.getAttribute("account") == null){
            response.sendRedirect("login.jsp");
        } else {
            String amount_raw = request.getParameter("quantity");
            String productId_raw = request.getParameter("id");
            String psize_raw = request.getParameter("psize");
            response.sendRedirect("buy?id=" + productId_raw + "&psize=" + psize_raw + "&quantity=" + amount_raw);
        }
    }
}