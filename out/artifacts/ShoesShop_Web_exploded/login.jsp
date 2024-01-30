<%-- 
    Document   : login
    Created on : Jan 12, 2024, 8:29:02 PM
    Author     : datng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div>
            
            <div>
            <h1>Đăng nhập</h1>
            <h1><a href="<c:url value="/register.jsp"/>">Đăng ký</a></h1>
            </div>

            <form action="login">
                <label for="user">Số điện thoại/Email</label> <br/>
                <input type="text" name="user" id="user" placeholder="Nhập số điện thoại hoặc email" required autofocus><br/>
                <label for="pass">Mật khẩu</label><br/>
                <input type="password" name="pass" id="pass" placeholder="Nhập mật khẩu" required>

                <div>
                    <input name="remember" value="1" type="checkbox">
                    <label for="exampleCheck1">Remember me</label>
                </div>
                
                <button type="submit">Đăng nhập</button>
            </form>
            <h5>${requestScope.error}</h5>
        </div>
    </body>
</html>
