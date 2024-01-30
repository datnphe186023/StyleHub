package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.product.Product;
import model.product.ProductDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "CategoryServlet", value = "/category")
public class CategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categories_raw = request.getParameter("categories");
        List<String> categories = receiveArrayOfValues(categories_raw);
        ProductDAO productDAO = new ProductDAO();
        List<Product> productList = productDAO.getCategory(categories);
        request.setAttribute("productList", productList);
        request.getRequestDispatcher("product.jsp").forward(request,response);
    }

    public List<String> receiveArrayOfValues(String categories)
    {
        String[] categoryArray = categories.split(",");
        return Arrays.asList(categoryArray);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}