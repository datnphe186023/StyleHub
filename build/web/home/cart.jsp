<%@page contentType="text/html" pageEncoding="UTF-8" %>
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

    </head>
    <body>

        <%
            if(session.getAttribute("account") == null){
                response.sendRedirect("account");
            }
        %>


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
                            <div class="col-sm-2 col-md-2" style="font-weight: 400; font-family: 'Rokkitt', Georgia, serif; text-transform: uppercase; font-size: 15px; letter-spacing: 2px;">
                                <%
                                    if (session.getAttribute("account") != null) {
                                %>
                                <a href="accountDetail.jsp">${sessionScope.account.fullName}</a>
                                <%
                                }else{
                                %>
                                <a href="account" style="margin-right: 10px;">Login</a>
                                <a href="register">Register</a>
                                <%
                                    }
                                %>
                            </div>
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

            <div class="breadcrumbs">
                <div class="container">
                    <div class="row">
                        <div class="col">
                            <p class="bread"><span><a href="index.jsp">Home</a></span> / <span>Shopping Cart</span></p>
                        </div>
                    </div>
                </div>
            </div>


            <div class="colorlib-product">
                <div class="container">
                    <div class="row row-pb-lg">
                        <div class="col-md-10 offset-md-1">
                            <div class="process-wrap">
                                <div class="process text-center active">
                                    <p><span>01</span></p>
                                    <h3>Shopping Cart</h3>
                                </div>
                                <div class="process text-center">
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
                    <div class="row row-pb-lg">
                        <div class="col-md-12">
                            <div class="product-name d-flex">
                                <div class="one-forth text-left px-4">
                                    <span>Product</span>
                                </div>
                                <div class="one-eight text-center">
                                    <span>Size</span>
                                </div>
                                <div class="one-eight text-center">
                                    <span>Price</span>
                                </div>
                                <div class="one-eight text-center">
                                    <span>Quantity</span>
                                </div>
                                <div class="one-eight text-center px-4">
                                    <span>Remove</span>
                                </div>
                            </div>
                            <c:set var="cart" value="${sessionScope.cart}"/>
                            <c:set var="t" value="0"/>
                            <c:set var="total" value="0"/>
                            <c:forEach items="${cart.items}" var="i">
                                <c:set var="t" value="${t+1}"/>
                                <c:set var="total" value="${total+i.product.outPrice*i.quantity}"/>
                                <div class="product-cart d-flex">
                                    <div class="one-forth">
                                        <c:set var="image" value="images/${i.product.images.get(0)}"/>
                                        <a href="${pageContext.request.contextPath}/product?productId=${i.product.id}">
                                            <img class="product-img" src="<c:url value='/images/${i.product.images.get(0)}'/>" class="img-fluid" alt="${i.product.title}">
                                            <div class="display-tc">
                                                <h3>${i.product.title}</h3>
                                            </div>
                                        </a>

                                    </div>
                                    <div class="one-eight text-center">
                                        <div class="display-tc">
                                            <span class="price">${i.size}</span>
                                        </div>
                                    </div>
                                    <div class="one-eight text-center">
                                        <div class="display-tc">
                                            <span class="price">${i.product.outPrice}</span>
                                        </div>
                                    </div>
                                    <div class="one-eight text-center">
                                        <div class="display-tc">
                                            <button style="display:inline-block;"><a
                                                    href="process?num=-1&id=${i.product.id}&psize=${i.size}">-</a></button>
                                            <input style="display:inline-block;" name="quantity"
                                                   class="form-control input-number text-center" type="text"
                                                   readonly value="${i.quantity}"/>
                                            <button style="display:inline-block;"><a
                                                    href="process?num=1&id=${i.product.id}&psize=${i.size}">+</a></button>
                                        </div>
                                    </div>
                                    <div class="one-eight text-center">
                                        <div class="display-tc">
                                            <a href="process?num=${i.quantity-i.quantity*2}&id=${i.product.id}&psize=${i.size}"
                                               class="closed"></a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="row row-pb-lg">
                        <div class="col-md-12">
                            <div class="total-wrap">
                                <div class="row">
                                    <div class="col-sm-8">
                                    </div>
                                    <div class="col-sm-4 text-center">
                                        <div class="total">
                                            <div class="grand-total">
                                                <p><span><strong>Total:</strong></span> <span>${total}</span></p>
                                            </div>
                                            <form action="checkout" method="get">
                                                <input type="submit" value="Check out">
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-8 offset-sm-2 text-center colorlib-heading colorlib-heading-sm">
                            <h2>Related Products</h2>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3 col-lg-3 mb-4 text-center">
                            <div class="product-entry border">
                                <a href="#" class="prod-img">
                                    <img src="images/item-1.jpg" class="img-fluid" alt="Free html5 bootstrap 4 template">
                                </a>
                                <div class="desc">
                                    <h2><a href="#">Women's Boots Shoes Maca</a></h2>
                                    <span class="price">$139.00</span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3 col-lg-3 mb-4 text-center">
                            <div class="product-entry border">
                                <a href="#" class="prod-img">
                                    <img src="images/item-2.jpg" class="img-fluid" alt="Free html5 bootstrap 4 template">
                                </a>
                                <div class="desc">
                                    <h2><a href="#">Women's Minam Meaghan</a></h2>
                                    <span class="price">$139.00</span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3 col-lg-3 mb-4 text-center">
                            <div class="product-entry border">
                                <a href="#" class="prod-img">
                                    <img src="images/item-3.jpg" class="img-fluid" alt="Free html5 bootstrap 4 template">
                                </a>
                                <div class="desc">
                                    <h2><a href="#">Men's Taja Commissioner</a></h2>
                                    <span class="price">$139.00</span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3 col-lg-3 mb-4 text-center">
                            <div class="product-entry border">
                                <a href="#" class="prod-img">
                                    <img src="images/item-4.jpg" class="img-fluid" alt="Free html5 bootstrap 4 template">
                                </a>
                                <div class="desc">
                                    <h2><a href="#">Russ Men's Sneakers</a></h2>
                                    <span class="price">$139.00</span>
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
                                <li><a href="https://maps.app.goo.gl/z7MAnSiKahsZu5V79">Site maps</a></li>
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

