<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container">
    <nav class="menu-links">
        <a href="/userProfile" <c:if test="${requestScope['javax.servlet.forward.request_uri']=='/userProfile'}">class="active"</c:if>>Profile</a>
        <a href="/selectExam" <c:if test="${requestScope['javax.servlet.forward.request_uri']=='/selectExam'}">class="active"</c:if>>Start Free Exam</a>
        <a href="/viewGroups" <c:if test="${requestScope['javax.servlet.forward.request_uri']=='/viewGroups'}">class="active"</c:if>>Your Exams / Courses</a>
        <a href="/results" <c:if test="${requestScope['javax.servlet.forward.request_uri']=='/results'}">class="active"</c:if>>Results and Rewards</a>
        <a href="/referFriend" <c:if test="${requestScope['javax.servlet.forward.request_uri']=='/referFriend'}">class="active"</c:if>>Refer your Friend</a>
        <a href="#" class="pull-right">Earned Points <span>${profile.points}</span></a>
    </nav>
</div>