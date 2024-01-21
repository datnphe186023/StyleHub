<%-- 
    Document   : register
    Created on : Jan 12, 2024, 9:09:12 PM
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
        <%@include file="header.jsp" %>

        <div>

            <div>
                <h1>Đăng ký</h1>
                <h1><a href="<c:url value="/login.jsp"/>">Đăng nhập</a></h1>
            </div>

            <form>
                <label for="user">Số điện thoại</label> <br/>
                <input type="text" name="user" id="user" placeholder="Nhập số điện thoại" required autofocus><br/>
                <label for="user">Mật khẩu</label><br/>
                <input type="text" name="pass" id="pass" placeholder="Nhập mật khẩu" required><br/>

                <button type="submit">Đăng ký</button>
            </form>

        </div>

        <%@include file="footer.jsp" %>
    </body>
</html>
