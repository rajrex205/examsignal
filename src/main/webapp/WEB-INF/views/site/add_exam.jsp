<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Add Exam | Exam Signal"/>
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
                Add Subject
            </h1>
            <ol class="breadcrumb">
                <li><a href="/admindashboard"><i class="fa fa-dashboard"></i> Home</a></li>
                <li class="active"><a href="/addExam"> Add Exam</a></li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <!-- left column -->
                <div class="col-md-12">
                    <!-- general form elements -->
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">Add Exam</h3>
                        </div><!-- /.box-header -->
                        <!-- form start -->
                        <form:form method="POST" action="/addExamAction" modelAttribute="exam" cssClass="form-horizontal" role="form">
                            <div class="box-body">
                                <c:if test="${ not empty message}">
                                    <div class="alert alert-success alert-dismissable">
                                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                                        <h4>	<i class="icon fa fa-check"></i> Alert!</h4>
                                            ${message}
                                    </div>
                                </c:if>
                                <div class="col-md-12">
                                    <form:label path="courseName" cssClass="control-label">Course</form:label>
                                    <form:select path="courseName" cssClass="form-control">
                                        <form:options items="${courses}" />
                                    </form:select>
                                </div>

                                <div class="col-md-12">
                                    <form:label path="examName" cssClass="control-label">Exam Name (like IIT_01)</form:label>
                                    <form:input path="examName" cssClass="form-control" placeholder="Enter Exam Name" />
                                </div>

                                <div class="col-md-12">
                                    <form:label path="examType" cssClass="control-label">Type of Exam</form:label>
                                    <form:radiobutton path="examType" value="PRACTICE" />Practice Test
                                    <form:radiobutton path="examType" value="EXAM" />Exam
                                </div>

                                <div class="col-md-12">
                                    <form:label path="duration" cssClass="control-label">Exam Duration (in minutes)</form:label>
                                    <form:input path="duration" cssClass="form-control" />
                                </div>
                            </div><!-- /.box-body -->

                            <div class="box-footer">
                                <input type="submit" value="Submit" class="btn btn-primary" />
                            </div>
                        </form:form>
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