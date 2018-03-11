<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title></title>
</head>
<body>
<form:form method="POST" action="/updateQuestionAction" modelAttribute="editq">
    <form:hidden path="questionId" />
    <form:hidden path="examId" />
    <table>
        <tr>
            <td><form:label path="question">Question</form:label></td>
            <td><form:input path="question" /></td>
        </tr>
        <tr>
            <td><form:label path="marks">Marks</form:label></td>
            <td><form:input path="marks" /></td>
        </tr>
        <tr>
            <td><form:label path="option1">Option 1</form:label></td>
            <td><form:input path="option1" /></td>
        </tr>
        <tr>
            <td><form:label path="option2">Option 2</form:label></td>
            <td><form:input path="option2" /></td>
        </tr>
        <tr>
            <td><form:label path="option3">Option 3</form:label></td>
            <td><form:input path="option3" /></td>
        </tr>
        <tr>
            <td><form:label path="option4">Option 4</form:label></td>
            <td><form:input path="option4" /></td>
        </tr>
        <tr>
            <td><form:label path="answer">Correct Option (enter 1/2/3/4)</form:label></td>
            <td><form:input path="answer" /></td>
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
