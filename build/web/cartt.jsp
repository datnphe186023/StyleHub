<%-- 
    Document   : cart
    Created on : Jan 18, 2024, 4:04:06 PM
    Author     : datng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
</head>
<body>
<h1>Cart</h1>
<c:set var="cart" value="${sessionScope.cart}"/>
<table border="1px" width="30%">
    <tr>
        <th>NO</th>
        <th>Name</th>
        <th>Quantity</th>
        <th>Price</th>
        <th>Action</th>
    </tr>
    <c:set var="t" value="0"/>
    <c:set var="total" value="0"/>
    <c:forEach items="${cart.items}" var="i">
        <c:set var="t" value="${t+1}"/>
        <c:set var="total" value="${total+i.product.outPrice*i.quantity}"/>
        <tr>
            <td>${t}</td>
            <td>${i.product.title}</td>
            <td>
                <button><a href="process?num=-1&id=${i.product.id}">-</a></button>
                <input type="text" readonly value="${i.quantity}"/>
                <button><a href="process?num=1&id=${i.product.id}">+</a></button>
            </td>
            <td>${i.product.outPrice}</td>
            <td>
                <form action="process" method="get">
                    <input type="hidden" name="id" value="${i.product.id}">
                    <input type="submit" value="Return item">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<h5>Total: ${total}</h5>
<p></p>
<form action="checkout" method="get">
    <input type="submit" value="Check out">
</form>
<hr/>
<h2 style="color: chocolate"><a href="product.jsp">Back to shopping</a></h2>
</body>
</html>
