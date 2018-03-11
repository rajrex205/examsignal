<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="Results and Rewards"/>
<!DOCTYPE html>
<html>
<!-- Common Header -->
<jsp:include page="../../layout/head.jsp" />
<body>
<script src="http://connect.facebook.net/en_US/all.js" type="text/javascript" charset="utf-8"></script>
<script>
    FB.init({
        appId: '1620847074860547',
        status: true,
        cookie: true,
        xfbml: true
    });

    function posttofb(){
        var userName = "${fbshare.userName}";
        var courseName = "${fbshare.courseName}";
        var examName = "${fbshare.examName}";
        var marksSecured = "${fbshare.marksSecured}";
        var totalMarks = "${fbshare.totalMarks}";
        var percentage = "${fbshare.percentage}";
        var percentageDescription = "${fbshare.percentageDescription}";
        var points = "${fbshare.totalPoints}";
        var image = "${popupdetails.scoreId}"+".png";
        var imageLink = "http://www.examsignal.com/fbshareresult/"+image;

        FB.ui({
            method: 'feed',
            link: 'http://www.examsignal.com',
            picture: imageLink,
            caption: 'ExamSignal.com'
            /*description: userName +'\'s score in '+ courseName+' '+ examName+ '\n'+
            marksSecured+ ' marks out of '+totalMarks+ ' [Total Earned Points: '+points +']\n'+
            percentageDescription +'\n ' +
            userName + ' scored '+ percentage + ' %\nTest Yourself with ExamSignal.com'*/
        }, function(response){
            if (response && response.post_id) {
                window.location="/results";
            } else {
                window.location="/results";
            }
        });
    }
</script>
<div class="container-full">
    <!-- Common Header -->
    <jsp:include page="../../layout/header.jsp" />

    <!-- Student Header Links -->
    <jsp:include page="../../layout/student-header.jsp" />

    <div class="container">
        <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-9 col-lg-9 center-block">

                <!-- tabs left -->
                <div class="tabbable tabs-left">
                    <ul class="nav nav-tabs">
                        <c:if test="${not empty examList}">
                            <c:forEach var="crs" items="${examList}">
                                <li class="${crs.activeAttribute}"><a href="#${crs.courseId}" data-toggle="tab">${crs.courseName}</a></li>
                            </c:forEach>
                        </c:if>
                    </ul>
                    <div class="tab-content">
                        <c:if test="${not empty examList}">
                            <c:forEach var="crs" items="${examList}">
                                <div class="tab-pane ${crs.activeAttribute}" id="${crs.courseId}">
                                    <div class=" col-md-9 col-lg-9">
                                        <table class="table table-hover results">
                                            <thead>
                                            <tr>
                                                <th>Date</th>
                                                <th>Exam ID</th>
                                                <th>Total Marks</th>
                                                <th>Marks Scored</th>
                                                <th></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:if test="${not empty crs.examResultViews}">
                                                <c:forEach var="exam" items="${crs.examResultViews}">
                                                    <tr>
                                                        <td>${exam.examDate}</td>
                                                        <td>${exam.examName}</td>
                                                        <td>${exam.maxMarks}</td>
                                                        <td>${exam.totalMarks}</td>
                                                        <td></td>
                                                    </tr>
                                                </c:forEach>
                                            </c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>

                    </div>
                </div>
                <!-- /tabs -->
                <br /><br /><b>Offer</b><br />
                ${offer}
                <br/><br/><br/>
                <c:if test="${popupdetails != null}">
                    <div id="popDiv" style="border: solid">
                        <b>Result PopUp Details</b><br/><br/>
                        Your Score: ${popupdetails.marksSecured} out of ${popupdetails.totalMarks}<br/>
                        ${popupdetails.percentage} - ${popupdetails.percentageDescription}<br/>
                        Time Taken : <br/>
                        ${popupdetails.timeTaken} minutes out of ${popupdetails.totalTime} minutes

                        <br/><br/>
                        <a onclick="posttofb()"><img src="${pageContext.request.contextPath}/resources/images/fbshare.png" style="max-width: 50px"></a>
                        <a href="http://www.examsignal.com/fbshareresult/${popupdetails.scoreId}.png">FB Share Image</a>
                    </div>
                </c:if>
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