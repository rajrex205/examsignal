<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Start Exam"/>
<div class="header navbar-default clearfix">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <div class="logo">
                <a href="/">
                    <img src="${pageContext.request.contextPath}/resources/images/logo.png" title="Exam Signal" alt="Exam Signal">
                </a>
            </div>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <nav>
                <ul class="nav navbar-nav navbar-right logged-in">
                    <div class="navbar-hidden">
                        <c:if test="${sessionScope.get('TYPE_OF_USER') == 'user'}">
                            <li><a href="/userProfile" <c:if test="${requestScope['javax.servlet.forward.request_uri']=='/userProfile'}">class="active"</c:if>>Profile</a></li>
                            <li><a href="/selectExam" <c:if test="${requestScope['javax.servlet.forward.request_uri']=='/selectExam'}">class="active"</c:if>>Start Exam</a></li>
                            <li><a href="/viewGroups" <c:if test="${requestScope['javax.servlet.forward.request_uri']=='/viewGroups'}">class="active"</c:if>>Your Exams / Courses</a></li>
                            <li><a href="/results" <c:if test="${requestScope['javax.servlet.forward.request_uri']=='/results'}">class="active"</c:if>>Results and Rewards</a></li>
                            <li><a href="/referFriend" <c:if test="${requestScope['javax.servlet.forward.request_uri']=='/referFriend'}">class="active"</c:if>>Refer your Friend</a></li>
                            <li><a href="#" class="pull-right">Earned Points <span>${profile.points}</span></a>
                        </c:if>
                    </div>
                    <li class="dropdown" role="presentation">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                            ${sessionScope.get("FIRST_NAME")}
                            ${sessionScope.get("LAST_NAME")}
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="/userProfile">View Profile</a></li>
                            <li><a href="/editUserProfile">Edit Profile</a></li>
                            <li role="separator" class="divider"></li>
                            <li>${logout}</li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</div>