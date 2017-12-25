<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Manage Exam | Exam Signal"/>
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
                <li><a href="/viewAllExams">View All Exams</a></li>
                <li class="active">Edit Exam ${examDescription}</li>
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
                            <h3 class="box-title">Manage Exam - ${examDescription}</h3>
                        </div><!-- /.box-header -->
                        <div class="box-body">
                            Add Question(s)<br/><a href="/addQuestion?examid=${examid}">Add</a><br/>
                            <br/>
                            -----------------<br/>
                            Update Duration:
                            <form:form method="POST" action="/updateDuration" modelAttribute="updateDuration" cssClass="form-horizontal" role="form">
                                <form:hidden path="examId"/>
                                <div class="form-group">
                                    <form:label path="duration" cssClass="control-label col-sm-3">Duration</form:label>
                                    <div class="col-sm-6">
                                        <form:input path="duration" cssClass="form-control" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-3 col-sm-10">
                                        <input type="submit" value="Submit" class="btn btn-blue" />
                                    </div>
                                </div>
                            </form:form><br/>
                            ------------------<br/>
                            Edit Added Questions:<br />
                            <table class="table table-hover">
                                <tr>
                                    <td>Question Number</td>
                                    <td>Question</td>
                                    <td>Option 1</td>
                                    <td>Option 2</td>
                                    <td>Option 3</td>
                                    <td>Option 4</td>
                                    <td>Answer</td>
                                    <td>Marks</td>
                                    <td>Edit</td>
                                    <td>Delete</td>
                                </tr>
                                <c:if test="${not empty questions}">
                                    <c:set var="currentSubject" value="${questions.get(0).subjectId}"/>
                                    <c:forEach var="q" items="${questions}">
                                        <c:if test="${q.subjectId != currentSubject}">
                                            <tr>
                                                <td>-----</td>
                                                <td>-----</td>
                                                <td>-----</td>
                                                <td>-----</td>
                                                <td>-----</td>
                                                <td>-----</td>
                                                <td>-----</td>
                                                <td>-----</td>
                                                <td>-----</td>
                                                <td>-----</td>
                                            </tr>
                                            <c:set var="currentSubject" value="${q.subjectId}"/>
                                        </c:if>
                                        <tr>
                                            <td>${q.questionNumber}</td>
                                            <td>${q.questionText}</td>
                                            <td>${q.option1}</td>
                                            <td>${q.option2}</td>
                                            <td>${q.option3}</td>
                                            <td>${q.option4}</td>
                                            <td>${q.answer}</td>
                                            <td>${q.marks}</td>
                                            <td><a href="/editQuestion?questionid=${q.id}&examid=${q.examId}">Edit</a></td>
                                            <td><a href="/deleteQuestion?questionid=${q.id}&examid=${q.examId}">Delete</a></td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                            </table>
                        </div>
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