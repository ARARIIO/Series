<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" type="text/css" href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2>Registration Form</h2>
    <form:form action="${pageContext.request.contextPath}/register" modelAttribute="user" method="post">
        <div class="form-group">
            <form:label path="email">Email:</form:label>
            <form:input type="email" path="email" class="form-control" required="true"/>
        </div>
        <div class="form-group">
            <form:label path="password">Password:</form:label>
            <form:password path="password" class="form-control" required="true"/>
        </div>
        <button type="submit" class="btn btn-primary">Register</button>
    </form:form>

</div>
</body>
</html>
