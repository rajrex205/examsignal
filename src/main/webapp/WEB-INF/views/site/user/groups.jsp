<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="pageTitle" scope="request" value="Groups"/>
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
            <div class="col-xs-12 col-sm-12 col-md-10 col-lg-10 center-block">
                <!-- Subscribe Group | Starts here -->
                <div class="subscribe-group clearfix">
                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 add-group">
                        <div class="col-xs-12 col-sm-12 col-md-9 col-lg-9 center-block">
                            <h1>Subscribe</h1>
                            <form method="POST" action="/subscribeGroupAction">
                                <div class="control-group">
                                    <label class="control-label pull-right">to a NEW GROUP</label>
                                    <div class="controls">
                                        <input type="text" name="group" id="group" class="form-control" placeholder="Enter group's name...">
                                    </div>
                                    ${subscribeResponse}
                                </div>
                                <!-- Button -->
                                <div class="control-group pull-right">
                                    <div class="controls">
                                        <input id="subscribe" name="subscribe" class="btn btn-black" type="submit" value="Subscribe"/>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 groups">
                        <div class="col-xs-12 col-sm-12 col-md-9 col-lg-9 center-block">
                            <h2>Groups you are subscribed to!</h2>
                        </div>
                        <div class="left-border">
                            <div class="col-xs-12 col-sm-12 col-md-9 col-lg-9 center-block">
                                <div class="group-list">
                                    <c:if test="${empty subscribedGroupResponse}">
                                        None of the Groups subscribed.
                                    </c:if>
                                    <c:if test="${not empty subscribedGroupResponse}">
                                        <c:forEach var="s" items="${subscribedGroupResponse}">
                                            <div class="span8">${s.groupName}
                                                <a href="/deleteGroup?gid=${s.adminId}" class="pull-right">
                                                    <img src="${pageContext.request.contextPath}/resources/images/delete-icon.png">
                                                </a>
                                            </div>
                                        </c:forEach>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
                <!-- Subscribe Group | Ends here -->

                <!-- Add Course | Starts here -->
                <div class="course-block clearfix">
                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 add-course">
                        <div class="col-xs-12 col-sm-12 col-md-9 col-lg-9 center-block">
                            <h1>Add <span>a COURSE</span></h1>
                            <form:form method="POST" action="/addUserCourse" modelAttribute="addCourseForm">
                                <div class="control-group">
                                    <div class="controls">
                                        <form:select class="selectpicker form-control" multiple="multiple" path="courseIDs" items="${addableCourses}" itemValue="id" itemLabel="CourseName" />
                                    </div>
                                </div>
                                <!-- Button -->
                                <div class="control-group pull-right">
                                    <div class="controls">
                                        <input id="add" name="add" class="btn btn-black" type="submit" value="Add"/>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>

                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 groups">
                        <div class="col-xs-12 col-sm-12 col-md-9 col-lg-9 center-block">
                            <h2>Courses you are interested in!</h2>
                        </div>
                        <div class="left-border">
                            <div class="col-xs-12 col-sm-12 col-md-9 col-lg-9 center-block">
                                <div class="group-list">
                                    <c:if test="${empty preferredCourses}">
                                        None of the Courses found.
                                    </c:if>
                                    <c:if test="${not empty preferredCourses}">
                                        <c:forEach var="p" items="${preferredCourses}">
                                            <div class="span8">${p.courseName} <i>(${p.groupName})</i>
                                                <a href="/unsubscribeCourse?cid=${p.courseId}" class="pull-right">
                                                    <img src="${pageContext.request.contextPath}/resources/images/delete-icon.png">
                                                </a>
                                            </div>
                                        </c:forEach>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
                <!-- Add Course | Ends here -->

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
    jQuery('.group-list img').hover(function(){
        jQuery(this).attr("src", function(index, attr){
            return attr.replace(".png", "-hover.png");
        });
    }, function(){
        jQuery(this).attr("src", function(index, attr){
            return attr.replace("-hover.png", ".png");
        });
    });

    jQuery('.selectpicker').selectpicker();
</script>

</body>
</html>