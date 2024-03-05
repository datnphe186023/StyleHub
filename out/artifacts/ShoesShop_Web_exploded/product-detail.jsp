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


    <div class="colorlib-product">
        <div class="container">
            <form name="f" action="" method="post">
                <div class="row row-pb-lg product-detail-wrap">
                    <div class="col-sm-8">
                        <div class="owl-carousel">
                            <c:set value="${requestScope.product}" var="product"/>
                            <c:forEach items="${requestScope.images}" var="image">
                                <div class="item">
                                    <div class="product-entry border">
                                        <a href="#" class="prod-img">
                                            <img src="<c:url value="/images/${image}"/>" class="img-fluid"
                                                 alt="${product.title}">
                                        </a>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="product-desc">
                            <h3>${product.title}</h3>
                            <p class="price">
                                <fmt:formatNumber value="${product.outPrice}" pattern="#,##0đ" var="outPrice"/>
                                <span>${outPrice}</span>
                                <c:set var="averageReview" value="0"/>
                                <c:forEach items="${product.reviews}" var="rate">
                                    <c:set var="averageReview" value="${averageReview + rate.review}"/>
                                </c:forEach>
                                <c:set var="averageReview" value="${averageReview/product.reviews.size()}"/>
                                <span class="rate">
                                        <i class="icon-star-full"></i>
                                        <i class="icon-star-full"></i>
                                        <i class="icon-star-full"></i>
                                        <i class="icon-star-full"></i>
                                        <i class="icon-star-half"></i>
                                        4.5 (19 Rating)
                                    </span>
                                <script>
                                    var averageReview = ${averageReview};
                                    var fullStars = Math.floor(averageReview);
                                    var halfStar = (averageReview % 1) >= 0.5;
                                    var emptyStars = 5 - fullStars - (halfStar ? 1 : 0);
                                    var starsHtml = '';
                                    for (var i = 0; i < fullStars; i++) {
                                        starsHtml += '<i class="icon-star-full"></i>';
                                    }
                                    if (halfStar) {
                                        starsHtml += '<i class="icon-star-half"></i>';
                                    }
                                    for (var i = 0; i < emptyStars; i++) {
                                        starsHtml += '<i class="icon-star-empty"></i>';
                                    }
                                    var finalHtml = starsHtml + ' (' + ${product.reviews.size()} +' Rating)';
                                    document.querySelector('.rate').innerHTML = finalHtml;
                                </script>
                            </p>
                            <div class="size-wrap">
                                <div class="block-26 mb-2">
                                    <h4>Size</h4>
                                    <c:set value="0" var="maxStock"/>
                                    <select size="1" id="sizeSelect${product.id}">
                                        <c:forEach items="${product.size}" var="psize">
                                            <option value="${psize.size}" data-stock="${psize.stock}">
                                                    ${psize.size} - Stock : ${psize.stock}
                                            </option>
                                        </c:forEach>
                                    </select>

                                </div>
                            </div>
                            <div class="input-group mb-4">
                                    <span class="input-group-btn">
                                         <button type="button" class="quantity-left-minus btn" data-type="minus"
                                                 data-field="quantity">
                                            <i class="icon-minus2"></i>
                                        </button>
                                    </span>
                                <input type="text" id="quantity" name="quantity" class="form-control input-number"
                                       value="1">
                                <span class="input-group-btn ml-1">
                                        <button type="button" class="quantity-right-plus btn" data-type="plus"
                                                data-field="quantity">
                                            <i class="icon-plus2"></i>
                                        </button>
                                </span>
                                <script>
                                    document.addEventListener('DOMContentLoaded', function () {
                                        var quantityInput = document.getElementById('quantity');

                                        // Function to update quantity
                                        function updateQuantity(operation) {
                                            var currentValue = parseInt(quantityInput.value);

                                            if (operation === 'plus') {
                                                quantityInput.value = currentValue + 1;
                                            } else if (operation === 'minus') {
                                                if (currentValue > 1) {
                                                    quantityInput.value = currentValue - 1;
                                                }
                                            }
                                        }

                                        // Add event listeners to the plus and minus buttons
                                        var plusButton = document.querySelector('.quantity-right-plus');
                                        var minusButton = document.querySelector('.quantity-left-minus');

                                        plusButton.addEventListener('click', function () {
                                            updateQuantity('plus');
                                        });

                                        minusButton.addEventListener('click', function () {
                                            updateQuantity('minus');
                                        });
                                    });
                                </script>
                            </div>
                            <div class="row">
                                <div class="col-sm-12 text-center">
                                    <p class="addtocart">
                                        <a href="#"
                                           onclick="buy(${product.id}, document.getElementById('sizeSelect${product.id}').value)"
                                           class="btn btn-primary btn-addtocart">
                                            <i class="icon-shopping-cart"></i> Add to Cart
                                        </a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
            <div class="row">
                <div class="col-sm-12">
                    <div class="row">
                        <div class="col-md-12 pills">
                            <div class="bd-example bd-example-tabs">
                                <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">

                                    <li class="nav-item">
                                        <a class="nav-link active" id="pills-description-tab" data-toggle="pill"
                                           href="#pills-description" role="tab" aria-controls="pills-description"
                                           aria-expanded="true">Description</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" id="pills-review-tab" data-toggle="pill"
                                           href="#pills-review" role="tab" aria-controls="pills-review"
                                           aria-expanded="true">Review</a>
                                    </li>
                                </ul>

                                <div class="tab-content" id="pills-tabContent">
                                    <div class="tab-pane border fade show active" id="pills-description" role="tabpanel"
                                         aria-labelledby="pills-description-tab">
                                        ${product.description}
                                    </div>

                                    <div class="tab-pane border fade" id="pills-review" role="tabpanel"
                                         aria-labelledby="pills-review-tab">
                                        <div class="row">
                                            <div class="col-md-8">
                                                <h3 class="head">${product.reviews.size()} Reviews</h3>
                                                <c:forEach items="${product.reviews}" var="review">
                                                    <div class="review">
                                                        <div class="user-img"
                                                             style="background-image: url(images/${review.customer.image})"></div>
                                                        <div class="desc">
                                                            <h4>
                                                                <span class="text-left">${review.customer.fullName}</span>
                                                                <span class="text-right">${review.reviewDate}</span>
                                                            </h4>
                                                            <p class="star">
                                                                    <span class="product-review-rate">
                                                                        <i class="icon-star-full"></i>
                                                                        <i class="icon-star-full"></i>
                                                                        <i class="icon-star-full"></i>
                                                                        <i class="icon-star-half"></i>
                                                                        <i class="icon-star-empty"></i>
                                                                    </span>
                                                                <script>
                                                                    var review = ${review.review};
                                                                    var fullStars = Math.floor(review);
                                                                    var halfStar = (review % 1) >= 0.5;
                                                                    var emptyStars = 5 - fullStars - (halfStar ? 1 : 0);
                                                                    var starsHtml = '';
                                                                    for (var i = 0; i < fullStars; i++) {
                                                                        starsHtml += '<i class="icon-star-full"></i>';
                                                                    }
                                                                    if (halfStar) {
                                                                        starsHtml += '<i class="icon-star-half"></i>';
                                                                    }
                                                                    for (var i = 0; i < emptyStars; i++) {
                                                                        starsHtml += '<i class="icon-star-empty"></i>';
                                                                    }
                                                                    var finalHtml = starsHtml;
                                                                    document.querySelector('.product-review-rate').innerHTML = finalHtml;
                                                                </script>
                                                            </p>
                                                            <p>${review.detail}</p>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="rating-wrap">
                                                    <h3 class="head">Total Review</h3>
                                                    <jsp:useBean id="review" class="model.review.ReviewDAO"/>
                                                    <div class="wrap">
                                                        <p class="star">
                                                                    <span>
                                                                        <i class="icon-star-full"></i>
                                                                        <i class="icon-star-full"></i>
                                                                        <i class="icon-star-full"></i>
                                                                        <i class="icon-star-full"></i>
                                                                        <i class="icon-star-full"></i>
                                                                    </span>
                                                            <span>${review.getNumberReviews(5, product.id)} Reviews</span>
                                                        </p>
                                                        <p class="star">
                                                                    <span>
                                                                        <i class="icon-star-full"></i>
                                                                        <i class="icon-star-full"></i>
                                                                        <i class="icon-star-full"></i>
                                                                        <i class="icon-star-full"></i>
                                                                        <i class="icon-star-empty"></i>
                                                                    </span>
                                                            <span>${review.getNumberReviews(4, product.id)} Reviews</span>
                                                        </p>
                                                        <p class="star">
                                                                    <span>
                                                                        <i class="icon-star-full"></i>
                                                                        <i class="icon-star-full"></i>
                                                                        <i class="icon-star-full"></i>
                                                                        <i class="icon-star-empty"></i>
                                                                        <i class="icon-star-empty"></i>
                                                                    </span>
                                                            <span>${review.getNumberReviews(3, product.id)} Reviews</span>
                                                        </p>
                                                        <p class="star">
                                                                    <span>
                                                                        <i class="icon-star-full"></i>
                                                                        <i class="icon-star-full"></i>
                                                                        <i class="icon-star-empty"></i>
                                                                        <i class="icon-star-empty"></i>
                                                                        <i class="icon-star-empty"></i>
                                                                    </span>
                                                            <span>${review.getNumberReviews(2, product.id)} Reviews</span>
                                                        </p>
                                                        <p class="star">
                                                                    <span>
                                                                        <i class="icon-star-full"></i>
                                                                        <i class="icon-star-empty"></i>
                                                                        <i class="icon-star-empty"></i>
                                                                        <i class="icon-star-empty"></i>
                                                                        <i class="icon-star-empty"></i>
                                                                    </span>
                                                            <span>${review.getNumberReviews(1, product.id)} Reviews</span>
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
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
<script type="text/javascript">
    function buy(id, size) {
        var quantityInput = document.getElementById('quantity');
        var enteredQuantity = parseInt(quantityInput.value, 10);
        var sizeSelect = document.getElementById('sizeSelect${product.id}');
        var selectedOption = sizeSelect.options[sizeSelect.selectedIndex];
        var stockForSelectedSize = parseInt(selectedOption.getAttribute('data-stock'), 10);
        if (enteredQuantity > stockForSelectedSize) {
            alert('Quantity exceeds available stock for selected size!');
            return;
        }
        document.f.action = "cart?id=" + id + "&psize=" + size;
        document.f.submit();
    }

</script>