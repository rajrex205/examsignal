<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="pageTitle" scope="request" value="Profile"/>
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
                                <img alt="User Pic" src="${pageContext.request.contextPath}/user-images/${profilePicture}" class="img-responsive" align="center">
                                <form method="POST" action="/addPhoto" enctype="multipart/form-data">
                                    Upload Profile Picture: <input type="file" name="photo" id="photo">
                                    <input type="submit" value="Upload"/>
                                </form>
                                <div class="user-info">
                                    <a href="#">Change Password</a>
                                    <p class="unique-id">
                                        My Unique id
                                        <span>${profile.uniqueId}</span>
                                    </p>
                                    <p>
                                        My Earned Points
                                        <span>${profile.points}</span>
                                    </p>
                                </div>
                            </div>

                            <div class=" col-md-9 col-lg-9 ">
                                <table class="table table-user-information">
                                    <tbody>
                                        <tr>
                                            <td colspan="2">${profile.firstName} ${profile.lastName}</td>
                                        </tr>
                                        <tr>
                                            <td>Email</td>
                                            <td>: ${profile.email}</td>
                                        </tr>
                                        <tr>
                                            <td>Phone No</td>
                                            <td>: ${profile.phone}</td>
                                        </tr>
                                        <tr>
                                            <td>Gender</td>
                                            <td>: ${profile.gender}</td>
                                        </tr>
                                        <tr>
                                            <td>Highest Education</td>
                                            <td>: ${profile.highestEducation}</td>
                                        </tr>
                                        <tr>
                                            <td>Address</td>
                                            <td>: ${profile.address}</td>
                                        </tr>
                                        <tr>
                                            <td>Preferred Courses</td>
                                            <td>: ${profile.preferredCourses}</td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="pull-right" style="margin-top: -10px">
                                    <a href="/editUserProfile">Edit Profile</a>
                                </div>
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
</body>
</html>