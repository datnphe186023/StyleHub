package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.product.Product;
import model.product.ProductDAO;

import java.io.IOException;
import java.util.List;
import java.util.zip.ZipError;

@WebServlet(name = "SaleServlet", value = "/sale")
public class SaleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String discountRaw = request.getParameter("discount");
        ProductDAO productDAO = new ProductDAO();
        try{
            double discount = Double.parseDouble(discountRaw);
            List<Product> productList =  productDAO.getSale(discount);
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

            request.setAttribute("productList", products);
            request.setAttribute("discount", discountRaw);
            request.setAttribute("numberOfPage", numberOfPage);
            request.getRequestDispatcher("sale.jsp").forward(request,response);
        }catch (Exception e){
            System.out.println("get sale servlet " + e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}