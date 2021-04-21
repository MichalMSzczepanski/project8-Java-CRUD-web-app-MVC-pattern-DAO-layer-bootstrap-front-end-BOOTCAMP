<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Login</title>

    <!-- Custom fonts for this template-->
    <link href="<c:url value="/theme/vendor/fontawesome-free/css/all.min.css"/>" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">

    <!-- Custom styles for this template-->
    <link href="<c:url value="/theme/css/sb-admin-2.css"/>" rel="stylesheet">


</head>

<body class="bg-gradient-primary">

<%--&lt;%&ndash;clears cache&ndash;%&gt;--%>
<%--<% response.setHeader("Cache-Control", "no cache, no-store, must-revalidate"); %>--%>


<div class="container">

    <!-- Outer Row -->
    <div class="row justify-content-center">

        <div class="col-xl-5 col-lg-12 col-md-9">

            <div class="card o-hidden border-0 shadow-lg my-5">
                <div class="card-body p-0">
                    <!-- Nested Row within Card Body -->
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="p-5">
                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-4">Hi there Stranger!</h1>
                                    <p>Care to log in?</p>
                                </div>
                                <form class="user d-flex flex-column" action="/login" method="post">
                                    <div class="form-group">
                                        <input type="email" class="form-control form-control-user" name="loginAdminEmail" <c:choose>
                                        <c:when test="${not empty adminEmail}">
                                               value="${adminEmail}"
                                        </c:when>
                                        <c:otherwise>
                                               placeholder="Enter Email Address..."
                                        </c:otherwise>
                                        </c:choose>
                                        >
                                    </div>
                                    <c:choose>
                                        <c:when test="${not empty emailIncorrect}">
                                            <div class="alert alert-danger" role="alert">
                                                Wrong email, try again.
                                            </div>
                                        </c:when>
                                    </c:choose>
                                    <div class="form-group">
                                        <input type="password" class="form-control form-control-user" name="loginAdminPassword" placeholder="Password">
                                    </div>
                                    <c:choose>
                                        <c:when test="${not empty passwordIncorrect}">
                                            <div class="alert alert-danger" role="alert">
                                                Password incorrect, try again.
                                            </div>
                                        </c:when>
                                    </c:choose>
<%--                                    <div class="form-group">--%>
<%--                                        <div class="custom-control custom-checkbox small">--%>
<%--                                            <input type="checkbox" class="custom-control-input" id="customCheck">--%>
<%--                                            <label class="custom-control-label" for="customCheck">Remember Me</label>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>

                                    <button type="submit" class="btn btn-primary btn-user w-50 btn-block align-self-center">
                                        Login
                                    </button>
<%--                                    <hr>--%>
<%--                                    <a href="index.html" class="btn btn-google btn-user btn-block">--%>
<%--                                        <i class="fab fa-google fa-fw"></i> Login with Google--%>
<%--                                    </a>--%>
<%--                                    <a href="index.html" class="btn btn-facebook btn-user btn-block">--%>
<%--                                        <i class="fab fa-facebook-f fa-fw"></i> Login with Facebook--%>
<%--                                    </a>--%>
                                </form>
<%--                                <hr>--%>
<%--                                <div class="text-center">--%>
<%--                                    <a class="small" href="forgot-password.html">Forgot Password?</a>--%>
<%--                                </div>--%>
                                <div class="text-center mt-3">
                                    <a class="small" href="mailto:michal.m.szczepanski@gmail.com">Request an account!</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </div>

</div>

<!-- Bootstrap core JavaScript-->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="js/sb-admin-2.min.js"></script>

</body>

</html>
