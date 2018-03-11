<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Dashboard</title>
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
                xhr.open('GET', 'http://localhost:8080/gsignout');
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
<body>
<p style="margin-right: 50px;width: 200px;">${logout}</p>
Message: ${isLoggedIn} ${name}
<br/>
${email}<br/>
${profilePage}
</body>
</html>
