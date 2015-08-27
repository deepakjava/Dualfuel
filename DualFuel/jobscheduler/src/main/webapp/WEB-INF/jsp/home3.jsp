<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>DualFuel Corp</title>

         <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.js"></script>
         <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
         <script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
         <script type="text/javascript" src="lib/humanize-duration.js"></script>
         <script src="lib/bower_components/momentjs/min/moment.min.js"></script>
         <script src="lib/bower_components/momentjs/min/locales.min.js"></script>
         <script src="lib/bower_components/humanize-duration/humanize-duration.js"></script>
         <script type="text/javascript" src="lib/sockjs.min.js"></script>
         <script type="text/javascript" src="lib/stomp.min.js"></script>
         <script src="lib/bower_components/angular/angular.min.js"></script>
         <script src="lib/dist/angular-timer.js"></script>
         <script type="text/javascript" src="lib/bootstrap/js/bootstrap-table.js"></script>
         <script type="text/javascript" src="lib/ngDialog.js"></script>

         <script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>
         <script src="lib/c3j/js/c3.min.js"></script>

         <script type="text/javascript" src="app/controllers.js"></script>

         <link rel="stylesheet" href="http://getbootstrap.com/dist/css/bootstrap.min.css">
         <link rel="stylesheet" href="http://getbootstrap.com/dist/css/bootstrap.css">
         <link rel="stylesheet" href="lib/bootstrap/css/bootstrap-responsive.css">
         <link rel="stylesheet" href="lib/bootstrap/css/bootstrap-table.css">
         <link rel="stylesheet" href="lib/bootstrap/css/bootstrap-theme.min.css">
         <link rel="stylesheet" href="lib/angular/ngDialog.css">
         <link rel="stylesheet" href="lib/angular/ngDialog-theme-default.css">
         <link rel="stylesheet" href="lib/angular/ngDialog-theme-plain.css">
         <link href="assets/css/c3.css" rel="stylesheet" type="text/css">



     <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->

    <style type="text/css">

        body{
        font-family: Verdana, Geneva, sans-serif;
        background: #262626;
        font-size: 11px;

        }

        .table{
        color: #FF9E3D;
        border-collapse: collapse;
        font-size: 11px;
        font-family: Verdana, Geneva, sans-serif;


        }
        tbody{
        background: #262626;
        }

        thead {
        background: #616161;
        color: #F2F2F2;
        }
        .red{
        color: #FF0033;
        }

        .flash_default{
            border: #262626 5px solid;
        }
        .flash_red{
            border: #FF3300 5px solid;
        }
        .flash_white{
            border: #FFFF33 5px solid;
        }
        .visible-desktop {
            display: table-cell!important;
        }

        .hower:hover {
               background-color: #545454;
        }
        .hower {
               background-color: #333333;
               border: #555555 1px solid;
               padding: 5px 5px;
               color: #FFFFFF;
               font-family: "Arial Black";
        }
    </style>
</head>

<%
        java.util.List<String> tables = new java.util.ArrayList();
%>

<body ng-app="myApp" ng-controller="ideaCtrl">

<nav class="navbar navbar-default navbar-static-top" style="padding:5px 5px;">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#dashboard">Dualfuel App Manager</a>
        </div>

         <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li ng-class="active"><a href="/jobscheduler/index.jsp">Home <span class="sr-only">(current)</span></a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="/jobscheduler/spring/private/logout.do">Logout</a></li>
          </ul>
        </div><!-- /.navbar-collapse -->

    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
<br /><br />
<div class="container marketing">
       <div class="row" >
              <div class="col-lg-4">
                 <div class="hower" onclick="location.href='/jobscheduler/spring/private/coned/home.htm'">
                        <br />
                        <center>
                            <img class="img-circle" src="https://lh6.ggpht.com/t_KAOM7bgxkfXPQNlV6QUp1_RmO6FzFEBSCRoQhBIZew7OOxQtP-22IAame4mIgCMtw=w300" alt="Generic placeholder image" width="160" height="160" style="border: #777777 2px solid;">
                            <h2>Con-Ed Data Viewer</h2>
                        </center>
                    </div>
              </div><!-- /.col-lg-4 -->
             <div class="col-lg-4">
                 <div class="hower" onclick="location.href='/jobscheduler/spring/private/job/home.htm'">
                        <br />
                        <center>
                            <img class="img-circle" src="http://www.magentocommerce.com/magento-connect/media/catalog/product/cache/9/image/9df78eab33525d08d6e5fb8d27136e95/a/o/aoe_scheduler_512ff5e89bb95c8dfed357311d5be09c_scheduler.png" alt="Generic placeholder image" width="160" height="160" style="border: #777777 2px solid;">
                            <h2>Job Scheduler</h2>
                        </center>
                    </div>
              </div><!-- /.col-lg-4 -->
       </div>
</div>
</body>
</html>