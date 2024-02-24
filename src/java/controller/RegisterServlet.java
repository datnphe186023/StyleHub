package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.customer.Customer;
import model.customer.CustomerDAO;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("user");
        String pass = request.getParameter("pass");
        String fullName = request.getParameter("full_name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("birthday");
        String image = request.getParameter("image");
        try{
            CustomerDAO customerDAO = new CustomerDAO();
            int originNumberOfCustomer = customerDAO.getAllCustomer().size();
            Customer newCustomer = new Customer();
            newCustomer.setUsername(username);
            newCustomer.setPassword(pass);
            newCustomer.setFullName(fullName);
            newCustomer.setPhoneNumber(phone);
            newCustomer.setEmail(email);
            newCustomer.setGender(gender);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date birthday = dateFormat.parse(dob);
            newCustomer.setBirthday(birthday);
            newCustomer.setImage(image);
            newCustomer.setRole((short) 1);
            customerDAO.addCustomer(newCustomer);
            if (originNumberOfCustomer == customerDAO.getAllCustomer().size()) {
                request.setAttribute("error", "username is existed, please try again");
                request.getRequestDispatcher("register.jsp").forward(request,response);
            } else {
                response.sendRedirect("account");
            }
        }catch (Exception e){
            System.out.println("register servlet post " + e);
        }
    }
}