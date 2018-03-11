<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<body>
<div class="container-full">

    <div class="container">
        <div class="row">
                Course: ${details.courseName} <br/>
                ExamName: ${details.examName} <br/>
                Subjects: ${details.allSubjects} <br/>
                Total: ${details.totalMarks} <br/>
                Duration: ${details.duration} <br/>
                ExamID(Internal): ${details.examId} <br/>
        </div>
    </div>

</div><!-- container-full -->
</body>
</html>