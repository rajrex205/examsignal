<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="Edit Profile"/>
<!DOCTYPE html>
<html>
<!-- Common Header -->
<jsp:include page="../../layout/head.jsp" />
<body>
<div class="container-full">
    <!-- Common Header -->
    <jsp:include page="../../layout/header.jsp" />

    <!-- Student Header Links -->
    <jsp:include page="../../layout/student-header.jsp" />

    <div class="container">
        <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-9 col-lg-9 center-block">
                <div class="panel panel-info">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-3 col-lg-3">
                                <img alt="User Pic" src="${pageContext.request.contextPath}/resources/images/default-image.jpg" class="img-responsive" align="center">

                                <div class="user-info">
                                    <a href="#">Change Password</a>
                                    <p class="unique-id">
                                        My Unique id
                                        <span>${uniqueID}</span>
                                    </p>
                                    <p>
                                        My Earned Points
                                        <span>${points}</span>
                                    </p>
                                </div>
                            </div>

                            <div class=" col-md-9 col-lg-9 ">
                                <form:form method="POST" action="/editUserProfileAction" modelAttribute="editForm">
                                <table class="table table-user-information">
                                    <tbody>
                                        <tr style="font-size: 16px">
                                            <td>First Name *</td>
                                            <td><form:input path="firstName" cssClass="form-control"  /></td>
                                        </tr>
                                        <tr>
                                            <td>Last Name *</td>
                                            <td><form:input path="lastName" cssClass="form-control"/></td>
                                        </tr>
                                        <tr>
                                            <td>Email *</td>
                                            <td>
                                                <form:input path="email" cssClass="form-control" readonly="${emailReadOnly}" />

                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Phone No *</td>
                                            <td><form:input path="phone" cssClass="form-control" /></td>
                                        </tr>
                                        <tr>
                                            <td>Gender *</td>
                                            <td>
                                                <form:radiobutton path="gender" value="Male" />Male
                                                <form:radiobutton path="gender" value="Female" />Female
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Highest Education *</td>
                                            <td>
                                                <form:select path="highestEducation" class="form-control selectpicker">
                                                    <form:options items="${formHigherEducation}" />
                                                </form:select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Address</td>
                                            <td>
                                                <form:input path="address" cssClass="form-control"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Preferred Courses *</td>
                                            <td>
                                                <form:select path="preferredCourses" cssClass="form-control selectpicker" multiple="true">
                                                    <form:options items="${formPreferredCourse}" itemValue="courseId" itemLabel="courseName" />
                                                </form:select>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                    ${error}
                                    <div class="controls pull-right">
                                        <input type="submit" value="Update Profile" name="update" id="update" class="btn btn-blue" />
                                    </div>
                                </form:form>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Common Footer Links -->
    <jsp:include page="../../layout/footer.jsp" />
</div><!-- container-full -->
<!-- Common JS Imports -->
<jsp:include page="../../layout/common-js-import.jsp" />
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-select.js"></script>
<script type="text/javascript">
    jQuery('.selectpicker').selectpicker();
</script>

</body>
</html>