<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Exam Signal</title>
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
</head>
<body>

<div class="container-full">
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
        </div>
    </div>

    <div>
        Reset Password : <br />
        <form action="/forgotPasswordAction" method="post">
            Email : <input type="text" name="email" id="email"/>
            <input type="submit" value="Reset" />
        </form>

        <br />
        ${response}
        <br />
    </div>

    <footer class="clearfix">
        <div class="container footer">
            <a href="#">about us</a>
            <a href="#">contact us</a>
            <a href="#">terms of use</a>
            <a href="#">privacy policy</a>
        </div>
    </footer>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<!-- Latest compiled and minified Bootstrap JavaScript -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script type="text/javascript">
    jQuery('.auth').on('click', function(e) {
        jQuery('.nav-tabs a[href="' + jQuery(this).attr('href') + '"]').tab('show');
        jQuery('.modal').modal('show');
        e.preventDefault();
    });
</script>
</body>
</html>