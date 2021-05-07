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

    <title>SB Admin 2 - Dashboard</title>

    <!-- Custom fonts for this template-->
    <link href="<c:url value="/theme/vendor/fontawesome-free/css/all.min.css"/>" rel="stylesheet" type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="<c:url value="/theme/css/sb-admin-2.css"/>" rel="stylesheet">

</head>

<body id="page-top">

<%--clears cache--%>
<% response.setHeader("Cache-Control", "no cache, no-store, must-revalidate"); %>

<%--external jspf file with PAGE HEADER --%>
<%@ include file="/header.jspf" %>

<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">UsersCRUD</h1>
        <a href="/user/list" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i
                class="fas fa-download fa-sm text-white-50"></i> Lista użytkowników </a>
    </div>

    <!-- DataTales Example -->
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary font-change">Edycja użytkownika ${userName}</h6>
        </div>
        <div class="card-body">
            <form method="post">
                <div class="form-group">
                    <label>Name</label>
                    <input type="text" class="form-control" name="newUserName" value="${userName}">
                </div>
                <c:choose>
                    <c:when test="${not empty userUserNameMissing}">
                        <div class="alert alert-danger" role="alert">
                            You forgot to add a username, try again.
                        </div>
                    </c:when>
                </c:choose>
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" class="form-control" name="newUserEmail" value="${userEmail}">
                </div>
                <c:choose>
                    <c:when test="${not empty userEmailMissing}">
                        <div class="alert alert-danger" role="alert">
                            You forgot to add an email, try again.
                        </div>
                    </c:when>
                    <c:when test="${not empty userEmailOccupied}">
                        <div class="alert alert-danger" role="alert">
                            That email's occupied, try again.
                        </div>
                    </c:when>
                </c:choose>
                <div class="form-group">
                    <label>Password</label>
                    <input type="password" class="form-control" name="newUserPassword" placeholder="Hasło użytkownika">
                </div>
                <div class="form-group">
                    <label>Confirm password</label>
                    <input type="password" class="form-control" name="newUserPasswordConfirm" placeholder="Potwierdź hasło">
                </div>
                <c:choose>
                    <c:when test="${not empty passwordsDifferent}">
                        <div class="alert alert-danger" role="alert">
                            Passwords don't match, try again
                        </div>
                    </c:when>
                </c:choose>
                <div class="form-check">
                    <input type="checkbox" class="form-check-input" id="exampleCheck1">
                    <label class="form-check-label" for="exampleCheck1"> Sign me up to your newsletter</label>
                </div>
                <br>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>

    </div>
    <a class="btn btn-primary" href="/user/list">Back</a>

</div>
<!-- /.container-fluid -->

</div>
<!-- End of Main Content -->

<%--external jspf file with PAGE FOOTER --%>
<%@ include file="/footer.jspf" %>

</body>

</html>