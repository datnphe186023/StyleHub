package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.product.Product;
import model.product.ProductDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryServlet", value = "/collections")
public class CollectionsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] categories = request.getParameterValues("categories");
        ProductDAO productDAO = new ProductDAO();
        List<Product> productList = productDAO.getProductByCategory(categories);
        request.setAttribute("productList", productList);
        request.getRequestDispatcher("product-by-collections.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}