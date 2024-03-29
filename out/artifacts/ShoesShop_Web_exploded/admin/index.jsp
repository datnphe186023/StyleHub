<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Quản trị Admin</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Main CSS-->
    <link rel="stylesheet" type="text/css" href="admin/css/main.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
    <!-- or -->
    <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
    <!-- Font-icon css-->
    <link rel="stylesheet" type="text/css"
          href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
    <!-- Include Chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body onload="time()" class="app sidebar-mini rtl">
<!-- Navbar-->
<header class="app-header">
    <!-- Sidebar toggle button--><a class="app-sidebar__toggle" href="#" data-toggle="sidebar"
                                    aria-label="Hide Sidebar"></a>
    <!-- Navbar Right Menu-->
    <ul class="app-nav">


        <!-- User Menu-->
        <li><a class="app-nav__item" href="home"><i class='bx bx-log-out bx-rotate-180'></i> Home </a>
        <li><a class="app-nav__item" href="login"><i class='bx bx-log-out bx-rotate-180'></i> Logout </a>

        </li>
    </ul>
</header>
<!-- Sidebar menu-->
<div class="app-sidebar__overlay" data-toggle="sidebar"></div>
<aside class="app-sidebar">
    <div class="app-sidebar__user"><img class="app-sidebar__user-avatar" src="images/${account.image}" width="50px"
                                        alt="User Image">
        <div>
            <p class="app-sidebar__user-name"><b>${sessionScope.account.fullName}</b></p>
            <p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
        </div>
    </div>
    <hr>
    <ul class="app-menu">
        <li><a class="app-menu__item" href="admin"><i class='app-menu__icon bx bx-tachometer'></i><span
                class="app-menu__label">Bảng điều khiển</span></a></li>
        <li><a class="app-menu__item" href="customermanager"><i class='app-menu__icon bx bx-user-voice'></i><span
                class="app-menu__label">Quản lý khách hàng</span></a></li>
        <li><a class="app-menu__item" href="productmanager"><i
                class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lý sản phẩm</span></a>
        </li>
        <li><a class="app-menu__item" href="ordermanager"><i class='app-menu__icon bx bx-task'></i><span
                class="app-menu__label">Quản lý đơn hàng</span></a></li>
        <li><a class="app-menu__item"
               href="https://docs.google.com/spreadsheets/d/1fOvH-dHByIxizCOyuNf8Jkk0aNm1e-jIQw3ObxfjrWw"
               target="_blank"><i class='app-menu__icon bx bx-task'></i><span
                class="app-menu__label">Kiểm tra phản hồi</span></a></li>
    </ul>
