<%@page import="com.springapp.util.ExamSignalApplicationConstant"%>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${pageTitle}</title>
    <!-- font Awesome -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">

    <!-- Latest compiled and minified Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css?v=<%=ExamSignalApplicationConstant.WEB_APPLICATION_VERSION %>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-select.css?v=<%=ExamSignalApplicationConstant.WEB_APPLICATION_VERSION %>">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!-- Google SignOut -->
    <meta name="google-signin-client_id" content="369177612653-i94p6hv9s6e9icnu36rducrji9l31v6b.apps.googleusercontent.com">
    <script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
    <script>
        function onLoad() {
            gapi.load('auth2', function() {
                gapi.auth2.init();
            });
        }
        function signOut() {
            var auth2 = gapi.auth2.getAuthInstance();
            auth2.signOut().then(function () {
                var xhr = new XMLHttpRequest();
                xhr.open('GET', '/gsignout');
                //xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xhr.onload = function() {
                    var serverResponse = xhr.responseText;
                    if(serverResponse == 'true' ) {
                        window.location="/";
                    } else {
                        console.log('Something went wrong while Google Authentication !!')
                    }
                };
                xhr.send();
            });
        }
    </script>
</head>