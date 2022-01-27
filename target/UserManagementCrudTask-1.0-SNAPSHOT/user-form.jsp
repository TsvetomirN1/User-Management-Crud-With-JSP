<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
         style="background-color: darkcyan">
        <div>
            <a class="navbar-brand"> User Management App </a>
        </div>

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/list"
                   class="nav-link">Users</a></li>
        </ul>
    </nav>
</header>
<br>
<div class="container col-md-5">
    <div class="card">
        <div class="card-body">
            <c:if test="${userEntity != null}">
            <form action="update" method="post">
                </c:if>
                <c:if test="${userEntity == null}">
                <form action="insert" method="post">

                    </c:if>

                    <caption>
                        <h2>
                            <c:if test="${userEntity != null}">
                                Edit User
                            </c:if>
                            <c:if test="${userEntity == null}">
                                Add New User
                            </c:if>
                        </h2>
                    </caption>

                    <c:if test="${userEntity != null}">
                        <input type="hidden" name="id" value="<c:out value='${userEntity.id}' />"/>
                    </c:if>

                    <fieldset class="form-group">
                        <label>FirstName</label> <input type="text"
                                                        value="<c:out value='${userEntity.firstName}' />"
                                                        class="form-control"
                                                        name="first_name" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>LastName</label> <input type="text"
                                                       value="<c:out value='${userEntity.lastName}' />"
                                                       class="form-control"
                                                       name="last_name" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>DateOfBirth</label> <input type="date"
                                                          value="<c:out value='${userEntity.dateOfBirth}' />"
                                                          class="form-control"
                                                          name="date_of_birth" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>PhoneNumber</label> <input type="text"
                                                          value="<c:out value='${userEntity.phoneNumber}' />"
                                                          class="form-control"
                                                          name="phone_number" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Email</label> <input type="text"
                                                    value="<c:out value='${userEntity.email}' />"
                                                    class="form-control"
                                                    name="email">
                    </fieldset>


                    <button type="submit" class="btn btn-success">Save</button>
                </form>
        </div>
    </div>
</div>
</body>
</html>
