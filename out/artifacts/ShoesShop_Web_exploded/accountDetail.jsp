<%@ page import="model.customer.Customer" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        .account-center-heading {
            font-size: 36px;
            text-align: center;
            margin-top: 20px;
            margin-bottom: 20px;
        }

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

        .avatar-container {
            position: relative;
            width: 150px; /* Adjust width and height as needed */
            height: 150px;
            border-radius: 50%;
            overflow: hidden;
        }

        .avatar-img-container {
            position: relative;
            width: 100%;
            height: 100%;
        }

        .avatar-img {
            width: 100%;
            height: auto;
            display: block;
        }

        .upload-button {
            display: none;
            position: absolute;
            bottom: 10px;
            left: 50%;
            transform: translateX(-50%);
            padding: 5px 10px;
            background-color: #666d69; /* Button background color */
            color: #fff; /* Button text color */
            border: none;
            border-radius: 5px;
            cursor: pointer;
            outline: none;
        }

        .avatar-container:hover .upload-button {
            display: block;
        }


    </style>


</head>
<body>
<c:set var="size" value="${sessionScope.size}"/>
<c:set var="account" value="${sessionScope.account}"/>
<div class="colorlib-loader"></div>

<div id="page">
    <nav class="colorlib-nav" role="navigation">
        <div class="top-menu">
            <div class="container">
                <div class="row">
                    <div class="col-sm-5 col-md-7">
                        <div id="colorlib-logo"><a href="home">Style Hub</a></div>
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
                            <c:if test="<%=account.isAdmin()%>">
                                <a href="admin">Admin</a>
                            </c:if>
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
                            <li class="active"><a href="home">Home</a></li>
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
                            <li><a href="about">About</a></li>
                            <li><a href="contact">Contact</a></li>
                            <li class="cart"><a href="cart"><i class="icon-shopping-cart"></i> Cart [${size}]</a>
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
                    <div class="row">
                        <div class="container ml-md-3">
                            <form action="account" method="post">
                                <div class="avatar-container">
                                    <div class="avatar-img-container">
                                        <img src="images/${account.image}" alt="Avatar" class="avatar-img">
                                        <input type="file" id="uploadfile" name="image" style="display: none;"
                                              value="${account.image}" onchange="previewImage(this);" accept="image/*">
                                        <button class="upload-button" onclick="chooseFile()">Upload New Image</button>
                                    </div>
                                </div>
                                <script>
                                    function chooseFile() {
                                        var fileInput = document.getElementById('uploadfile');
                                        fileInput.click(); // Trigger click event on file input
                                    }
                                    function previewImage(input) {
                                        var avatarImg = document.querySelector('.avatar-img');
                                        if (input.files && input.files[0]) {
                                            var reader = new FileReader();

                                            reader.onload = function (e) {
                                                avatarImg.src = e.target.result;
                                            };

                                            reader.readAsDataURL(input.files[0]);
                                        }
                                    }
                                </script>
                                <div class="form-group">
                                    <label for="full-name">Họ và tên</label>
                                    <input type="text" class="form-control" name="full-name" id="full-name"
                                           value="${account.fullName}">
                                </div>
                                <div class="form-group">
                                    <label for="phone">Số điện thoại</label>
                                    <input type="text" class="form-control" name="phone" id="phone"
                                           value="${account.phoneNumber}">
                                </div>
                                <div class="form-group">
                                    <label for="email">Email</label>
                                    <input type="email" class="form-control" name="email" id="email"
                                           value="${account.email}">
                                </div>
                                <div class="form-group">
                                    <label>Giới tính*</label>
                                    <div class="form-check">
                                        <input type="radio" class="form-check-input" name="gender" id="male"
                                               value="Male" required>
                                        <label class="form-check-label" for="male">Nam</label>
                                    </div>
                                    <div class="form-check">
                                        <input type="radio" class="form-check-input" name="gender" id="female"
                                               value="Female">
                                        <label class="form-check-label" for="female">Nữ</label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="birthday">Birthday*</label>
                                    <input type="date" class="form-control" id="birthday" name="birthday"
                                           value="${account.birthday}">
                                </div>
                                <input type="text" name="action" value="updateInfo" hidden="hidden">
                                <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                                ${requestScope.infoUpdateResult}
                            </form>
                            <h1>Change Password</h1>
                            <form action="account" method="get">
                                <div class="password-section">
                                    <div class="form-group">
                                        <label for="currentPassword">Mật khẩu hiện tại*</label>
                                        <input type="password" class="form-control" id="currentPassword"
                                               name="currentPassword" placeholder="Mật khẩu hiện tại*" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="newPassword">Mật khẩu mới*</label>
                                        <input type="password" class="form-control" id="newPassword" name="newPassword"
                                               placeholder="Mật khẩu mới*" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="confirmPassword">Nhập lại mật khẩu mới*</label>
                                        <input type="password" class="form-control" id="confirmPassword"
                                               name="confirmPassword" placeholder="Nhập lại mật khẩu mới*" required>
                                    </div>
                                    <input type="text" name="action" value="changePass" hidden="hidden">
                                </div>
                                <button type="submit" class="btn btn-primary">Change password</button>
                            </form>
                            ${requestScope.passChangeResult}
                            <button class="btn btn-secondary"><a href="login" style="color: white;">Log Out</a></button>
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
                        <li><a href="about">About us</a></li>
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
