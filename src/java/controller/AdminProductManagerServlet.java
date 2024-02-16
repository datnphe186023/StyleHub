package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.customer.Customer;
import model.product.Product;
import model.product.ProductDAO;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "AdminProductManagerServlet", value = "/productmanager")
public class AdminProductManagerServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            HttpSession session = request.getSession();
            Customer customer = (Customer) session.getAttribute("account");
            if (customer.isAdmin()) {
                if (action == null) {
                    ProductDAO productDAO = new ProductDAO();
                    List<Product> productList = productDAO.getAll();
                    List<String> category = productDAO.getCategory();
                    request.setAttribute("CategoryData", category);
                    request.setAttribute("ProductData", productList);
                    request.getRequestDispatcher("/admin/product.jsp").forward(request, response);
                } else if (action.equalsIgnoreCase("insert")) {
                    ProductDAO productDAO = new ProductDAO();
                    List<String> category = productDAO.getCategory();
                    request.setAttribute("CategoryData", category);
                    request.getRequestDispatcher("/admin/productinsert.jsp").forward(request, response);
                } else if (action.equalsIgnoreCase("insertcategory")) {
                    String name = request.getParameter("name");
                    ProductDAO productDAO = new ProductDAO();
                    String category = productDAO.getCategoryByName(name);
                    if (category != null) {
                        request.setAttribute("error", name + " already");
                        request.getRequestDispatcher("admin/productinsert.jsp").forward(request, response);
                    } else {
                        productDAO.addCategory(name);
                        request.getRequestDispatcher("productmanager?action=insert").forward(request, response);
                    }
                } else if (action.equalsIgnoreCase("insertproduct")) {
                    String[] category = request.getParameterValues("category");
                    String productTitle = request.getParameter("product_name");
                    String inPriceRaw = request.getParameter("inPrice");
                    String outPriceRaw = request.getParameter("outPrice");
                    String sizeRaw = request.getParameter("size");
                    String stockRaw = request.getParameter("stock");
                    String image = request.getParameter("product_img");
                    String description = request.getParameter("description");
                    try {
                        double inPrice = Double.parseDouble(inPriceRaw);
                        double outPrice = Double.parseDouble(outPriceRaw);
                        ProductDAO productDAO = new ProductDAO();
                        productDAO.addProduct(productTitle, inPrice, outPrice, description);
                        String[] sizeRawString = sizeRaw.split("\\s*,\\s*");
                        String[] stockRawString = stockRaw.split("\\s*,\\s*");
                        int[] size = Arrays.stream(sizeRawString).mapToInt(Integer::parseInt).toArray();
                        int[] stock = Arrays.stream(stockRawString).mapToInt(Integer::parseInt).toArray();
                        List<Product> productList = productDAO.getAll();
                        productDAO.updateCategoryForProduct(productList.get(productList.size() - 1).getId(), category);
                        for (int i = 0; i < size.length; i++) {
                            productDAO.addItem(productList.get(productList.size() - 1).getId(), size[i], stock[i]);
                        }
                        productList = productDAO.getAll();
                        productDAO.updateItem(productList.get(productList.size() - 1).getId(), image);
                    } catch (Exception e) {
                        System.out.println("insertproduct " + e);
                    }
                    response.sendRedirect("productmanager?action=insert");
                } else if (action.equalsIgnoreCase("deleteproduct")) {
                    String productIdRaw = request.getParameter("productId");
                    ProductDAO productDAO = new ProductDAO();
                    int productId;
                    try {
                        productId = Integer.parseInt(productIdRaw);
                        productDAO.deleteProduct(productId);
                    } catch (Exception e) {
                        System.out.println("delete product servlet " + e);
                    }
                    response.sendRedirect("productmanager");
                } else if (action.equalsIgnoreCase("updateproduct")) {
                    String productIdRaw = request.getParameter("productId");
                    String[] category = request.getParameterValues("category");
                    String productTitle = request.getParameter("product_name");
                    String inPriceRaw = request.getParameter("inPrice");
                    String outPriceRaw = request.getParameter("outPrice");
                    String[] sizeRaw = request.getParameterValues("size");
                    String[] stockRaw = request.getParameterValues("stock");
                    String image = request.getParameter("image");
                    String description = request.getParameter("description");
                    try {
                        double inPrice = Double.parseDouble(inPriceRaw);
                        double outPrice = Double.parseDouble(outPriceRaw);
                        ProductDAO productDAO = new ProductDAO();
                        int[] size = Arrays.stream(sizeRaw).mapToInt(Integer::parseInt).toArray();
                        int[] stock = Arrays.stream(stockRaw).mapToInt(Integer::parseInt).toArray();
                        int productId = Integer.parseInt(productIdRaw);
                        productDAO.updateProduct(productId, productTitle, inPrice, outPrice, description);
                        productDAO.updateCategoryForProduct(productId, category);
                        for (int i = 0; i < size.length; i++) {
                            productDAO.updateItem(productId, size[i], stock[i]);
                        }
                        productDAO.updateItem(productId, image);
                    } catch (Exception e) {
                        System.out.println("update product servlet " + e);
                    }
                    response.sendRedirect("productmanager");
                }
            } else {
                response.sendRedirect("404.jsp");
            }

        } catch (Exception e) {
            System.out.println("product manager " + e);
            response.sendRedirect("404.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}