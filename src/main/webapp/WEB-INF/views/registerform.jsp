<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title></title>
</head>
<body>
<h2>Registration Form</h2>
<form:form method="POST" action="/register">
    <table>
        <tr>
            <td><form:label path="email">EmailId</form:label></td>
            <td><form:input path="email" /></td>
        </tr>
        <tr>
            <td><form:label path="password">Password</form:label></td>
            <td><form:password path="password" /></td>
        </tr>
        <tr>
            <td><form:label path="confirmPassword">Confirm Password</form:label></td>
            <td><form:password path="confirmPassword" /></td>
        </tr>
        <tr>
            <td><form:label path="firstName">First Name</form:label></td>
            <td><form:input path="firstName" /></td>
        </tr>
        <tr>
            <td><form:label path="lastName">Last Name</form:label></td>
            <td><form:input path="lastName" /></td>
        </tr>
        <tr>
            <td><form:label path="gender">Gender</form:label></td>
            <td>
                <form:radiobutton path="gender" value="M" />Male
                <form:radiobutton path="gender" value="F" />Female
            </td>
        </tr>
        <tr>
            <td><form:label path="phone">Phone</form:label></td>
            <td><form:input path="phone" /></td>
        </tr>
        <tr>
            <td><form:label path="address">Address(Optional)</form:label></td>
            <td><form:input path="address" /></td>
        </tr>
        <tr>
            <td><form:label path="highestEducation">Highest Education</form:label></td>
            <td><form:input path="highestEducation" /></td>
        </tr>
        <tr>
            <td><form:label path="preferredCourse">Preferred Course</form:label></td>
            <td><form:input path="preferredCourse" /></td>
        </tr>
        <tr>
            <td><form:label path="referralCode">Referral Code</form:label></td>
            <td><form:input path="referralCode" /></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Submit"/>
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>