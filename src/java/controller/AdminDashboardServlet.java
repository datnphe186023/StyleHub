package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.customer.Customer;
import model.customer.CustomerDAO;
import model.order.Order;
import model.order.OrderDAO;
import model.order.OrderDetail;
import model.order.OrderDetailDAO;
import model.product.ProductDAO;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AdminServlet", value = "/admin")
public class AdminDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        try{
            Customer customer = (Customer) session.getAttribute("account");
            if (customer.isAdmin()) {
                ProductDAO productDAO = new ProductDAO();
                CustomerDAO customerDAO = new CustomerDAO();
                OrderDAO orderDAO = new OrderDAO();
                int totalProduct = productDAO.countTotalProduct();
                int totalUser = customerDAO.getAllCustomer().size();
                int totalOrder = orderDAO.getNumberOfOrdersByMonth(java.time.LocalDate.now().toString());
                LocalDate currentDate = LocalDate.now();
                int totalLowStockProduct = productDAO.countLowStockProduct();
                List<Order> todayOrder = orderDAO.getOrdersByDate(java.time.LocalDate.now().toString());
                List<Double> dailyRevenue = new ArrayList<>();
                List<Double> dailyProfit = new ArrayList<>();
                for (int i = 0; i < 30; i++) {
                    LocalDate date = currentDate.minusDays(i);
                    List<Order> ordersForDate = orderDAO.getOrdersByDate(date.toString());
                    double dailyTotal = 0.0, profit = 0.0;
                    for (Order order : ordersForDate) {
                        dailyTotal += order.getTotalPrice();
                        profit += order.calculateProfit();
                    }
                    dailyProfit.add(profit);
                    dailyRevenue.add(dailyTotal);
                }
                LocalDate lastMonth = currentDate.minusMonths(1);
                List<Order> thisMonthOrders = orderDAO.getOrdersByMonth(currentDate.getYear(), currentDate.getMonthValue());
                List<Order> lastMonthOrders = orderDAO.getOrdersByMonth(lastMonth.getYear(), lastMonth.getMonthValue());
                double thisMonthRevenue = 0.0;
                double thisMonthProfit = 0.0;
                for (Order order : thisMonthOrders) {
                    thisMonthRevenue += order.getTotalPrice();
                    thisMonthProfit += order.calculateProfit();
                }
                double lastMonthRevenue = 0.0;
                double lastMonthProfit = 0.0;
                for (Order order : lastMonthOrders) {
                    lastMonthRevenue += order.getTotalPrice();
                    lastMonthProfit += order.calculateProfit();
                }
                request.setAttribute("thisMonthRevenue", thisMonthRevenue);
                request.setAttribute("thisMonthProfit", thisMonthProfit);
                request.setAttribute("lastMonthRevenue", lastMonthRevenue);
                request.setAttribute("lastMonthProfit", lastMonthProfit);
                request.setAttribute("dailyRevenue", dailyRevenue);
                request.setAttribute("dailyProfit", dailyProfit);
                request.setAttribute("totalProduct", totalProduct);
                request.setAttribute("totalUser", totalUser);
                request.setAttribute("totalOrder", totalOrder);
                request.setAttribute("totalLowStockProduct", totalLowStockProduct);
                request.setAttribute("todayOrder", todayOrder);
                request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
            } else {
                response.sendRedirect("404.jsp");
            }
        }catch (Exception e){
            System.out.println("AdminServlet" + e);
            response.sendRedirect("404.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}