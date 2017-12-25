<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
  <meta name="google-signin-client_id" content="369177612653-i94p6hv9s6e9icnu36rducrji9l31v6b.apps.googleusercontent.com">
  <script>

  </script>
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
      <!-- Collect the nav links, forms, and other content for toggling -->
      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <nav>
          <ul class="nav navbar-nav navbar-right">
            <li role="presentation"><a href="#">About Us</a></li>
            <li role="presentation"><a href="#">Contact Us</a></li>
            <c:if test="${not empty sessionScope.get('FIRST_NAME')}">
              <li class="dropdown" role="presentation">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                    ${sessionScope.get("FIRST_NAME")}
                    ${sessionScope.get("LAST_NAME")}
                  <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                  <li><a href="/userProfile">View Profile</a></li>
                  <li><a href="/editUserProfile">Edit Profile</a></li>
                  <li role="separator" class="divider"></li>
                  <li>${logout}</li>
                </ul>
              </li>
            </c:if>
            <c:if test="${empty sessionScope.get('FIRST_NAME')}">
              <li role="presentation"><a href="#signin" class="auth">Login</a></li>
              <li role="presentation"><a href="#signup" class="auth">Register</a></li>
            </c:if>
          </ul>
        </nav>
      </div>
    </div>
  </div>

  <div class="container">
    <div class="col-md-3">
      <h1>Welcome to Exam Signal!</h1>
      <p>We believe in transparency, communication to every individual  and we are trying to archive this with smart work and good planning. We are in 21st century and in this century we are moving towards globalization. Examsignal is trying to globalize our education system by providing an online education platform for each and every student of India free of cost.</p>
      <p>Let's start free exam now!</p>
      <input type="button" class="btn btn-blue" value="Start Free Exam" id="start-exam-btn">
    </div>
    <div class="col-md-8">
      <img src="../images/about.png" class="img-responsive">
    </div>
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
</body>
</html>