</aside>
<main class="app-content">
    <div class="row">
        <div class="col-md-12">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb">
                    <li class="breadcrumb-item"><a href="#"><b>Bảng điều khiển</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>
        </div>
    </div>
    <!-- Chart Canvas Element -->
    <canvas id="dailyRevenueChart" style="width: 100%; height: 400px;"></canvas>
    <!-- Chart Script -->
    <script type="text/javascript">
        var labels = [];
        var incomeData = [];
        var profitData = [];
        var currentDate = new Date();
        for (var i = 29; i >= 0; i--) {
            var date = new Date(currentDate);
            date.setDate(date.getDate() - i);
            var formattedDate = ('0' + date.getDate()).slice(-2) + '/' + ('0' + (date.getMonth() + 1)).slice(-2);
            labels.push(formattedDate);
        }
        <c:forEach var="income" items="${requestScope.dailyRevenue}">
        incomeData.unshift(${income});
        </c:forEach>
        <c:forEach var="profit" items="${requestScope.dailyProfit}">
        profitData.unshift(${profit});
        </c:forEach>
        var data = {
            labels: labels,
            datasets: [{
                label: "Daily Revenue",
                backgroundColor: "rgba(255, 99, 132, 0.2)",
                borderColor: "rgba(255, 99, 132, 1)",
                borderWidth: 1,
                data: incomeData
            },
                {
                    label: "Daily Profit",
                    backgroundColor: "rgba(54, 162, 235, 0.2)",
                    borderColor: "rgba(54, 162, 235, 1)",
                    borderWidth: 1,
                    data: profitData
                }]
        };
        var ctx = document.getElementById("dailyRevenueChart").getContext("2d");
        var myChart = new Chart(ctx, {
            type: 'line',
            data: data,
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            }
        });
    </script>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="row">
                <div class="col-md-6">
                    <div class="widget-small primary coloured-icon"><i class="icon bx bxs-dollar-circle fa-3x"></i>
                        <div class="info">
                            <h4>Doanh thu tháng này</h4>
                            <fmt:formatNumber value="${requestScope.thisMonthRevenue}" pattern="#,##0đ"
                                              var="thisMonthRevenue"/>
                            <p><b>${thisMonthRevenue}</b></p>
                            <p class="info-tong">Tổng doanh thu trong tháng này.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="widget-small danger coloured-icon"><i class='icon bx bxs-dollar-circle fa-3x'></i>
                        <div class="info">
                            <h4>Lợi nhuận tháng này</h4>
                            <fmt:formatNumber value="${requestScope.thisMonthProfit}" pattern="#,##0đ"
                                              var="thisMonthProfit"/>
                            <p><b>${thisMonthProfit}</b></p>
                            <p class="info-tong">Tổng lợi nhuận trong tháng này.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="widget-small primary coloured-icon"><i class="icon bx bxs-dollar-circle fa-3x"></i>
                        <div class="info">
                            <h4>Doanh thu tháng trước</h4>
                            <fmt:formatNumber value="${requestScope.lastMonthRevenue}" pattern="#,##0đ"
                                              var="lastMonthRevenue"/>
                            <p><b>${lastMonthRevenue}</b></p>
                            <p class="info-tong">Tổng doanh thu trong tháng trước.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="widget-small danger coloured-icon"><i class='icon bx bxs-dollar-circle fa-3x'></i>
                        <div class="info">
                            <h4>Lợi nhuận tháng trước</h4>
                            <fmt:formatNumber value="${requestScope.lastMonthProfit}" pattern="#,##0đ"
                                              var="lastMonthProfit"/>
                            <p><b>${lastMonthProfit}</b></p>
                            <p class="info-tong">Tổng doanh thu trong tháng trước.</p>
                        </div>
                    </div>
                </div>
                <!-- col-6 -->
                <div class="col-md-6">
                    <a href="customermanager" style="text-decoration: none; color: inherit;">
                    <div class="widget-small primary coloured-icon"><i class='icon bx bxs-user-account fa-3x'></i>
                        <div class="info">
                            <h4>Tổng khách hàng</h4>
                            <p><b>${requestScope.totalUser} khách hàng</b></p>
                            <p class="info-tong">Tổng số khách hàng được quản lý.</p>
                        </div>
                    </div>
                    </a>
                </div>
                <!-- col-6 -->
                <div class="col-md-6">
                    <a href="productmanager" style="text-decoration: none; color: inherit;">
                    <div class="widget-small primary coloured-icon">
                        <i class="icon bx bxs-data fa-3x"></i>
                        <div class="info">
                            <h4>Tổng sản phẩm</h4>
                            <p><b>${requestScope.totalProduct} sản phẩm</b></p>
                            <p class="info-tong">Tổng số sản phẩm được quản lý.</p>
                        </div>
                    </div>
                    </a>
                </div>
                <!-- col-6 -->
                <div class="col-md-6">
                    <a href="ordermanager" style="text-decoration: none; color: inherit;">
                    <div class="widget-small warning coloured-icon">
                        <i class="icon bx bxs-shopping-bags fa-3x"></i>
                        <div class="info">
                            <h4>Tổng đơn hàng</h4>
                            <p><b>${requestScope.totalOrder} đơn hàng</b></p>
                            <p class="info-tong">Tổng số hóa đơn bán hàng trong tháng.</p>
                        </div>
                    </div>
                    </a>
                </div>
                <!-- col-6 -->
                <div class="col-md-6">
                    <a href="productmanager?action=lowProduct" style="text-decoration: none; color: inherit;">
                        <div class="widget-small danger coloured-icon">
                            <i class='icon bx bxs-error-alt fa-3x'></i>
                            <div class="info">
                                <h4>Sắp hết hàng</h4>
                                <p><b>${requestScope.totalLowStockProduct} sản phẩm</b></p>
                                <p class="info-tong">Số sản phẩm cảnh báo hết cần nhập thêm.</p>
                            </div>
                        </div>
                    </a>
                </div>
                <!-- col-12 -->
                <div class="col-md-12">
                    <div class="tile">
                        <h3 class="tile-title">Đơn hàng hôm nay</h3>
                        <div>
                            <table class="table table-bordered">
                                <thead>
                                <tr>
                                    <th>ID đơn hàng</th>
                                    <th>ID Khách hàng</th>
                                    <th>Số điện thoại</th>
                                    <th>Địa chỉ</th>
                                    <th>Ngày mua</th>
                                    <th>Tổng tiền</th>
                                    <th>Tình Trạng</th>
                                    <th>Chức năng</th>

                                </tr>
                                </thead>
                                <tbody>
                                <jsp:useBean id="database" class="model.customer.CustomerDAO"/>
                                <c:forEach items="${requestScope.todayOrder}" var="order">
                                    <tr>
                                        <td>${order.id}</td>
                                        <td>${order.customerId}</td>
                                        <td>(+84)${database.getCustomerById(order.customerId).phoneNumber}</td>
                                        <td>
                                            <c:set var="addressParts"
                                                   value="${fn:split(database.getAddressForAdmin(order.address), '%')}"/>
                                            <c:forEach var="part" items="${addressParts}">
                                                ${part}<br>
                                            </c:forEach>
                                        </td>
                                        <td>${order.created}</td>
                                        <fmt:formatNumber value="${order.totalPrice}" pattern="#,##0đ"
                                                          var="totalPrice"/>
                                        <td>${totalPrice}</td>
                                        <td><span class="badge bg-success">${order.status}</span></td>
                                        <td>
                                            <a style=" color: rgb(245 157 57);background-color: rgb(251 226 197); padding: 5px;border-radius: 5px;"
                                               href="ordermanager?action=showdetail&orderId=${order.id}"><i
                                                    class="fa"></i>Chi tiết đơn hàng</a></td>
                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>
                        </div>
                        <!-- / div trống-->
                    </div>
                </div>
                <!-- / col-12 -->
            </div>
        </div>
    </div>


    <div class="text-center" style="font-size: 13px">
        <p><b>Copyright
            <script type="text/javascript">
                document.write(new Date().getFullYear());
            </script>
            Phần mềm quản lý Website
        </b></p>
    </div>
