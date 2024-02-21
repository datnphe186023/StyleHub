package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.customer.Customer;
import model.order.Order;
import model.order.OrderDAO;
import model.order.OrderDetail;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminOrderManagerServlet", value = "/ordermanager")
public class AdminOrderManagerServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        try{
            HttpSession session = request.getSession();
            Customer customer = (Customer) session.getAttribute("account");
            if (customer.isAdmin()){
                String action = request.getParameter("action");
                if (action == null) {
                    OrderDAO orderDAO = new OrderDAO();
                    List<Order> orderList = orderDAO.getAll();
                    request.setAttribute("order", orderList);
                    request.getRequestDispatcher("admin/order.jsp").forward(request,response);
                } else if (action.equals("showdetail")) {
                    try{
                        OrderDAO orderDAO = new OrderDAO();
                        String orderIdRaw = request.getParameter("orderId");
                        int orderId = Integer.parseInt(orderIdRaw);
                        List<OrderDetail> orderDetailList = orderDAO.getOrderDetailForOrder(orderId);
                        request.setAttribute("detail", orderDetailList);
                        request.getRequestDispatcher("admin/orderdetail.jsp").forward(request,response);
                    }catch (Exception e){
                        System.out.println("order list admin " + e);
                        response.sendRedirect("ordermanager");
                    }
                } else if (action.equals("updateorder")) {
                    try{
                        String orderIdRaw = request.getParameter("orderId");
                        String status = request.getParameter("status");
                        int orderId = Integer.parseInt(orderIdRaw);
                        if (status.equals("Shipped")) {
                            OrderDAO orderDAO = new OrderDAO();
                            orderDAO.updateOrderForAdmin(orderId);
                        } else if (status.equals("Canceled")) {
                            OrderDAO orderDAO = new OrderDAO();
                            orderDAO.cancelOrderForAdmin(orderId);
                        }
                        response.sendRedirect("admin");
                    }catch (Exception e){
                        System.out.println("update order servlet " + e);
                    }
                }
            } else {
                response.sendRedirect("404.jsp");
            }
        }catch (Exception e){
            System.out.println("order manager servlet" + e);
            response.sendRedirect("404.jsp");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
}