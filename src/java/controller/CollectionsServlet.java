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
        //this is paging
        int numberOfItems = productList.size();
        int numberOfItemPerPage = 16;
        int numberOfPage=numberOfItems/numberOfItemPerPage+(numberOfItems%numberOfItemPerPage==0?0:1);
        int start,end;
        String pageNumber_raw = request.getParameter("page");
        int page;
        try{
            page = Integer.parseInt(pageNumber_raw);
        }catch (Exception e){
            page = 1;
        }
        start = (page - 1) * numberOfItemPerPage;
        end = Math.min(page * numberOfItemPerPage, numberOfItems);
        List<Product> products = productDAO.getProductListForPage(productList, start, end);
        //set attribute the url for the sake of category
        StringBuilder collections = new StringBuilder();
        if (categories != null) {
            for (int i = 0; i < categories.length; i++) {
                collections.append("categories=" + categories[i]);
                if (i < categories.length - 1) {
                    collections.append("&");
                }
            }
        }

        //send product list after paging to jsp
        request.setAttribute("productList", products);
        request.setAttribute("collections", collections.toString());
        request.setAttribute("numberOfPage", numberOfPage);
        request.getRequestDispatcher("product-by-collections.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}