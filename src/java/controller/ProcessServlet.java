package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.cart.Cart;
import model.item.Item;
import model.product.Product;
import model.product.ProductDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProcessServlet", value = "/process")
public class ProcessServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Cart cart = null;
        Object o = session.getAttribute("cart");
        if (o != null) {
            cart = (Cart) o;
        } else {
            cart = new Cart();
        }
        String num_raw = request.getParameter("num").trim();
        String id_raw = request.getParameter("id").trim();
        String psize_raw = request.getParameter("psize");
        String currentQuantityRaw = request.getParameter("currentQuantity");
        int id, amount, psize, currentQuantity;
        try {
            id = Integer.parseInt(id_raw);
            amount = Integer.parseInt(num_raw);
            psize = Integer.parseInt(psize_raw);
            currentQuantity = Integer.parseInt(currentQuantityRaw);
            ProductDAO productDAO = new ProductDAO();
            if (currentQuantity + amount <= productDAO.getStockForSize(id, psize)) {
                if (cart.getQuantity(id, psize) + amount == 0) {
                    cart.removeItem(id, psize);
                    int size = (int) session.getAttribute("size");
                    session.setAttribute("size", size - 1);
                } else {
                    Product product = productDAO.get(id);
                    double price = product.getOutPrice();
                    Item item = new Item(product, amount, price, psize);
                    cart.addItem(item);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
        request.getRequestDispatcher("cart").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}