package controller;

import com.oracle.wls.shaded.org.apache.xpath.operations.Or;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.customer.Customer;
import model.customer.CustomerDAO;
import model.order.Order;
import model.order.OrderDAO;
import model.order.OrderDetail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AccountServlet", value = "/account")
public class AccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession(true);
        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = null;
        if (session.getAttribute("account") == null) {
            if (action=="register") {

            } else {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            customer = (Customer) session.getAttribute("account");
            if (customer.isAdmin()) {
                request.setAttribute("action", action);
                request.getRequestDispatcher("admin").forward(request,response);
            }
            if (action==null) {
                request.getRequestDispatcher("accountDetail.jsp").forward(request,response);
            } else if (action.equals("register")) {
                request.getRequestDispatcher("register.jsp").forward(request,response);
            } else if (action.equals("address-list")) {
                try{
                    List<String> addressListRaw = customer.getCustomerAddresses();
                    List<String[]> addressList = new ArrayList<>();
                    for (String addressRaw : addressListRaw) {
                        addressList.add(addressRaw.split("%"));
                    }
                    request.setAttribute("addressList", addressList);
                    request.getRequestDispatcher("accountDetail-addresses.jsp").forward(request,response);
                }catch (Exception e){
                    System.out.println(e);
                }
            } else if (action.equals("addAddress")) {
                    String receiverName = request.getParameter("receiver-name");
                    String receiverPhone = request.getParameter("receiver-phone");
                    String address = request.getParameter("address");
                    String finalAddress = receiverName + "%" +  receiverPhone + "%" + address;
                    try {
                        customerDAO.addAddress(customer.getId(), finalAddress);
                        customer = customerDAO.getCustomerById(customer.getId());
                        session.setAttribute("account", customer);
                        response.sendRedirect("account?action=address-list");
                    } catch (Exception e){
                        response.sendRedirect("account");
                    }
            } else if (action.equals("removeAddress")){
                String addressIdRaw = request.getParameter("addressId");
                int addressId;
                try{
                    addressId = Integer.parseInt(addressIdRaw);
                    customerDAO.removeAddress(addressId);
                    customer = customerDAO.getCustomerById(customer.getId());
                    session.setAttribute("account", customer);
                    response.sendRedirect("account?action=address-list");
                } catch (Exception e){
                    System.out.println(e);
                    response.sendRedirect("account");
                }
            } else if (action.equals("changePass")) {
                String currentPassword = request.getParameter("currentPassword");
                String newPassword = request.getParameter("newPassword");
                String confirmPassword = request.getParameter("confirmPassword");
                try {
                    boolean result = customerDAO.updatePassword(customer.getId(), currentPassword, newPassword, confirmPassword);
                    if(result){
                        request.setAttribute("passChangeResult", "Update Successful");
                    } else {
                        request.setAttribute("passChangeResult", "Update Failed");
                    }
                    request.getRequestDispatcher("accountDetail.jsp").forward(request,response);
                }catch (Exception e){
                    response.sendRedirect("account");
                }
            } else if (action.equals("updateInfo")) {
                String newName = request.getParameter("full-name");
                String newPhone = request.getParameter("phone");
                String newEmail = request.getParameter("email");
                String newGender = request.getParameter("gender");
                String newBirthday = request.getParameter("birthday");
                try {
                    Boolean result = customerDAO.updateCustomer(customer.getId(), newName, newPhone, newEmail, newGender, newBirthday);
                    Customer updatedCustomer = customerDAO.getCustomerById(customer.getId());
                    session.setAttribute("account", updatedCustomer);
                    if (result) {
                        request.setAttribute("infoUpdateResult", "Update Successful");
                    } else {
                        request.setAttribute("infoUpdateResult", "Update Failed");
                    }
                    request.getRequestDispatcher("accountDetail.jsp").forward(request, response);
                } catch (Exception e){
                    response.sendRedirect("account");
                }
            } else if (action.equals("order-list")) {
                try{
                    OrderDAO orderDAO = new OrderDAO();
                    List<Order> orderList = orderDAO.getOrderForCustomer(customer.getId());
                    List<OrderDetail>[] orderDetailList = new List[orderList.size()];
                    for (int i = 0; i < orderList.size(); i++){
                        orderDetailList[i] = orderDAO.getOrderDetailForOrder(orderList.get(i).getId());
                    }
                    request.setAttribute("orderDetail", orderDetailList);
                    request.setAttribute("orders", orderList);
                    request.getRequestDispatcher("accountDetail-orders.jsp").forward(request,response);
                } catch (Exception e){
                    System.out.println(e);
                    response.sendRedirect("account");
                }
            } else if (action.equals("cancelOrder")) {
                String orderIdRaw = request.getParameter("orderId");
                int orderId;
                try{
                    orderId = Integer.parseInt(orderIdRaw);
                    OrderDAO orderDAO = new OrderDAO();
                    orderDAO.cancelOrderForUser(orderId);
                    response.sendRedirect("account?action=order-list");
                }catch (Exception e){
                    System.out.println(e);
                    response.sendRedirect("account");
                }
            }
        }
    }

    @Override
    //this is for updating account information
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}