</main>
<script src="admin/js/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
<script src="admin/js/popper.min.js"></script>
<script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
<!--===============================================================================================-->
<script src="admin/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
<script src="admin/js/main.js"></script>
<!--===============================================================================================-->
<script src="admin/js/plugins/pace.min.js"></script>
<!--===============================================================================================-->
<!--===============================================================================================-->
<script type="text/javascript">
    var data = {
        labels: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6"],
        datasets: [{
            label: "Dữ liệu đầu tiên",
            fillColor: "rgba(255, 213, 59, 0.767), 212, 59)",
            strokeColor: "rgb(255, 212, 59)",
            pointColor: "rgb(255, 212, 59)",
            pointStrokeColor: "rgb(255, 212, 59)",
            pointHighlightFill: "rgb(255, 212, 59)",
            pointHighlightStroke: "rgb(255, 212, 59)",
            data: [20, 59, 90, 51, 56, 100]
        },
            {
                label: "Dữ liệu kế tiếp",
                fillColor: "rgba(9, 109, 239, 0.651)  ",
                pointColor: "rgb(9, 109, 239)",
                strokeColor: "rgb(9, 109, 239)",
                pointStrokeColor: "rgb(9, 109, 239)",
                pointHighlightFill: "rgb(9, 109, 239)",
                pointHighlightStroke: "rgb(9, 109, 239)",
                data: [48, 48, 49, 39, 86, 10]
            }
        ]
    };
    var ctxl = $("#lineChartDemo").get(0).getContext("2d");
    var lineChart = new Chart(ctxl).Line(data);

    var ctxb = $("#barChartDemo").get(0).getContext("2d");
    var barChart = new Chart(ctxb).Bar(data);
</script>
<script type="text/javascript">
    //Thời Gian
    function time() {
        var today = new Date();
        var weekday = new Array(7);
        weekday[0] = "Chủ Nhật";
        weekday[1] = "Thứ Hai";
        weekday[2] = "Thứ Ba";
        weekday[3] = "Thứ Tư";
        weekday[4] = "Thứ Năm";
        weekday[5] = "Thứ Sáu";
        weekday[6] = "Thứ Bảy";
        var day = weekday[today.getDay()];
        var dd = today.getDate();
        var mm = today.getMonth() + 1;
        var yyyy = today.getFullYear();
        var h = today.getHours();
        var m = today.getMinutes();
        m = checkTime(m);
        nowTime = h + ":" + m + "";
        if (dd < 10) {
            dd = '0' + dd
        }
        if (mm < 10) {
            mm = '0' + mm
        }
        today = day + ', ' + dd + '/' + mm + '/' + yyyy;
        tmp = '<span class="date"> ' + today + ' - ' + nowTime +
            '</span>';
        document.getElementById("clock").innerHTML = tmp;
        clocktime = setTimeout("time()", "1000", "Javascript");

        function checkTime(i) {
            if (i < 10) {
                i = "0" + i;
            }
            return i;
        }
    }
</script>
</body>

</html>