<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="View all Exams | Exam Signal"/>
<!DOCTYPE html>
<html>
<!-- Common Header -->
<jsp:include page="../layout/admin-head.jsp" />
<!-- ADD THE CLASS fixed TO GET A FIXED HEADER AND SIDEBAR LAYOUT -->
<!-- the fixed layout is not compatible with sidebar-mini -->
<body class="skin-blue fixed sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- Left side column. contains the sidebar -->
    <jsp:include page="../layout/admin-header.jsp" />
    <!-- =============================================== -->


    <!-- Left side column. contains the sidebar -->
    <jsp:include page="../layout/admin-sidebar.jsp" />
    <!-- =============================================== -->
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                View all Exams
            </h1>
            <ol class="breadcrumb">
                <li><a href="/admindashboard"><i class="fa fa-dashboard"></i> Home</a></li>
                <li class="active"><a href="/viewAllExams"> View all Exams</a></li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <!-- left column -->
                <div class="col-md-12">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">View All Exams</h3>
                        </div><!-- /.box-header -->
                        <div class="box-body">
                            <c:if test="${not empty exams}">
                                <table class="table table-striped">
                                    <tr>
                                        <td>Course</td>
                                        <td>Exam</td>
                                        <td>Edit</td>
                                    </tr>
                                    <c:forEach var="item" items="${exams}">
                                        <tr>
                                            <td>
                                                    ${item.courseName}
                                            </td>
                                            <td>
                                                    ${item.examName}
                                            </td>
                                            <td>
                                                <a href="/manageExam?examId=${item.examId}">Edit</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:if>
                        </div><!-- /.box-body -->
                    </div><!-- /.box -->
                </div><!--/.col (right) -->
            </div>   <!-- /.row -->
        </section><!-- /.content -->
    </div><!-- /.content-wrapper -->

    <footer class="main-footer">
        <div class="pull-right hidden-xs">
            <b>Version</b> 0.0.1
        </div>
        <strong>Copyright &copy; 2015-2016 <a href="http://www.examsignal.com/">ExamSignal</a></strong> All rights reserved.
    </footer>
</div><!-- ./wrapper -->

<jsp:include page="../layout/admin-footer.jsp" />
</body>
</html>