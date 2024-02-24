<%@ page import="model.customer.Customer" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Style Hub</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="https://fonts.googleapis.com/css?family=Montserrat:300,400,500,600,700" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Rokkitt:100,300,400,700" rel="stylesheet">

    <!-- Animate.css -->
    <link rel="stylesheet" href="css/animate.css">
    <!-- Icomoon Icon Fonts-->
    <link rel="stylesheet" href="css/icomoon.css">
    <!-- Ion Icon Fonts-->
    <link rel="stylesheet" href="css/ionicons.min.css">
    <!-- Bootstrap  -->
    <link rel="stylesheet" href="css/bootstrap.min.css">

    <!-- Magnific Popup -->
    <link rel="stylesheet" href="css/magnific-popup.css">

    <!-- Flexslider  -->
    <link rel="stylesheet" href="css/flexslider.css">

    <!-- Owl Carousel -->
    <link rel="stylesheet" href="css/owl.carousel.min.css">
    <link rel="stylesheet" href="css/owl.theme.default.min.css">

    <!-- Date Picker -->
    <link rel="stylesheet" href="css/bootstrap-datepicker.css">
    <!-- Flaticons  -->
    <link rel="stylesheet" href="fonts/flaticon/font/flaticon.css">

    <!-- Theme style  -->
    <link rel="stylesheet" href="css/style.css">

    <style>
        .user-account {
            font-weight: 400;
            font-family: 'Rokkitt', Georgia, serif;
            text-transform: uppercase;
            font-size: 15px;
            letter-spacing: 2px;
            display: flex;
            align-items: center;
        }

        .user-account a {
            text-decoration: none;
            color: black;
            margin-right: 10px;
        }

        .admin-links {
            display: none;
            position: absolute;
            background-color: white;
            border: 1px solid #ccc;
            padding: 5px;
        }

        .admin-links a {
            display: block;
            padding: 5px;
        }

        .user-account:hover .admin-links {
            display: block;
        }

        .account-center-heading {
            font-size: 36px;
            text-align: center;
            margin-top: 20px;
            margin-bottom: 20px;
        }

        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
        }

        .order {
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 10px;
            margin-bottom: 20px;
        }

        .order-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 10px;
        }

        .order-header .order-id {
            font-weight: bold;
        }

        .order-header .order-status {
            font-weight: bold;
        }

        .order-details {
            display: flex;
            flex-direction: column;
        }

        .order-product {
            display: flex;
            margin-bottom: 5px;
        }

        .product-img {
            width: 80px;
            height: 80px;
            margin-right: 10px;
        }

        .product-details {
            flex-grow: 1;
        }

        .product-name {
            font-weight: bold;
        }

        .product-price,
        .product-quantity {
            margin-top: 5px;
        }

        .total-price {
            margin-left: auto;
        }

        .cancel-btn {
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
            float: right; /* Align the button to the right */
            margin-top: 10px; /* Add some top margin for spacing */
        }

        .cancel-btn:hover {
            background-color: #c82333; /* Darken the background color on hover */
        }

        .bold {
            font-weight: bold;
            font-size: 1.1em;
        }

        .nav-tabs {
            list-style-type: none;
            padding: 0;
            margin: 0;
            border-bottom: none;
        }

        .nav-tabs li {
            display: inline;
            margin-right: 10px;
        }

        .nav-tabs li a {
            text-decoration: none;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            color: #666;
        }

        .nav-tabs li a.active {
            background-color: #f0f0f0;
            border-bottom: 1px solid #f0f0f0;
        }

        .search-bar {
            margin-top: 10px;
            margin-bottom: 10px;
        }

        .search-bar input[type="text"] {
            padding: 8px;
            width: 200px;
            border-radius: 4px;
            border: 1px solid #ddd;
        }

        .search-bar input[type="submit"] {
            padding: 8px 16px;
            border-radius: 4px;
            border: none;
            background-color: #007bff;
            color: white;
            cursor: pointer;
        }
    </style>


