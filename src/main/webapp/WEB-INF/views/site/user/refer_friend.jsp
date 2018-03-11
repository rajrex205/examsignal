<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="Refer your friend"/>
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

    <div class="container border-bottom clearfix">
        <div class="col-md-12">
            <div class="invite pull-right">
                <form method="POST" action="/referFriendAction">
                    <div class="control-group">
                        <label class="control-label" for="email">I would like to invite my friend via:</label>
                        <div class="controls">
                            <input type="text" name="email" id="email" class="form-control input-sm" placeholder="Enter Friend's mail id..">
                        </div>
                        ${response}
                    </div>
                    <!-- Button -->
                    <div class="control-group pull-right">
                        <label class="control-label" for="refer"></label>
                        <div class="controls">
                            <input type="submit" value="Refer" name="refer" id="refer" class="btn btn-blue" />
                        </div>
                    </div>
                </form>
                <div class="user-picture">
                    <img alt="User Pic" src="${pageContext.request.contextPath}/resources/images/default-image.jpg" class="img-responsive center-block">
                </div>
            </div>
        </div>
    </div>

    <div class="user-terms">
        <div class="container">
            <a href="#" class="pull-right">Terms and Conditions to get more points</a>
        </div>
    </div>

    <!-- Common Footer Links -->
    <jsp:include page="../../layout/footer.jsp" />

</div><!-- container-full -->
<!-- Common JS Imports -->
<jsp:include page="../../layout/common-js-import.jsp" />
</body>
</html>