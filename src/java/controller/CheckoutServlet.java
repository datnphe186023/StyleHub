package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.cart.Cart;
import model.cart.CartDAO;
import model.customer.Customer;
import model.order.OrderDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CheckoutServlet", value = "/checkout")
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            Customer customer = (Customer) session.getAttribute("account");
            List<String> addressListRaw = customer.getCustomerAddresses();
            List<String[]> addressList = new ArrayList<>();
            for (String addressRaw : addressListRaw) {
                addressList.add(addressRaw.split("%"));
            }
            request.setAttribute("addressList", addressList);
            request.getRequestDispatcher("checkOut.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("check out servlet get " + e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String payment = request.getParameter("payment");
        try {
            HttpSession session = request.getSession(true);
            Cart cart = (Cart) session.getAttribute("cart");
            Customer customer = (Customer) session.getAttribute("account");
            OrderDAO orderDAO = new OrderDAO();
            String addressRaw = request.getParameter("addressId");
            int addressId;
            try {
                addressId = Integer.parseInt(addressRaw);
                orderDAO.addOrder(customer, cart, addressId);
                CartDAO cartDAO = new CartDAO();
                cartDAO.removeCart(customer.getId());
                session.removeAttribute("cart");
                session.setAttribute("size", 0);
                if (payment.equals("Momo")) {
                    request.setAttribute("orderId", orderDAO.getAll().size());
                    request.setAttribute("total", cart.getTotalMoney());
                    request.getRequestDispatcher("qrcode.jsp").forward(request, response);
                } else if (payment.equals("COD")) {
                    request.getRequestDispatcher("order-complete.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                System.out.println("check out " + e);
            }
        } catch (Exception e) {
            System.out.println("check out final" + e);
        }
    }
}