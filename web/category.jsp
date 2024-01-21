<%-- 
    Document   : category
    Created on : Jan 12, 2024, 9:27:21 PM
    Author     : datng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body> 
        
        <!--this page is like a big category for user to choose from, by genres, ageRate, and some kinds of top of books-->

        <!--display all genres in a list-->
        <div>
            <ul>
                <c:forEach items="${requestScope.genres}" var="genres">
                    <li><a href="/genres?genres=${genres.title}"></a>${genres.title}</li>
                    </c:forEach>
            </ul>
        </div>
        
        <!--display all ageRates in a list-->
        <div>
            <ul>
                <c:forEach items="${requestScope.ageRate}" var="ageRate">
                    <li><a href="/ageRate?ageRate=${ageRate}"></a>${ageRate}</li>
                    </c:forEach>
            </ul>
        </div>
        
        <!--display top books, exp: top bán chạy, top mới ra mắt, ...-->
        <div>
            <ul>
                <c:forEach items="${requestScope.data}" var="top">
                    <li><a href="/top?top=${top}"></a>${top}</li>
                    </c:forEach>
            </ul>
        </div>

    </body>
</html>
