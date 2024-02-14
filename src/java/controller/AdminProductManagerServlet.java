package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.customer.Customer;
import model.customer.CustomerDAO;
import model.product.Product;
import model.product.ProductDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminProductManagerServlet", value = "/productmanager")
public class AdminProductManagerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            HttpSession session = request.getSession();
            Customer customer = (Customer) session.getAttribute("account");
            if (customer.isAdmin()) {
                if (action == null) {
                    ProductDAO productDAO = new ProductDAO();
                    List<Product> productList = productDAO.getAll();
                    List<Size> size = c.getSize();
                    List<Color> color = c.getColor();
                    List<Category> category = c.getCategory();
                    request.setAttribute("CategoryData", category);
                    request.setAttribute("ProductData", product);
                    request.setAttribute("SizeData", size);
                    request.setAttribute("ColorData", color);
                    request.getRequestDispatcher("/admin/product.jsp").forward(request, response);
                }

                if (action.equalsIgnoreCase("insert")) {
                    productDAO c = new productDAO();
                    List<Category> category = c.getCategory();
                    request.setAttribute("CategoryData", category);
                    request.getRequestDispatcher("/admin/productinsert.jsp").forward(request, response);
                }

                if (action.equalsIgnoreCase("insertcategory")) {
                    String name = request.getParameter("name");
                    productDAO dao = new productDAO();
                    Entity.Category c = dao.getCategoryByName(name);
                    if (c != null) {
                        request.setAttribute("error", name + " already");
                        request.getRequestDispatcher("admin/productinsert.jsp").forward(request, response);
                    } else {
                        dao.insertCategory(name);
                        request.getRequestDispatcher("productmanager?action=insert").forward(request, response);

                    }
                }

                if (action.equalsIgnoreCase("insertproduct")) {
                    String product_id = request.getParameter("product_id");
                    String category_id = request.getParameter("category_id");
                    String product_name = request.getParameter("product_name");
                    String product_price = request.getParameter("price");
                    String product_size = request.getParameter("size");
                    String product_color = request.getParameter("color");
                    String product_quantity = request.getParameter("quantity");
                    String product_img = "images/" + request.getParameter("product_img");
                    String product_describe = request.getParameter("describe");
                    int quantity = Integer.parseInt(product_quantity);
                    Float price = Float.parseFloat(product_price);
                    int cid = Integer.parseInt(category_id);
                    productDAO dao = new productDAO();
                    Category cate = new Category(cid);
                    String[] size_rw = product_size.split("\\s*,\\s*");
                    String[] color_rw = product_color.split("\\s*,\\s*");
                    int[] size = new int[size_rw.length];
                    int[] color = new int[color_rw.length];
                    List<Size> list = new ArrayList<>();
                    try {
                        for (int i = 0; i < size.length; i++) {
                            Size s = new Size(product_id, size_rw[i]);
                            list.add(s);
                        }
                    } catch (Exception e) {
                    }
                    // color
                    List<Color> list2 = new ArrayList<>();
                    try {
                        for (int i = 0; i < color.length; i++) {
                            Color s1 = new Color(product_id, color_rw[i]);
                            list2.add(s1);
                        }
                    } catch (Exception e) {
                    }

                    Product product = new Product();
                    product.setCate(cate);
                    product.setProduct_id(product_id);
                    product.setProduct_name(product_name);
                    product.setProduct_price(price);
                    product.setProduct_describe(product_describe);
                    product.setQuantity(quantity);
                    product.setImg(product_img);
                    product.setSize(list);
                    product.setColor(list2);
                    dao.insertProduct(product);
                    response.sendRedirect("productmanager?action=insert");
                }

                if (action.equalsIgnoreCase("deleteproduct")) {
                    String product_id = request.getParameter("product_id");
                    productDAO dao = new productDAO();
                    dao.ProductDelete(product_id);
                    response.sendRedirect("productmanager");
                }

                if (action.equalsIgnoreCase("updateproduct")) {
                    String product_id = request.getParameter("product_id");
                    String category_id = request.getParameter("category_id");
                    String product_name = request.getParameter("product_name");
                    String product_price = request.getParameter("product_price");
                    String product_size = request.getParameter("product_size");
                    String product_color = request.getParameter("product_color");
                    String product_quantity = request.getParameter("product_quantity");
                    String product_img = "images/" + request.getParameter("product_img");
                    String product_describe = request.getParameter("product_describe");
                    int quantity = Integer.parseInt(product_quantity);
                    Float price = Float.parseFloat(product_price);
                    int cid = Integer.parseInt(category_id);
                    productDAO dao = new productDAO();
                    Category cate = new Category(cid);
                    String[] size_rw = product_size.split("\\s*,\\s*");
                    String[] color_rw = product_color.split("\\s*,\\s*");
                    int[] size = new int[size_rw.length];
                    int[] color = new int[color_rw.length];
                    //size
                    List<Size> list = new ArrayList<>();
                    try {
                        for (int i = 0; i < size.length; i++) {
                            Size s = new Size(product_id, size_rw[i]);
                            list.add(s);
                        }
                    } catch (Exception e) {
                    }
                    // color
                    List<Color> list2 = new ArrayList<>();
                    try {
                        for (int i = 0; i < color.length; i++) {
                            Color s1 = new Color(product_id, color_rw[i]);
                            list2.add(s1);
                        }
                    } catch (Exception e) {
                    }

                    Product product = new Product();
                    product.setCate(cate);
                    product.setProduct_id(product_id);
                    product.setProduct_name(product_name);
                    product.setProduct_price(price);
                    product.setProduct_describe(product_describe);
                    product.setQuantity(quantity);
                    product.setImg(product_img);
                    product.setSize(list);
                    product.setColor(list2);
                    dao.insertProduct(product);
                    response.sendRedirect("productmanager");
                }
            } else {
                response.sendRedirect("user?action=login");
            }

        } catch (Exception e) {
            response.sendRedirect("404.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}