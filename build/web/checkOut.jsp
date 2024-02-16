<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="model.customer.Customer" %>
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

    </style>

</head>
<body>
<c:set var="size" value="${sessionScope.size}"/>
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
                        <a href="register.jsp" class="register-link">Register</a>
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
                            <li><a href="about.html">About</a></li>
                            <li><a href="contact.html">Contact</a></li>
                            <li class="cart"><a href="cart.jsp"><i class="icon-shopping-cart"></i> Cart [${size}]</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </nav>
    <div class="breadcrumbs">
        <div class="container">
            <div class="row">
                <div class="col">
                    <p class="bread"><span><a href="index.jsp">Home</a></span> / <span>Checkout</span></p>
                </div>
            </div>
        </div>
    </div>
    <div class="colorlib-product">
        <form action="checkout" method="post" class="colorlib-form">
            <div class="container">
                <div class="row row-pb-lg">
                    <div class="col-sm-10 offset-md-1">
                        <div class="process-wrap">
                            <div class="process text-center active">
                                <p><span>01</span></p>
                                <h3>Shopping Cart</h3>
                            </div>
                            <div class="process text-center active">
                                <p><span>02</span></p>
                                <h3>Checkout</h3>
                            </div>
                            <div class="process text-center">
                                <p><span>03</span></p>
                                <h3>Order Complete</h3>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-8">
                        <h2>Billing Details</h2>
                        <div class="container">
                            <div class="row">
                                <div class="col-md-8">
                                    <div class="list-group">
                                        <c:forEach items="${requestScope.addressList}" var="address">
                                            <label class="list-group-item">
                                                <input type="radio" name="addressId" value="${address[0]}">
                                                <div>
                                                    <h4 class="mb-1">Họ và tên người nhận: ${address[1]}</h4>
                                                    <p class="mb-1">Số điện thoại: ${address[2]}</p>
                                                    <p class="mb-1">Địa chỉ : ${address[3]}</p>
                                                </div>
                                            </label>
                                        </c:forEach>
                                    </div>
                                    <a href="account?action=address-list" class="btn btn-primary mt-3">Thêm địa chỉ</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="cart-detail">
                                    <h2>Cart Total</h2>
                                    <c:set var="cart" value="${sessionScope.cart}"/>
                                    <c:set var="total" value="0"/>
                                    <ul>
                                        <li>
                                            <ul>
                                                <c:forEach var="i" items="${cart.items}">
                                                    <c:set var="total" value="${total+i.product.outPrice*i.quantity}"/>
                                                    <fmt:formatNumber value="${i.product.outPrice}" pattern="#,##0đ"
                                                                      var="outPrice"/>
                                                    <li><span>${i.product.title}</span><span>${outPrice}</span></li>
                                                </c:forEach>
                                            </ul>
                                        </li>
                                        <fmt:formatNumber value="${total}" pattern="#,##0đ" var="totalFormated"/>
                                        <li><span>Order Total</span> <span>${totalFormated}</span></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="w-100"></div>
                            <div class="col-md-12">
                                <div class="cart-detail">
                                    <h2>Payment Method</h2>
                                    <div class="form-group">
                                        <div class="col-md-12">
                                            <div class="radio">
                                                <label><input type="radio" name="payment" value="COD">COD</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-12">
                                            <div class="radio">
                                                <label><input type="radio" name="payment" value="BankTransfer">Chuyển khoản ngân hàng</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-12">
                                            <div class="radio">
                                                <label><input type="radio" name="payment" value="VNPay">VNPay</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-12">
                                            <div class="checkbox">
                                                <label><input type="checkbox" id="termsCheckbox" value=""> I have read and accept the terms
                                                    and
                                                    conditions</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 text-center">
                                <button id="submitButton" type="submit" class="btn btn-primary" disabled>Place an order</button>
                            </div>
                        </div>
                        <script>
                            document.addEventListener("DOMContentLoaded", function () {
                                var termsCheckbox = document.getElementById("termsCheckbox");
                                var submitButton = document.getElementById("submitButton");

                                termsCheckbox.addEventListener("change", function () {
                                    if (termsCheckbox.checked) {
                                        submitButton.disabled = false;
                                    } else {
                                        submitButton.disabled = true;
                                    }
                                });
                            });
                        </script>
                    </div>
                </div>
            </div>
        </form>
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
                        <li><a href="https://maps.app.goo.gl/z7MAnSiKahsZu5V79">Site maps</a></li>
                    </ul>
                    </p>
                </div>
                <div class="col footer-col colorlib-widget">
                    <h4>Information</h4>
                    <p>
                    <ul class="colorlib-footer-links">
                        <li><a href="about.html">About us</a></li>
                        <li><a href="#">Privacy Policy</a></li>
                        <li><a href="#">Support</a></li>
                    </ul>
                    </p>
                </div>

                <div class="col footer-col">
                    <h4>Contact Information</h4>
                    <ul class="colorlib-footer-links">
                        <li>Nhà trọ Hoàng Quân, <br> Phú Hữu Tân Xã Thạch Thất Hà Nội</li>
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