<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Add Question | Exam Signal"/>
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
                Question for Subject: ${selectedSubjectName}
            </h1>
            <ol class="breadcrumb">
                <li><a href="/admindashboard"><i class="fa fa-dashboard"></i> Home</a></li>
                <li><a href="/manageExam?examId=${question.examId}">Exam Manager</a></li>
                <li class="active">Add Question</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content">
            <div class="row">
                <!-- left column -->
                <div class="col-md-12">
                    <!-- general form elements -->
                    <div class="box box-primary">
                        <!-- tabs left -->
                        <div class="nav-tabs-custom">
                            <c:if test="${not empty subjects}">
                                <ul class="nav nav-tabs">
                                    <c:forEach var="listValue" items="${subjects}">
                                        <li><a href="/addQuestion?examid=${examid}&selectedSubjectId=${listValue.id}">${listValue.subjectName}</a></li>
                                    </c:forEach>
                                </ul>
                            </c:if>
                            <div class="tab-content">
                                <div class="tab-pane active">
                                    <c:if test="${question.subjectId != 0}">
                                        <form:form method="POST" action="/addQuestionAction" modelAttribute="question" cssClass="form-horizontal" role="form">
                                            <form:hidden path="examId" />
                                            <form:hidden path="subjectId" />

                                            <div class="form-group">
                                                <form:label path="question" cssClass="control-label col-sm-3">Question</form:label>
                                                <div class="col-sm-6">
                                                    <form:input path="question" cssClass="form-control" />
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <form:label path="marks" cssClass="control-label col-sm-3">Marks</form:label>
                                                <div class="col-sm-6">
                                                    <form:input path="marks" cssClass="form-control" />
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <form:label path="option1" cssClass="control-label col-sm-3">Option 1</form:label>
                                                <div class="col-sm-6">
                                                    <form:input path="option1" cssClass="form-control" />
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <form:label path="option2" cssClass="control-label col-sm-3">Option 2</form:label>
                                                <div class="col-sm-6">
                                                    <form:input path="option2" cssClass="form-control" />
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <form:label path="option3" cssClass="control-label col-sm-3">Option 3</form:label>
                                                <div class="col-sm-6">
                                                    <form:input path="option3" cssClass="form-control" />
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <form:label path="option4" cssClass="control-label col-sm-3">Option 4</form:label>
                                                <div class="col-sm-6">
                                                    <form:input path="option4" cssClass="form-control" />
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <form:label path="answer" cssClass="control-label col-sm-3">Correct Option (enter 1/2/3/4)</form:label>
                                                <div class="col-sm-6">
                                                    <form:select path="answer" cssClass="form-control" >
                                                        <form:option value="1" label="1"/>
                                                        <form:option value="2" label="2"/>
                                                        <form:option value="3" label="3"/>
                                                        <form:option value="4" label="4"/>
                                                    </form:select>
                                                </div>
                                            </div>

                                            <div class="box-footer">
                                                <input type="submit" value="Submit" class="btn btn-primary" />
                                            </div>
                                        </form:form>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <!-- /tabs -->

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