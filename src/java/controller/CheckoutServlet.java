package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.cart.Cart;
import model.cart.CartDAO;
import model.customer.Customer;
import model.order.OrderDAO;
import utils.Email;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CheckoutServlet", value = "/checkout")
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String discountRaw = request.getParameter("discount");
        String discountCode = request.getParameter("discountCode");
        String finalPriceRaw = request.getParameter("finalPrice");
        try {
            Customer customer = (Customer) session.getAttribute("account");
            List<String> addressListRaw = customer.getCustomerAddresses();
            List<String[]> addressList = new ArrayList<>();
            for (String addressRaw : addressListRaw) {
                addressList.add(addressRaw.split("%"));
            }
            double discount = Double.parseDouble(discountRaw);
            double finalPrice = Double.parseDouble(finalPriceRaw);
            request.setAttribute("discount", discount);
            request.setAttribute("finalPrice", finalPrice);
            request.setAttribute("addressList", addressList);
            request.setAttribute("discountCode", discountCode);
            request.getRequestDispatcher("checkOut.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("check out servlet get " + e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String payment = request.getParameter("payment");
        String discountCode = request.getParameter("discountCode");
        String finalPriceRaw = request.getParameter("finalPrice");
        try {
            HttpSession session = request.getSession(true);
            Cart cart = (Cart) session.getAttribute("cart");
            Customer customer = (Customer) session.getAttribute("account");
            OrderDAO orderDAO = new OrderDAO();
            String addressRaw = request.getParameter("addressId");
            int addressId;
            try {
                double finalPrice = Double.parseDouble(finalPriceRaw);
                addressId = Integer.parseInt(addressRaw);
                orderDAO.addOrder(customer, cart, addressId,discountCode, finalPrice);
                CartDAO cartDAO = new CartDAO();
                cartDAO.removeCart(customer.getId());
                session.removeAttribute("cart");
                session.setAttribute("size", 0);
                String subject = "[Style Hub] Thông báo xác nhận đặt hàng thành công!";
                String content = "Mã đơn hàng: " + orderDAO.getAll().size() + "\n" +
                        "Xin chào " + customer.getFullName() + ",\n" +
                        "\n" +
                        "Chúng tôi xin được thông báo rằng đơn hàng của bạn đã được xác nhận thành công.\n" +
                        "\n" +
                        "Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi tại đây hoặc gọi cho chúng tôi theo số [+84 705 410 751]!\n" +
                        "\n" +
                        "Cảm ơn bạn đã ủng hộ shop. Sự ủng hộ của bạn là động lực để chúng tôi ngày một hoàn thiện hơn.\n" +
                        "\n" +
                        "Chúc bạn một ngày tốt lành!";
                Email.sendEmail(customer.getEmail(), subject, content);
                request.setAttribute("orderId", orderDAO.getAll().size());
                request.getRequestDispatcher("order-complete.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                System.out.println("check out " + e);
            }
        } catch (Exception e) {
            System.out.println("check out final" + e);
        }
    }
}