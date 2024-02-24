package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.product.Product;
import model.product.ProductDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductServlet", value = "/product")
public class    ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //this is to get a specific product

        String productId_raw = request.getParameter("productId");
        //store the product-detail url in session to redirect back when in need (login for example)
        HttpSession session = request.getSession(true);
        String originalURL = request.getRequestURI();
        originalURL += "?productId=" + productId_raw;
        session.setAttribute("originalURL", originalURL);
        int productId;
        try{
            ProductDAO productDAO = new ProductDAO();
            productId = Integer.parseInt(productId_raw);
            Product product = productDAO.get(productId);
            List<String> imagesList = product.getImages();
            request.setAttribute("images", imagesList);
            request.setAttribute("product", product);
            request.getRequestDispatcher("product-detail.jsp").forward(request,response);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}