</head>
<body>
<c:set var="size" value="${sessionScope.size}"/>
<c:set var="account" value="${sessionScope.account}"/>
<jsp:useBean id="database" class="model.product.ProductDAO"/>
<div class="colorlib-loader"></div>

<div id="page">
    <nav class="colorlib-nav" role="navigation">
        <div class="top-menu">
            <div class="container">
                <div class="row">
                    <div class="col-sm-5 col-md-7">
                        <div id="colorlib-logo"><a href="index.jsp">Style Hub</a></div>
                    </div>
                    <div class="col-sm-5 col-md-3">
                        <form action="search" class="search-wrap">
                            <div class="form-group">
                                <input type="search" name="name" class="form-control search" placeholder="Search">
                                <button class="btn btn-primary submit-search text-center" type="submit"><i
                                        class="icon-search"></i></button>
                            </div>
                        </form>
                    </div>
                    <%
                        HttpSession session1 = request.getSession(false);
                        Customer account = (Customer) session.getAttribute("account");
                    %>
                    <div class="user-account">
                        <%
                            if (account != null) {
                        %>
                        <a href="account" class="account-link"><%= account.getFullName() %>
                        </a>
                        <div class="admin-links">
                            <a href="account">Account</a>
                            <a href="admin">Admin</a>
                        </div>
                        <%
                        } else {
                        %>
                        <a href="account" class="login-link">Login</a>
                        <a href="register" class="register-link">Register</a>
                        <%
                            }
                        %>
                    </div>

                    <script>
                        // JavaScript to show admin links on hover
                        document.addEventListener("DOMContentLoaded", function () {
                            var accountLink = document.querySelector(".col-sm-2 a[href='account']");
                            var adminLinks = document.querySelector(".admin-links");

                            if (accountLink && adminLinks) {
                                accountLink.addEventListener("mouseenter", function () {
                                    adminLinks.style.display = "block";
                                });

                                accountLink.addEventListener("mouseleave", function () {
                                    adminLinks.style.display = "none";
                                });
                            }
                        });
                    </script>
                </div>
                <div class="row">
                    <div class="col-sm-12 text-left menu-1">
                        <ul>
                            <li class="active"><a href="index.jsp">Home</a></li>
                            <li class="has-dropdown">
                                <a href="collections?categories=Men">Men</a>
                                <ul class="dropdown">
                                    <li><a href="collections?categories=Men&categories=Boot">Boot</a></li>
                                    <li><a href="collections?categories=Men&categories=Derby">Derby</a></li>
                                    <li><a href="collections?categories=Men&categories=Loafer">Loafer</a></li>
                                </ul>
                            </li>
                            <li class="has-dropdown">
                                <a href="collections?categories=Women">Women</a>
                                <ul class="dropdown">
                                    <li><a href="collections?categories=Women&categories=Boot">Boot</a></li>
                                    <li><a href="collections?categories=Women&categories=Derby">Derby</a></li>
                                    <li><a href="collections?categories=Women&categories=Loafer">loafer</a></li>
                                    <li><a href="collections?categories=Women&categories=Slipper">Slipper</a></li>
                                    <li><a href="collections?categories=Women&categories=Sandal">Sandal</a></li>
                                    <li><a href="collections?categories=Women&categories=Flip Flop">Flip Flop</a></li>
                                </ul>
                            </li>
                            <li><a href="collections?categories=BST THE UPGRADE">BST The Upgrade</a></li>
                            <li><a href="collections?categories=The Focus Project">The Focus Project</a></li>
                            <li><a href="about.jsp">About</a></li>
                            <li><a href="contact.jsp">Contact</a></li>
                            <li class="cart"><a href="cart.jsp"><i class="icon-shopping-cart"></i> Cart [${size}]</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </nav>
    <h1 class="account-center-heading">Account Center</h1>
    <div class="container">
        <div class="row">
            <div class="col-md-3">
                <ul class="list-group">
                    <li class="list-group-item"><a href="account">Thông tin tài khoản</a></li>
                    <li class="list-group-item"><a href="account?action=address-list">Danh sách địa chỉ</a></li>
                    <li class="list-group-item"><a href="account?action=order-list">Danh sách đơn hàng</a></li>
                    <li class="list-group-item"><a href="login">Đăng xuất</a></li>
                </ul>
            </div>
            <div class="col-md-9">
                <div class="container">
                    <div>
                        <ul class="nav-tabs">
                            <li><a href="account?action=order-list" id="allTab">All</a></li>
                            <li><a href="account?action=order-list&status=pending" id="pendingTab">Processing</a></li>
                            <li><a href="account?action=order-list&status=canceled" id="canceledTab">Canceled</a></li>
                            <li><a href="account?action=order-list&status=delivered" id="deliveredTab">Delivered</a>
                            </li>
                        </ul>
                        <script>
                            // Function to set active class based on current status parameter
                            function setActiveTab() {
                                // Get current status parameter from the URL
                                const urlParams = new URLSearchParams(window.location.search);
                                const status = urlParams.get('status');
                                // Remove active class from all tabs
                                const tabs = document.querySelectorAll('.nav-tabs a');
                                tabs.forEach(tab => {
                                    tab.classList.remove('active');
                                });
                                if (status === null) {
                                    document.getElementById('allTab').classList.add('active');
                                } else if (status === 'pending') {
                                    document.getElementById('pendingTab').classList.add('active');
                                } else if (status === 'canceled') {
                                    document.getElementById('canceledTab').classList.add('active');
                                } else if (status === 'delivered') {
                                    document.getElementById('deliveredTab').classList.add('active');
                                }
                            }

                            window.addEventListener('load', setActiveTab);
                        </script>
                        <div class="search-bar">
                            <form action="account" method="get">
                                <input type="text" name="search" placeholder="Search orders...">
                                <input type="text" name="action" value="order-search" hidden="hidden">
                                <input type="submit" value="Search">
                            </form>
                        </div>
                    </div>
                    <div class="row">
                        <div class="container ml-md-3">
                            <h2 style="text-align: center">Danh sách đơn hàng</h2>
                            <c:set var="orders" value="${requestScope.orders}"/>
                            <c:set var="orderDetails" value="${requestScope.orderDetail}"/>
                            <c:set var="i" value="0"/>
                            <c:forEach var="order" items="${orders}">
                                <div class="order">
                                    <div class="order-header">
                                        <div class="order-id">Order ID: ${order.id}</div>
                                        <div class="order-status">Status: ${order.status}</div>
                                    </div>
                                    <div class="order-details">
                                        <c:forEach var="orderDetailItem" items="${orderDetails[i]}">
                                            <div class="order-product">
                                                <img src="images/${database.get(orderDetailItem.productId).images.get(0)}"
                                                     alt="Product Image" class="product-img">
                                                <div class="product-details">
                                                    <div class="product-name">${database.get(orderDetailItem.productId).title}</div>
                                                    <div class="product-size">Size: ${orderDetailItem.size}</div>
                                                    <div class="product-quantity">
                                                        Quantity: ${orderDetailItem.amount}</div>
                                                    <fmt:formatNumber
                                                            value="${database.get(orderDetailItem.productId).outPrice}"
                                                            pattern="#,##0đ" var="outPrice"/>
                                                    <div class="product-price">Price: <span
                                                            class="bold">${outPrice}</span></div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                        <fmt:formatNumber value="${order.totalPrice}" pattern="#,##0đ"
                                                          var="totalPrice"/>
                                        <div class="total-price">Total Price: <span class="bold">${totalPrice}</span>
                                        </div>
                                        <c:if test="${!order.status.equals('shipped') && !order.status.equals('cancelled')}">
                                            <form id="cancelForm_${order.id}" action="account" method="get">
                                                <input type="hidden" name="orderId" value="${order.id}">
                                                <input type="text" name="action" value="cancelOrder" hidden="hidden"/>
                                                <input type="submit" value="Cancel Order" class="cancel-btn"
                                                       onclick="return canCancel(${order.id}, '${order.status}');">
                                            </form>
                                        </c:if>
                                        <script>
                                            function canCancel(orderId, status) {
                                                if (status !== 'purchased-pending') {
                                                    alert('You cannot cancel this order at the moment.');
                                                    return false;
                                                }
                                                return true;
                                            }
                                        </script>
                                    </div>
                                    <c:set var="i" value="${i+1}"/>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <footer id="colorlib-footer" role="contentinfo">
        <div class="container">
            <div class="row row-pb-md">
                <div class="col footer-col colorlib-widget">
                    <h4>About Style Hub</h4>
                    <p>A man needs his own style</p>
                    <p>
                    <ul class="colorlib-social-icons">
                        <li><a href="https://www.facebook.com/datphuong1810/"><i class="icon-facebook"></i></a></li>
                    </ul>
                    </p>
                </div>
                <div class="col footer-col colorlib-widget">
                    <h4>Customer Care</h4>
                    <p>
                    <ul class="colorlib-footer-links">
                        <li><a href="#">Contact</a></li>
                        <li><a href="tel://0705410751">Customer Services</a></li>
                        <li><a href="https://maps.app.goo.gl/ij6UKJKGTrgLyaYHA">Site maps</a></li>
                    </ul>
                    </p>
                </div>
                <div class="col footer-col colorlib-widget">
                    <h4>Information</h4>
                    <p>
                    <ul class="colorlib-footer-links">
                        <li><a href="about.jsp">About us</a></li>
                        <li><a href="#">Privacy Policy</a></li>
                        <li><a href="#">Support</a></li>
                    </ul>
                    </p>
                </div>

                <div class="col footer-col">
                    <h4>Contact Information</h4>
                    <ul class="colorlib-footer-links">
                        <li>Nhà trọ Mỹ Linh, <br> Phú Hữu Tân Xã Thạch Thất Hà Nội</li>
                        <li><a href="tel://0705410751">+84 0705410751</a></li>
                        <li><a href="mailto:datnguyenphuong1810@gmail.com">datnguyenphuong1810@gmail.com</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="copy">
            <div class="row">
                <div class="col-sm-12 text-center">
                    <p>
							<span>
Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This website is made with <i
                                    class="icon-heart" aria-hidden="true"></i> by <a
                                    href="https://www.facebook.com/datphuong1810/" target="_blank">Đạt NP</a>
                                </span>
                    </p>
                </div>
            </div>
        </div>
    </footer>
</div>

<div class="gototop js-top">
    <a href="#" class="js-gotop"><i class="ion-ios-arrow-up"></i></a>
</div>

<!-- jQuery -->
<script src="js/jquery.min.js"></script>
<!-- popper -->
<script src="js/popper.min.js"></script>
<!-- bootstrap 4.1 -->
<script src="js/bootstrap.min.js"></script>
<!-- jQuery easing -->
<script src="js/jquery.easing.1.3.js"></script>
<!-- Waypoints -->
<script src="js/jquery.waypoints.min.js"></script>
<!-- Flexslider -->
<script src="js/jquery.flexslider-min.js"></script>
<!-- Owl carousel -->
<script src="js/owl.carousel.min.js"></script>
<!-- Magnific Popup -->
<script src="js/jquery.magnific-popup.min.js"></script>
<script src="js/magnific-popup-options.js"></script>
<!-- Date Picker -->
<script src="js/bootstrap-datepicker.js"></script>
<!-- Stellar Parallax -->
<script src="js/jquery.stellar.min.js"></script>
<!-- Main -->
<script src="js/main.js"></script>

</body>
</html>
