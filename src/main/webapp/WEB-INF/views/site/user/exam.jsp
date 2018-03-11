<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Start Exam"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Start Free Exam</title>
    <!-- font Awesome -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">

    <!-- Latest compiled and minified Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        #body {
            display: none;
        }
    </style>
</head>
<!-- Common Header -->
<jsp:include page="../../layout/head.jsp" />
<body>
<div class="container-full">
    <!-- Common Header -->
    <jsp:include page="../../layout/header.jsp" />

    <!-- Student Header Links -->
    <jsp:include page="../../layout/student-header.jsp" />

    <div class="container">
        <input id="fullscreen-btn" name="submit" type="submit" value="Start Free Exam" class="btn btn-green center-block" />
    </div>

    <div class="container" id="body">
        <div class="row">
            <div class="col-md-12 col-md-offset-1-custom clearfix start-exam">

                <!-- tabs left -->
                <div class="tabbable tabs-left">
                    <ul class="nav nav-tabs">
                        <c:if test="${ not empty subjects}">
                            <c:forEach var="_subject" items="${subjects}">
                                <li class="${_subject.byDefaultActiveCode}"><a href="#${_subject.subjectId}" data-toggle="tab">${_subject.subjectName}</a></li>
                            </c:forEach>
                        </c:if>
                    </ul>
                    <div class="tab-content">
                        <c:if test="${ not empty subjects}">
                            <c:forEach var="_subject" items="${subjects}">
                                <div class="tab-pane ${_subject.byDefaultActiveCode}" id="${_subject.subjectId}">
                                    <div class=" col-md-6 col-lg-6 question">
                                        <h1>${_subject.firstQuestion.questionNumber}</h1>
                                        <p>${_subject.firstQuestion.questionText}</p>
                                        <form class="examForm" action="#" method="post">
                                            <input type="hidden" class="question-id" value="${_subject.firstQuestion.questionNumber}">
                                            <div class="options clearfix">
                                                <div class="col-md-6">
                                                    <input type="radio" name="selectedOption" value="1" id="selectedOption1" />
                                                    <label for="selectedOption1">${_subject.firstQuestion.option1}
                                                </div>
                                                <div class="col-md-6">
                                                    <input type="radio"  name ="selectedOption" value="2" id="selectedOption2"/>
                                                    <label for="selectedOption2">${_subject.firstQuestion.option2}</label>
                                                </div>
                                                <div class="col-md-6">
                                                    <input type="radio" name ="selectedOption" value="3" id="selectedOption3" />
                                                    <label for="selectedOption3">${_subject.firstQuestion.option3}</label>
                                                </div>
                                                <div class="col-md-6">
                                                    <input type="radio" name ="selectedOption" value="4" id="selectedOption4" />
                                                    <label for="selectedOption4">${_subject.firstQuestion.option4}</label>
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <div class="submit-response">&nbsp;</div>
                                            </div>
                                        </form>
                                        <div class="clearfix">&nbsp;</div>
                                        <div class="pager">
                                            <a class="pull-left exam-action" data-action="prev" href="#">< Previous</a>
                                            <a class="pull-left exam-action" data-action="next" href="#">Next ></a>
                                            <a class="pull-right later" href="#">Review Later</a>
                                        </div>
                                        <div>
                                            <form method="POST" action="/submitExam">
                                                <input type="hidden" name ="examiid" id="examiid" value="${sessionScope.get('EXAM_ID')}">
                                                <input id="submit-exam" name="submit" type="submit" value="Submit Exam" class="btn btn-green pull-right" />
                                            </form>
                                        </div>
                                    </div>

                                    <div class="col-md-3 col-lg-3 question-number">
                                        <c:set var="qNumber" value="1" scope="page"/>
                                        <c:forEach var="_questionStatus" items="${sessionScope.get('EXAM_SESSION').get(_subject.subjectId).questionAttemptStatus}">
                                            <span class="${_questionStatus}"><a href="#" data-question-id="${qNumber}" data-subject-id="${_subject.subjectId}">${qNumber}</a></span>
                                            <c:set var="qNumber" value="${qNumber +1}" scope="page"/>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>
                <!-- /tabs -->
            </div>
        </div>
    </div>

    <!-- Common Footer Links -->
    <jsp:include page="../../layout/footer.jsp" />

</div><!-- container-full -->

