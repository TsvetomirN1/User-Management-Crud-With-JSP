<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management Application</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>
<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: beige">
        <div>
            <a class="navbar-brand"> User Management App </a>
        </div>

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/sortByLastName"
                   class="nav-link">Users</a></li>
                   class="nav-link">SortByDateOfBirth</a></li>
        </ul>
    </nav>
</header>
<br>

<div class="row">

    <div class="container">
        <h3 class="text-center">List of Users</h3>
        <hr>
        <div class="container text-left">

            <a href="<%=request.getContextPath()%>/new" class="btn btn-success">Add
                New User</a>
        </div>
        <br>
        <div class="row">
            <form action="sorting" method="post" class="form-inline row">
                <div class="form-group">
                    <span>Search by Last name </span>
                    <input class="form-control" name="search_lname" type="text">
                    <span>Last name order</span>
                    <select class="form-control" name="lname_sort">
                        <option value="asc">Ascending</option>
                        <option value="desc">Descending</option>
                    </select>
                    <span>Birth day order</span>
                    <select class="form-control" name="bday_sort">
                        <option value="asc">Ascending</option>
                        <option value="desc">Descending</option>
                    </select>
                    <input class="form-control" type="submit" value="Search">
                </div>
            </form>
        </div>

        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>DateOfBirth</th>
                <th>PhoneNumber</th>
                <th>Email</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="userEntity" items="${sortByLastName}">

                <tr>
                    <td><c:out value="${userEntity.id}"/></td>
                    <td><c:out value="${userEntity.firstName}"/></td>
                    <td><c:out value="${userEntity.lastName}"/></td>
                    <td><c:out value="${userEntity.dateOfBirth}"/></td>
                    <td><c:out value="${userEntity.phoneNumber}"/></td>
                    <td><c:out value="${userEntity.email}"/></td>
                    <td><a href="edit?id=<c:out value='${userEntity.id}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp; <a
                                href="delete?id=<c:out value='${userEntity.id}' />">Delete</a></td>
                </tr>
            </c:forEach>

            </tbody>

        </table>
    </div>
</div>
</body>
</html>
