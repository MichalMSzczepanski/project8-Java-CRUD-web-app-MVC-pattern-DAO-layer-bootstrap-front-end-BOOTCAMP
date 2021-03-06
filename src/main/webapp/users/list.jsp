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
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">

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
<%--                <div class="d-sm-flex align-items-center justify-content-between mb-4">--%>
<%--                    <h1 class="h3 mb-0 text-gray-800">UsersCRUD</h1>--%>

<%--                </div>--%>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3 d-flex align-items-center justify-content-between">
                            <h6><a class="m-0 font-weight-bold text-primary font-change"  href="/user/list">User list</a></h6>
                            <a href="/user/add" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i class="fas fa-download fa-sm text-white-50"></i> Add User</a>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                    <tr>
                                        <th>Id</th>
                                        <th>User name</th>
                                        <th>Email</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${extractedUsers}" var="user">
                                        <tr>
                                            <td>${user.getId()}</td>
                                            <td>${user.getUserName()}</td>
                                            <td>${user.getEmail()}</td>
                                            <td>
                                                <a class="btn btn-primary" href="/user/show?UserId=${user.getId()}">Read</a>
                                                <a class="btn btn-primary" href="/user/edit?UserId=${user.getId()}">Update</a>
                                                <!-- Button trigger modal -->
                                                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop"> Delete </button>

                                                <!-- Modal -->
                                                <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                                    <div class="modal-dialog modal-dialog-centered">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title" id="staticBackdropLabel">You sure there Skipper?</h5>
                                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <p>Are you sure you want to delete the user <strong>${user.getUserName()}</strong>?</p>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">No, go back</button>
                                                                <a class="btn btn-primary" href="/deleteUser?UserId=${user.getId()}">Yes, I do</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <nav class="d-flex justify-content-center">
                                    <ul class="pagination">
                                        <li class="page-item ${pageNumber == 1 ? "disabled" : ""}">
                                            <a class="page-link" href="/user/list?pageNumber=${pageNumber - 1}${not empty param.search ? "&search=".concat(param.search) : ""}" tabindex="-1">Previous</a>
                                        </li>
                                        <c:if test="${pageNumber != 1}">
                                            <a class="page-link" href="/user/list?pageNumber=${pageNumber - 1}${not empty param.search ? "&search=".concat(param.search) : ""}" tabindex="-1">${pageNumber-1}</a>
                                        </c:if>
                                        <li class="page-item active"><a class="page-link" href="#">${pageNumber} <span class="sr-only">(current)</span></a></li>
                                        <c:if test="${pageNumber != totalNumberOfPage}">
                                            <a class="page-link" href="/user/list?pageNumber=${pageNumber + 1}${not empty param.search ? "&search=".concat(param.search) : ""}" tabindex="-1">${pageNumber+1}</a>
                                        </c:if>
                                        <li class="page-item ${pageNumber == totalNumberOfPages ? "disabled" : ""}">
                                            <a class="page-link" href="/user/list?pageNumber=${pageNumber + 1}${not empty param.search ? "&search=".concat(param.search) : ""}" tabindex="-1">Next</a>
                                        </li>
                                    </ul>
                                </nav>
                            </div>
                        </div>
                    </div>
            </div>
        </div>

<%--external jspf file with PAGE FOOTER --%>
<%@ include file="/footer.jspf" %>

</body>

</html>