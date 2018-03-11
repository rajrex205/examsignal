<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="${pageContext.request.contextPath}/resources/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" />
            </div>
            <div class="pull-left info">
                <p>Alexander Pierce</p>
            </div>
        </div>
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="header">MAIN NAVIGATION</li>
            <li <c:if test="${requestScope['javax.servlet.forward.request_uri']=='/admindashboard'}">class="active"</c:if>>
                <a href="/admindashboard">
                    <i class="fa fa-dashboard"></i> <span>Dashboard</span>
                </a>
            </li>
            <li class="<c:if test="${requestScope['javax.servlet.forward.request_uri']=='/addCourse'}">active</c:if>">
                <a href="/addCourse">
                    <i class="fa fa-files-o"></i>
                    <span>Add Course</span>
                </a>
            </li>
            <li class="<c:if test="${requestScope['javax.servlet.forward.request_uri']=='/addSubject'}">active</c:if>">
                <a href="/addSubject">
                    <i class="fa fa-files-o"></i>
                    <span>Add/Delete Subject</span>
                </a>
            </li>
            <li class="treeview <c:if test="${requestScope['javax.servlet.forward.request_uri']=='/viewAllExams' || requestScope['javax.servlet.forward.request_uri']=='/addExam'}">active</c:if>">
                <a href="#">
                    <i class="fa fa-folder"></i> <span>Exams</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li>
                        <a href="/viewAllExams"> View and Edit Exams</a>
                    </li>
                    <li>
                        <a href="/addExam"> Add Exam</a>
                    </li>
                </ul>
            </li>
            <li <c:if test="${requestScope['javax.servlet.forward.request_uri']=='/viewPendingRequests'}">class="active"</c:if>>
                <a href="/viewPendingRequests">
                    <i class="fa fa-calendar"></i> <span>View Subscription Requests</span>
                    <small class="label pull-right bg-red">3</small>
                </a>
            </li>

        </ul>
    </section>
    <!-- /.sidebar -->
</aside>