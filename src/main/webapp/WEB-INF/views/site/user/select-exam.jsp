<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Select Exam"/>
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

    <div class="container">

        <div class="exam-page">
            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#free_test" aria-controls="home" role="tab" data-toggle="tab">Free Exam test</a></li>
                <li role="presentation"><a href="#practice_test" aria-controls="profile" role="tab" data-toggle="tab">Practice test</a></li>

            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="free_test">
                    <div class="row">
                        <div class="col-md-12">
                            <p class="text-app"><img src="${pageContext.request.contextPath}/resources/images/blue_button.png" title="" class="blue-button" alt="">Please note, you can give</p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <h4>Choose an exam</h4>
                        </div>
                    </div>
                    <div class="row">
                        <c:if test="${not empty allExams.exams}">
                        <c:forEach var="_course" items="${distinctCourses}">
                            <div class="col-md-2 list-box border-right">
                                <strong>${_course}</strong>
                                <div class="row">
                                    <div class="col-md-12">
                                        <c:forEach var="_examTest" items="${allExams.exams.get(_course)}">
                                            <c:if test="${_examTest.typeOfExam=='EXAM'}">
                                                <p class="text-uppercase exam" data-exam-id="${_examTest.examId}">
                                                    ${_examTest.examName}
                                                    <c:if test="${_examTest.isExamAttempted=='Y'}">
                                                        <img src="${pageContext.request.contextPath}/resources/images/blue_button.png" title="" class="blue-button" alt="">
                                                    </c:if>
                                                </p>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                        </c:if>
                    </div>

                </div>
                <div role="tabpanel" class="tab-pane" id="practice_test">
                    <div class="row">
                        <div class="col-md-12">
                            <p class="text-app"><img src="${pageContext.request.contextPath}/resources/images/blue_button.png" title="" class="blue-button" alt="">Please note, you can give</p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <h4>Choose an exam</h4>
                        </div>
                    </div>
                    <div class="row">
                        <c:if test="${not empty allExams.exams}">
                            <c:forEach var="_course" items="${distinctCourses}">
                                <div class="col-md-2 list-box border-right">
                                    <strong>${_course}</strong>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <c:forEach var="_practiceExam" items="${allExams.exams.get(_course)}">
                                                <c:if test="${_practiceExam.typeOfExam=='PRACTICE'}">
                                                    <p class="text-uppercase exam" data-exam-id="${_practiceExam.examId}">
                                                        ${_practiceExam.examName}
                                                        <c:if test="${_practiceExam.isExamAttempted=='Y'}">
                                                            <img src="${pageContext.request.contextPath}/resources/images/blue_button.png" title="" class="blue-button" alt="">
                                                        </c:if>
                                                    </p>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>

            </div>

        </div>
    </div>

    <!-- Exam Modal | Starts Here -->
    <div class="modal fade bs-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content white-bg">
                <div class="modal-header">
                    <h1>Exam Details</h1>
                </div>
                <div class="modal-body">
                    <table class="table table-responsive table-borderless">
                        <tbody>
                        <tr style="font-size: 16px">
                            <td>Exam Category</td>
                            <td id="exam-category"></td>
                        </tr>
                        <tr>
                            <td>Exam ID</td>
                            <td id="examid"></td>
                        </tr>
                        <tr>
                            <td>Subjects</td>
                            <td id="subjects"></td>
                        </tr>
                        <tr>
                            <td>Duration</td>
                            <td id="duration"></td>
                        </tr>
                        <tr>
                            <td>Max Score</td>
                            <td id="max-score"></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <form:form action="/exam" modelAttribute="form" method="get">
                        <input type="hidden" id="exam-id" name="examid">
                        <div class="col-md-12">
                            <div class="control-group">
                                <div class="controls">
                                    <input id="start-exam" class="btn btn-green" type="submit" value="Start Free Exam"/>
                                    <input id="cancel-exam" class="btn btn-blue" type="submit" value="Cancel"/>
                                </div>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div
        </div>
    </div>

    <!-- Common Footer Links -->
    <jsp:include page="../../layout/footer.jsp" />
</div><!-- container-full -->
<!-- Common JS Imports -->
<jsp:include page="../../layout/common-js-import.jsp" />
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-select.js"></script>

<script type="text/javascript">
    $('.exam').on('click', function(e) {
        var t = $(this);
        // Remove previous selected exam
        /*
        jQuery('.exam-list .practice-test > span, .exam-list .exam-test > span').each(function() {
            jQuery(this).removeClass('selected');
        });
        t.addClass('selected');
        */

        var exam_id = t.data('exam-id');
        $('#exam-id').val(exam_id);
        getExamDetail(exam_id);
        e.preventDefault();
    });

    $('#cancel-exam').on('click', function(e) {
        $('.modal').modal('hide');
        e.preventDefault();
    });



    function getExamDetail(examid) {
        var json = { "examId" : examid};

        $.ajax({
            url: '/examDetail.json',
            data: JSON.stringify(json),
            type: "POST",
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(data) {
                $('#exam-category').html(data.courseName);
                $('#examid').html(data.examName);
                $('#subjects').html(data.allSubjects);
                $('#duration').html(data.duration);
                $('#max-score').html(data.totalMarks);
                $('.modal').modal('show');
            }
        });

        e.preventDefault();
    };
</script>
</body>
</html>