<%-- 
    Document   : accountDetail
    Created on : Jan 12, 2024, 10:29:33 PM
    Author     : datng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <%@include file="header.jsp" %>
        <div>
            <h1>THÔNG TIN TÀI KHOẢN</h1>
            <label for="last-name">Họ*</label>
            <input type="text" name="last-name" id="last-name" placeholder="Nhập họ" required><br/>
            <label for="first-name">Tên*</label>
            <input type="text" name="first-name" id="first-name" placeholder="Nhập tên" required><br/>
            <label for="phone">Số điện thoại</label>
            <input type="text" name="phone" id="phone" value="${requestScope.phone}"><br/>
            <label for="email">Email</label>
            <input type="text" name="email" id="email" placeholder="Chưa có email"><br/>
            Giới tính* <label>
                <input type="radio" name="gender" value="male" required> Nam
            </label>
            <label>
                <input type="radio" name="gender" value="female"> Nữ
            </label><br/>
            <label for="birthday">Birthday*</label>
        <input type="date" id="birthday" name="birthday" required><br/>
        
        <label for="changePassword">Đổi mật khẩu</label>
        <input type="checkbox" id="changePassword" name="changePassword" onclick="togglePasswordSection()">
        
        <div class="password-section" style="display: none">
            <label for="currentPassword">Mật khẩu hiện tại*</label>
            <input type="password" id="currentPassword" name="currentPassword" placeholder="Mật khẩu hiện tại*">
            
            <label for="newPassword">Mật khẩu mới*</label>
            <input type="password" id="newPassword" name="newPassword" placeholder="Mật khẩu mới*">
            
            <label for="confirmPassword">Nhập lại mật khẩu mới*</label>
            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Nhập lại mật khẩu mới*">
        </div>
        
        <div>
            <button type="submit">Lưu thay đổi</button>
        </div>
        
        <script>
            function togglePasswordSection() {
                var passwordSection = document.querySelector('.password-section');
                passwordSection.style.display = document.getElementById('changePassword').checked ? 'block' : 'none';
            }
        </script>
        
        </div>

        <%@include file="footer.jsp" %>
    </body>
</html>
