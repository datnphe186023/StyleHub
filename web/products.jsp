<%-- 
    Document   : products
    Created on : Jan 18, 2024, 3:21:52 PM
    Author     : datng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            Enter the number of items to buy: <input type="number" name="num" value="1">
            <hr/>
            <table border="1px" width="40%">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Action</th>
                </tr>
                <jsp:useBean id="db" class="model.product.ProductDAO"/>
                <c:forEach items="${db.all}" var="p">
                    <tr>
                        <td>${p.id}</td>
                        <td>${p.title}</td>
                        <td>${p.outPrice}</td>
                        <td>
                            <input type="submit" onclick="buy(${p.id})" value="Buy item"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </body>
</html>
<script type="text/javascript">
    function buy(id){
        document.f.action="buy?id="+id;
        document.f.submit();
    }
</script>
