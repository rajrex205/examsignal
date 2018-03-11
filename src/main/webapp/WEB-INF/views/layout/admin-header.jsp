<header class="main-header">
  <!-- Logo -->
  <a href="/admindashboard" class="logo">
    <!-- mini logo for sidebar mini 50x50 pixels -->
    <span class="logo-mini"><b>E</b>S</span>
    <!-- logo for regular state and mobile devices -->
    <span class="logo-lg"><b>Exam</b>Signal</span>
  </a>
  <!-- Header Navbar: style can be found in header.less -->
  <nav class="navbar navbar-static-top" role="navigation">
    <!-- Sidebar toggle button-->
    <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
      <span class="sr-only">Toggle navigation</span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </a>
    <div class="navbar-custom-menu">
      <ul class="nav navbar-nav">
        <!-- Messages: style can be found in dropdown.less-->
        <!-- User Account: style can be found in dropdown.less -->
        <li class="dropdown user user-menu">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">
            <img src="${pageContext.request.contextPath}/resources/dist/img/user2-160x160.jpg" class="user-image" alt="User Image" />
            <span class="hidden-xs">${sessionScope.get("FIRST_NAME")} ${sessionScope.get("LAST_NAME")}</span>
          </a>
          <ul class="dropdown-menu">
            <!-- User image -->
            <li class="user-header">
              <img src="${pageContext.request.contextPath}/resources/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" />
              <p>
                ${sessionScope.get("FIRST_NAME")}
                ${sessionScope.get("LAST_NAME")}
              </p>
            </li>
            <!-- Menu Footer-->
            <li class="user-footer">
              <div class="pull-left">
                <a href="/admindashboard" class="btn btn-default btn-flat">Profile</a>
              </div>
              <div class="pull-right">
                <!--<a href="/adminsignout" class="btn btn-default btn-flat">Logout</a>-->
                ${logout}
              </div>
            </li>
          </ul>
        </li>
      </ul>
    </div>
  </nav>
</header>