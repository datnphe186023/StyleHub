package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.customer.Customer;
import model.customer.CustomerDAO;

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
                        request.getRequestDispatcher("404.jsp").forward(request,response);
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
                    request.getRequestDispatcher("404.jsp").forward(request,response);
                }
            }
        }
    }

    @Override
    //this is for updating account information
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newName = request.getParameter("full-name");
        String newPhone = request.getParameter("phone");
        String newEmail = request.getParameter("email");
        String newGender = request.getParameter("gender");
        String newBirthday = request.getParameter("birthday");
        CustomerDAO customerDAO = new CustomerDAO();
        HttpSession session = request.getSession(true);
        try {
            Customer customer = (Customer) session.getAttribute("account");
            Boolean result = customerDAO.updateCustomer(customer.getId(), newName, newPhone, newEmail, newGender, newBirthday);
            Customer updatedCustomer = customerDAO.getCustomerById(customer.getId());
            session.setAttribute("account", updatedCustomer);
            if (result) {
                request.setAttribute("result", "Update Successful");
            } else {
                request.setAttribute("result", "Update Failed");
            }
            request.getRequestDispatcher("accountDetail.jsp").forward(request, response);
        } catch (Exception e){
            response.sendRedirect("404.jsp");
        }
    }
}