package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.customer.Customer;
import model.customer.CustomerDAO;

import java.io.IOException;

@WebServlet(name = "PasswordServlet", value = "/password")
public class PasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //this is for user to change their password
        HttpSession session = request.getSession(true);
        if (session.getAttribute("account") == null) {
            request.getRequestDispatcher("404.jsp").forward(request,response);
        }
        request.getRequestDispatcher("pass-change.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        HttpSession session = request.getSession(true);
        try {
            Customer customer = (Customer) session.getAttribute("account");
            CustomerDAO customerDAO = new CustomerDAO();
            Boolean result = customerDAO.updatePassword(customer.getId(), currentPassword, newPassword, confirmPassword);
            if(result){
                request.setAttribute("result", "Update Successful");
            } else {
                request.setAttribute("result", "Update Failed");
            }
            request.getRequestDispatcher("pass-change.jsp").forward(request,response);
        }catch (Exception e){
            response.sendRedirect("404.jsp");
        }
    }
}