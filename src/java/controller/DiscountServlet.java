package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.cart.Cart;
import model.product.ProductDAO;

import java.io.IOException;

@WebServlet(name = "DiscountServlet", value = "/discount")
public class DiscountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String discountCode = request.getParameter("discountCode");
        try{
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            ProductDAO productDAO = new ProductDAO();
            double discount = productDAO.getDiscount(discountCode) * cart.getTotalMoney();
            double finalPrice = cart.getTotalMoney() - discount;
            request.setAttribute("discountCode", discountCode);
            request.setAttribute("discount", discount);
            request.setAttribute("finalPrice", finalPrice);
            request.getRequestDispatcher("cart").forward(request,response);
        }catch (Exception e){
            System.out.println("discount servlet " + e);
        }
    }
}