<!-- Common JS Imports -->
<jsp:include page="../../layout/common-js-import.jsp" />
<script type="text/javascript">
       $(document).ready(function() {

        /*
         * Submit Question Answer
         */
        $('.examForm').on('submit', function(event) {


            submitQuestion(json);
            event.preventDefault();
        });

        /*
         * On Prev and Next Button Click
         * @return Question and options
         */
        $('.exam-action').on('click', function(e) {
            var t = $(this);
            var subjectid = t.closest('.tab-pane').attr('id');
            var action = t.data('action');
            var url = '';
            if(action == 'prev') {
                url = '/getPreviousQuestion.json';
            } else {
                url = '/getNextQuestion.json';
            }
            var selectedOption = t.closest('.tab-pane').find('[name="selectedOption"]:checked').val();
            if(selectedOption) {
                submitQuestion(t);
            }
            getQuestion(subjectid, url);

            e.preventDefault();
        });

        $('.later').on('click', function(e) {
            var t = $(this);
            var subjectId = $(this).closest('.tab-pane').attr('id');
            var questionNumber = t.closest('.tab-pane').find('.question-id').val();

            var json = { "questionId" : questionNumber, "subjectId": subjectId};

            $.ajax({
                url: '/reviewQuestionLater.json',
                data: JSON.stringify(json),
                type: "POST",
                beforeSend: function(xhr) {
                    xhr.setRequestHeader("Accept", "application/json");
                    xhr.setRequestHeader("Content-Type", "application/json");
                },
                success: function(data) {
                    console.log(data);
                    var respContent = "";
                    respContent += "<span class='success'>Marked for Review: [";
                    respContent += data.message + "]</span>";

                    $('#'+data.subjectId+' .submit-response').html(respContent);
                }
            });

            e.preventDefault();
        });

        /*
         *
         */
        $('.question-number span a').on('click', function(e) {
            var t = $(this);
            var questionNumber = t.data('question-id');
            var subjectId = t.data('subject-id');

            var json = { "questionId" : questionNumber, "subjectId": subjectId};

            $.ajax({
                url: '/getQuestion.json',
                data: JSON.stringify(json),
                type: "POST",
                beforeSend: function(xhr) {
                    xhr.setRequestHeader("Accept", "application/json");
                    xhr.setRequestHeader("Content-Type", "application/json");
                },
                success: function(data) {
                    console.log(data);
                    $('#'+data.subjectId+" .submit-response").html('');
                    $('#'+data.subjectId+' .question h1').html(data.questionNumber);
                    $('#'+data.subjectId+' .question p').html(data.questionText);
                    $('#'+data.subjectId+' .question .question-id').val(data.questionNumber);

                    // Options
                    $('#'+data.subjectId+' .question label[for=selectedOption1]').html(data.option1);
                    $('#'+data.subjectId+' .question label[for=selectedOption2]').html(data.option2);
                    $('#'+data.subjectId+' .question label[for=selectedOption3]').html(data.option3);
                    $('#'+data.subjectId+' .question label[for=selectedOption4]').html(data.option4);
                }
            });

            e.preventDefault();
        });


    }); /* End of Document ready */

    /*
     * Method to get question
     * Next or prev (based on URL)
     */
    function getQuestion(subjectid, url) {
        $.ajax({
            url: url,
            data: subjectid,
            type: "POST",
            async: false,
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(data) {
                $('#'+data.subjectId+" .submit-response").html('');
                $('#'+data.subjectId+' .question h1').html(data.questionNumber);
                $('#'+data.subjectId+' .question p').html(data.questionText);
                $('#'+data.subjectId+' .question .question-id').val(data.questionNumber);

                // Options
                $('#'+data.subjectId+' .question label[for=selectedOption1]').html(data.option1);
                $('#'+data.subjectId+' .question label[for=selectedOption2]').html(data.option2);
                $('#'+data.subjectId+' .question label[for=selectedOption3]').html(data.option3);
                $('#'+data.subjectId+' .question label[for=selectedOption4]').html(data.option4);

                $('[name="selectedOption"]:checked').prop('checked', false);
            }
        });
    }

    /*
     * Method to submit question
     */
    function submitQuestion(t) {
        var questionNumber = t.closest('.tab-pane').find('.question-id').val();
        var subjectId = t.closest('.tab-pane').attr('id');
        var selectedOption = t.closest('.tab-pane').find('[name=selectedOption]:checked').val();
        var response = "submitted";
        var json = { "questionId" : questionNumber, "subjectId": subjectId, "selectedOption" : selectedOption, "response" : response};

        $.ajax({
            url: '/submitThisQuestion.json',
            data: JSON.stringify(json),
            type: "POST",
            async: false,
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(questionform) {
                var respContent = "";
                respContent += "<span class='success'>Answer Submitted : [";
                respContent += questionform.response + "]</span>";

                $('#'+questionform.subjectId+' .submit-response').html(respContent);
            }
        });
    }

    $('#fullscreen-btn').on('click', function(e) {
        $(this).parent().hide();
        $('#body').click();
        $('#body').show();
        e.preventDefault();
    });

    var elem = document.getElementById("body");

    elem.onclick = function() {
        req = elem.requestFullScreen || elem.webkitRequestFullScreen || elem.mozRequestFullScreen;
        req.call(elem);
    }
    function toggleFullScreen() {
        if (!document.fullscreenElement &&    // alternative standard method
                !document.mozFullScreenElement && !document.webkitFullscreenElement && !document.msFullscreenElement ) {  // current working methods
            if (document.documentElement.requestFullscreen) {
                document.documentElement.requestFullscreen();
            } else if (document.documentElement.msRequestFullscreen) {
                document.documentElement.msRequestFullscreen();
            } else if (document.documentElement.mozRequestFullScreen) {
                document.documentElement.mozRequestFullScreen();
            } else if (document.documentElement.webkitRequestFullscreen) {
                document.documentElement.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT);
            }
        } else {
            //$('#submit-exam').click();
            //console.log("I am in exit.");
            if (document.exitFullscreen) {
                document.exitFullscreen();
            } else if (document.msExitFullscreen) {
                document.msExitFullscreen();
            } else if (document.mozCancelFullScreen) {
                document.mozCancelFullScreen();
            } else if (document.webkitExitFullscreen) {
                document.webkitExitFullscreen();
            }
        }
    }
</script>
</body>
</html>