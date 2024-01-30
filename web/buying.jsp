<%-- 
    Document   : products
    Created on : Jan 18, 2024, 3:21:52 PM
    Author     : datng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
    <link rel="stylesheet" href="css/stylesheet.css"/>
</head>
<body>
<c:set var="size" value="${sessionScope.size}"></c:set>
<p id="bag"><img src="images/cart.jpg" alt="alt" width="50" height="50"/></p>
<a href="cart.jsp">Mybag (${size})</a>
<h1>The list of products</h1>
<form name="f" action="" method="post">
    Enter the number of items to buy: <input type="number" name="quantity" value="1">
    <hr/>
    <table border="1px" width="40%">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Size</th>
            <th>Price</th>
            <th>Action</th>
        </tr>
        <jsp:useBean id="db" class="model.product.ProductDAO"/>
        <c:forEach items="${db.all}" var="p">
            <tr>
                <td>${p.id}</td>
                <td>${p.title}</td>
                <td>
                    <select size="1" id="sizeSelect${p.id}">
                        <c:forEach items="${p.size}" var="psize">
                            <option value="${psize.size}">${psize.size}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>${p.outPrice}</td>
                <td>
                    <input type="submit" onclick="buy(${p.id}, document.getElementById('sizeSelect${p.id}').value)"
                           value="Buy item"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</form>
</body>
</html>
<script type="text/javascript">
    function buy(id, size) {
        document.f.action = "buy?id=" + id + "&psize=" + size;
        document.f.submit();
    }
</script>
