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

    <div class="banner">
        <img src="${pageContext.request.contextPath}/resources/images/homepage-banner.jpg">
        <div class="banner-btn">
            <input type="button" class="btn btn-blue" value="Start Free Exam" id="start-exam-btn">
        </div>
    </div>

    <div class="container">
        <!-- Testimonials | Start Here -->
        <div class="testimonial">
            <c:if test="${not empty testimonials}">
                    <c:forEach var="t" items="${testimonials}">
                        <div class="row item">
                            <div class="col-xs-12">
                                <div class="col-md-2 col-sm-2 col-xs-12">
                                    <img class="img-responsive" style="max-height: 100px;max-width: 100px" src="${pageContext.request.contextPath}/testimonial/${t.iconFileName}">
                                </div>
                                <div class="col-md-10 col-sm-10 col-xs-12">
                                    <div class="caption">
                                        <i class="fa fa-quote-left"></i>
                                        ${t.testimonial}
                                        <i class="fa fa-quote-right"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
            </c:if>
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

    <!-- Login, Register Modal | Starts Here -->
    <div class="modal fade bs-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="auth-tabs">
                    <ul id="myTab" class="nav nav-tabs">
                        <li class="${signinactive}"><a href="#signin" data-toggle="tab">Login</a></li>
                        <li><a href="#"> / </a></li>
                        <li class="${registeractive}"><a href="#signup" data-toggle="tab">Register</a></li>
                    </ul>
                </div>
                <div class="modal-body">
                    <div class="tab-content">
                        <div class="tab-pane fade active in col-md-8 center-block" id="signin">

                            <form:form method="POST" action="/authenticate" modelAttribute="login" cssClass="form-horizontal">
                                <fieldset>
                                    <!-- Sign In Form -->
                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label" for="emailId">E-mail</label>
                                        <div class="controls">
                                            <!--<input required="" name="userid" type="text" class="form-control" placeholder="username@domain.com" class="input-medium" required="">-->


                                            <!--<input required="" name="userid" type="text" class="form-control" placeholder="username@domain.com" class="input-medium" required="">-->
                                            <form:input path="emailId" cssClass="form-control" />

                                        </div>
                                    </div>

                                    <!-- Password input-->
                                    <div class="control-group">
                                        <label class="control-label" for="password">Password:</label>
                                        <div class="controls">
                                            <!--<input required="" id="passwordinput" name="passwordinput" class="form-control" type="password" placeholder="********" class="input-medium">-->
                                            <form:password path="password" cssClass="form-control" />
                                        </div>
                                    </div>

                                    <!-- Button -->
                                    <div class="control-group">
                                        <div class="controls action-btn">
                                            <input type="submit" value="Login" name="signin" class="btn btn-black" />
                                            <a href="/forgotPassword" class="forgot-password pull-right">Forgot password?</a>
                                        </div>
                                    </div>
                                </fieldset>
                            </form:form>
                            <div class="or">&nbsp;</div>
                            <div class="social">
                                <h5>Log in with</h5>
                                <div class="fb" onclick="location.href='${fbLoginUrl}';" style="cursor:pointer;"></div>
                                <div class="google">
                                    <div id="my-signin2">
                                        <script>
                                            function onSuccess(googleUser) {
                                                var id_token = googleUser.getAuthResponse().id_token;
                                                var xhr = new XMLHttpRequest();
                                                xhr.open('POST', '/gauthenticate');
                                                xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                                                xhr.onload = function() {
                                                    var serverResponse = xhr.responseText;
                                                    if(serverResponse == 'true' ) {
                                                        window.location="/userProfile";
                                                    } else {
                                                        console.log('Something went wrong while Google Authentication !!')
                                                    }
                                                };
                                                xhr.send('idtoken=' + id_token);
                                            }
                                            function onFailure() {
                                                console.log("Google Login Failed !!");
                                            }
                                            function renderButton() {
                                                gapi.signin2.render('my-signin2', {
                                                    'scope': 'https://www.googleapis.com/auth/plus.login',
                                                    'width': 176,
                                                    'height': 34,
                                                    'longtitle': false,
                                                    'theme': 'dark',
                                                    'onsuccess': onSuccess,
                                                    'onfailure': onFailure
                                                });
                                            }
                                        </script>
                                        <script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade col-md-8 center-block" id="signup">
                            <form:form method="POST" action="/register" cssClass="form-horizontal" modelAttribute="register">
                                <fieldset>
                                    <!-- Sign Up Form -->
                                    <!-- Text input-->
                                    <div class="control-group">
                                        <form:label cssClass="control-label" path="email">Email</form:label>
                                        <div class="controls">
                                            <form:input path="email" cssClass="form-control"/>
                                        </div>
                                    </div>

                                    <div class="control-group">
                                        <form:label cssClass="control-label" path="password">Password</form:label>
                                        <div class="controls">
                                            <form:password path="password" cssClass="form-control"/>
                                        </div>
                                    </div>

                                    <div class="control-group">
                                        <form:label cssClass="control-label" path="confirmPassword">Confirm Password</form:label>
                                        <div class="controls">
                                            <form:password path="confirmPassword" cssClass="form-control"/>
                                        </div>
                                    </div>

                                    <div class="control-group">
                                        <form:label cssClass="control-label" path="firstName">First Name</form:label>
                                        <div class="controls">
                                            <form:input path="firstName" cssClass="form-control"/>
                                        </div>
                                    </div>

                                    <div class="control-group">
                                        <form:label cssClass="control-label" path="lastName">Last Name</form:label>
                                        <div class="controls">
                                            <form:input path="lastName" cssClass="form-control"/>
                                        </div>
                                    </div>

                                    <div class="control-group">
                                        <form:label cssClass="control-label" path="gender">Gender</form:label>
                                        <div class="controls">
                                            <form:radiobutton path="gender" value="Male" />Male
                                            <form:radiobutton path="gender" value="Female" />Female
                                        </div>
                                    </div>

                                    <div class="control-group">
                                        <form:label cssClass="control-label" path="phone">Phone</form:label>
                                        <div class="controls">
                                            <form:input path="phone" cssClass="form-control"/>
                                        </div>
                                    </div>

                                    <div class="control-group">
                                    <form:label cssClass="control-label" path="address">Address (Optional)</form:label>
                                        <div class="controls">
                                            <form:input path="address" cssClass="form-control"/>
                                        </div>
                                    </div>

                                    <div class="control-group">
                                        <form:label cssClass="control-label" path="highestEducation">Highest Education</form:label>
                                        <div class="controls">
                                            <form:select path="highestEducation" class="form-control">
                                                <form:options items="${higherEducations}" />
                                            </form:select>
                                        </div>
                                    </div>

                                    <div class="control-group">
                                        <form:label cssClass="control-label" path="preferredCourse">Preferred Course</form:label>
                                        <div class="controls">
                                            <form:select path="preferredCourse" cssClass="form-control" multiple="true">
                                                <form:options items="${preferredCourses}" itemValue="courseId" itemLabel="courseName" />
                                            </form:select>
                                        </div>
                                    </div>

                                    <div class="control-group">
                                        <form:label cssClass="control-label" path="referralCode">Referral Code (Optional)</form:label>
                                        <div class="controls">
                                            <form:input path="referralCode" cssClass="form-control"/>
                                        </div>
                                    </div>

                                    <!-- Button -->
                                    <div class="control-group">
                                        <label class="control-label" for="confirmsignup"></label>
                                        <div class="controls">
                                            <input id="confirmsignup" name="confirmsignup" class="btn btn-success" type="submit" value="Sign Up"/>
                                        </div>
                                    </div>
                                </fieldset>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<!-- Latest compiled and minified Bootstrap JavaScript -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<!-- Latest compiled and minified Validation JavaScript -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript">
    var is_login = 0;
    <c:if test="${not empty sessionScope.get('FIRST_NAME')}">
        is_login = 1;
    </c:if>

    jQuery('#start-exam-btn').on('click', function(e) {
        if(is_login == 0) {
            jQuery('.modal').modal('show');
        } else {
            var url = window.location.href;
            console.log(url);
            window.location.href = url+"/selectExam";
        }
        e.preventDefault();
    });

    jQuery('.auth').on('click', function(e) {
        jQuery('.nav-tabs a[href="' + jQuery(this).attr('href') + '"]').tab('show');
        jQuery('.modal').modal('show');
        e.preventDefault();
    });

    /*
     * Form Validation
     */
    jQuery(function() {
        // Setup form validation on the #login element
        $("#login").validate({

            // Specify the validation rules
            rules: {
                emailId: {
                    required: true,
                    email: true
                },
                password: {
                    required: true,
                    minlength: 5
                },
            },

            // Specify the validation error messages
            messages: {
                password: {
                    required: "Please provide a password",
                    minlength: "Your password must be at least 5 characters long"
                },
                emailId: "Please enter a valid email address",
            },

            submitHandler: function(form) {
                form.submit();
            }
        });

        // Setup form validation on the #login element
        $("#register").validate({

            // Specify the validation rules
            rules: {
                email: {
                    required: true,
                    email: true
                },
                password: {
                    required: true,
                    minlength: 5
                },
                confirmPassword: {
                    required: true,
                    minlength: 5,
                    equalTo: "#password"
                },
                firstName: "required",
                lastName: "required",
                gender: "required",
                phone: "required",
                highestEducation: "required",
                preferredCourse: "required",

            },

            // Specify the validation error messages
            messages: {
                password: {
                    required: "Please provide a password",
                    minlength: "Your password must be at least 5 characters long"
                },
                confirmPassword: {
                    required: "Please provide a password",
                    minlength: "Your password must be at least 5 characters long"
                },
                email: "Please enter a valid email address",
            },

            submitHandler: function(form) {
                form.submit();
            }
        });
    });
</script>
</body>
</html>
