<!DOCTYPE html>
<html data-ng-app="myApp">

<head lang="en">
  <meta charset="utf-8">
  <title>Dual Fuel Con-Ed Project</title>
  <link rel="stylesheet" type="text/css" href="http://angular-ui.github.com/ng-grid/css/ng-grid.css" />
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.2/angular.min.js"></script>
  <script type="text/javascript" src="http://angular-ui.github.com/ng-grid/lib/ng-grid.debug.js"></script>
  <link rel="stylesheet" type="text/css" href="/jobscheduler/static/AngularJSService/css/style.css" />

  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

  <!-- Optional theme -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

  <!-- Latest compiled and minified JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

  <script type="text/javascript">

    var app = angular.module('myApp', ['ngGrid']);

    (function(a){a.createModal=function(b){defaults={title:"",message:"Your Message Goes Here!",closeButton:true,scrollable:false};var b=a.extend({},defaults,b);var c=(b.scrollable===true)?'style="max-height: 420px;width:80%;overflow-y: auto;"':"";html='<div class="modal fade" id="myModal">';html+='<div class="modal-dialog">';html+='<div class="modal-content">';html+='<div class="modal-header">';html+='<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>';if(b.title.length>0){html+='<h4 class="modal-title">'+b.title+"</h4>"}html+="</div>";html+='<div class="modal-body" '+c+">";html+=b.message;html+="</div>";html+='<div class="modal-footer">';if(b.closeButton===true){html+='<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>'}html+="</div>";html+="</div>";html+="</div>";html+="</div>";a("body").prepend(html);a("#myModal").modal().on("hidden.bs.modal",function(){a(this).remove()})}})(jQuery);

            /*
            * Here is how you use it
            */

            /*
            $(function(){
                $('.view-pdf').on('click',function(){
                    var pdf_link = $(this).attr('href');
                    var iframe = '<div class="iframe-container"><iframe src="'+pdf_link+'"></iframe></div>'
                    $.createModal({
                    title:'My Title',
                    message: iframe,
                    closeButton:true,
                    scrollable:false
                    });
                    return false;
                });
            })*/


    function getPDF(type, id){
        var pdf_link = "/jobscheduler/spring/getpdf/" + type +"/"+id;

        return showInFrame(pdf_link);
    }

      window.onbeforeunload = function(){
            return false; // This will stop the redirecting.
        }

    function showInFrame(urlString){

           var iframe = '<div class="iframe-container"><iframe src="'+urlString+'"></iframe></div>'
           $.createModal({
           title:'Dual Fuel Window',
           message: iframe,
           closeButton:true,
           scrollable:false
           });
           return false;
        };

          function showMessageInFrame(message){
                   $.createModal({
                   title:'Dual Fuel Window',
                   message: message,
                   closeButton:true,
                   scrollable:false
                   });
                   return false;
                };

    app.controller('MyController', function($scope, $http) {

         function sortByKey(array, key) {
            return array.sort(function(a, b) {
              var x = a[key];
              var y = b[key];
              return ((x > y) ? -1 : ((x < y) ? 1 : 0));
            });
          }
		$scope.filterOptions = {filterText: ''};
		$scope.selectedDays = "7d";
		$scope.pagingOptions = {
            pageSizes: [500, 1000, 5000],
            pageSize: 500,
            totalServerItems: 0,
            currentPage: 1
          };

        $scope.sortOptions = {
          //set initial sort
          sortfield: "DateSubmitted",
          sortdir: "desc"
        };

        $scope.$on('ngGridEventSorted', function(event, data) {
            $scope.sortOptions.sortfield = data.fields[0];
            $scope.sortOptions.sortdir = data.directions[0];
        });

          $scope.totalServerItems = 0;

          $scope.setPagingData = function(largeLoad, page, pageSize) {
              var data;
             if(largeLoad){
                 if ($scope.sortOptions.sortfield) {
                       largeLoad = sortByKey(largeLoad, $scope.sortOptions.sortfield)
                       if ($scope.sortOptions.sortdir == 'asc') {
                         largeLoad.reverse();
                       }

                 }


                  $scope.allData = largeLoad;
                  if ($scope.filterOptions.filterText) {
                       var ft = $scope.filterOptions.filterText.toLowerCase();
                       data = largeLoad.filter(function(item) {
                                               return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
                                           });
                   }else {
                       data = largeLoad;
                   }

                    $scope.totalServerItems = data.length;

                    var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
                    $scope.myData = pagedData;
                    $scope.pagingOptions.totalServerItems = largeLoad.length;

                    if (!$scope.$$phase) {
                      $scope.$apply();
                    }
                }
                $('#myPleaseWait').modal('hide');
          };


          $scope.getPagedDataAsync = function(pageSize, page) {
              var area = angular.element( document.querySelector('#serviceAreaList') ).val();
              var days = angular.element( document.querySelector('#lastDays') ).val();
              if(area=='')
                area = "All";

              $('#myPleaseWait').modal('show');
              setTimeout(function() {
                  $http.get('/jobscheduler/spring/getActiveTeamData/' + area + "/" + days).success(function(largeLoad) {
                    $scope.allData = largeLoad;
                    $scope.setPagingData(largeLoad, page, pageSize);
                  });
              }, 100);
            };

          $scope.getPagedDataCached = function(pageSize, page) {
                 $scope.setPagingData($scope.allData, page, pageSize);
          };

         $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

        $scope.populateGridData = function() {
              $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            };

        $scope.$watch('pagingOptions', function() {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
          }, true);

        $scope.$watch('filterOptions', function() {
            $scope.getPagedDataCached($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }, true);

        $scope.$watch('sortOptions', function() {
              $scope.getPagedDataCached($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.sortOptions, $scope.filterOptions.filterText);
        }, true);

		$scope.gridOptions = { data: 'myData',showGroupPanel: true,filterOptions: $scope.filterOptions,showColumnMenu:true, showFilter:true,
	  		    selectedItems: $scope.mySelections,
	  		    multiSelect: false,
	  		    enablePaging: true,
	  		    totalServerItems: 'totalServerItems',
                pagingOptions: $scope.pagingOptions,
                useExternalSorting: true,
                enablePinning: true,
                showFooter: true,
	  		    rowHeight: 22,
	  		    enableColumnResize: true,
	  		    columnDefs: [
                    { field: 'BBL', displayName: 'BBL' },
                    { field: 'Address', displayName: 'Address' },
                    { field: 'DateSubmitted', displayName: 'Date Submitted', cellFilter: 'date:\'MM/dd/yyyy\'' },
                    { field: 'Status', displayName: 'Request Status'},
                    { field: 'ServiceId', displayName: 'Service Id' },
                    { field: 'RequestType', displayName: 'Request Type' },
                    { field: 'ServiceStatusImg', displayName: '', cellTemplate: '<img src="data:image/png;base64,{{row.entity.ServiceStatusImg}}"  height="20" width="20" />', width: '30px' },
                    { field: 'ServiceStatus', displayName: 'Service Status'},
                    { field: 'OriginalOilConsumption', displayName: 'Original Oil Consumption', cellFilter:"number:0"},
                    { field: 'UpdatedOilConsumption', displayName: 'Updated Oil Consumption', cellFilter:"number:0"},
                    { field: 'ScopeOfWork', displayName: 'Scope of work', cellTemplate: '<div style="white-space : nowrap; overflow : hidden; display : inline-block; padding:2px 2px;" title="{{row.getProperty(col.field)}}" ondblclick="showMessageInFrame(\'{{row.getProperty(col.field)}}\')">{{row.getProperty(col.field)}}</div>'},
                    { field: 'ID', displayName: "Request", cellTemplate: '<a class="btn view-pdf" style="font-size: 11px;" onclick="getPDF(\'original\', {{row.entity.ID}});" href="#">Original</a>  &nbsp;<a class="btn view-pdf" style="font-size: 11px;" onclick="getPDF(\'updated\', {{row.entity.ID}});" href="#">Updated</a>' },
                    { field: 'Map', displayName: "Map", cellTemplate: '<a class="btn view-pdf" style="font-size: 11px;" onclick="showInFrame(\'{{row.entity.Map}}\');" href="#">View</a>  ' }
                                         ]
	  	};


		$scope.getServiceAreaDataFromServer = function() {

			$http({method: 'GET', url: '/jobscheduler/spring/getServiceAreaData'}).
			      success(function(data, status, headers, config) {
			      	$scope.serviceAreaList = data;
			      }).
			      error(function(data, status, headers, config) {
			        // called asynchronously if an error occurs show here
			      });

			  };

	  });

  </script>
</head>

<body data-ng-controller="MyController" style="overflow: auto;">


    <nav class="navbar navbar-inverse">
          <div class="container-fluid">
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="navbar-header">
              <a class="navbar-brand" href="#dashboard">Dual Fuel</a>
            </div>


            <div id="navbar" class="navbar-collapse collapse">
              <ul class="nav navbar-nav">
                <li ng-class="active"><a href="/jobscheduler/index.jsp">Home <span class="sr-only">(current)</span></a></li>
              </ul>
              <ul class="nav navbar-nav navbar-right">
                <li><a href="/jobscheduler/spring/private/logout.do">Logout</a></li>
              </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>


	<div class='tab'>

	    <div  data-ng-init="getServiceAreaDataFromServer()" class="activeTeamsDropdownStyle">
	            <table>
	                <tr>
	                    <td style="padding:5px 5px;"><b>Service Area:</b></td>
	                    <td style="padding:5px 5px;"><select id="serviceAreaList" data-ng-model="selectedArea" data-ng-change="populateGridData();" style="font-size: 11px">
                                				<option value="">-- All --</option>
                                				<option data-ng-repeat="(key, value) in serviceAreaList" value="{{key}}">{{value}}</option>
                                			</select></td>
                        <td style="padding:5px 5px;"><b>Request submitted in :</b></td>
                        <td style="padding:5px 5px;"><select id="lastDays" data-ng-model="selectedDays" data-ng-change="populateGridData();" style="font-size: 11px">
                            <option value="7d">7 days</option>
                            <option value="30d">30 days</option>
                            <option value="90d">90 days</option>
                            <option value="1y">1 year</option>
                            <option value="All">All</option>
                        </select></td>
	                </tr>

	                 <tr>
                        <td style="padding:5px 5px;"><b>Filter Columns: </b></td>
                        <td style="padding:5px 5px;"><input type="text" data-ng-model="filterOptions.filterText" /></td>
                     </tr>
	            </table>
        </div>

		<div class="filler"></div>
		<div class="gridModelStyle" data-ng-grid="gridOptions"></div>

	</div>
	<!-- Modal Start here-->
    <div class="modal bs-example-modal-sm" id="myPleaseWait" tabindex="-1"
        role="dialog" aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">
                        <span class="glyphicon glyphicon-time">
                        </span> &nbsp;&nbsp;Please wait .....
                     </h4>
                </div>
                <div class="modal-body">
                    <div class="progress">
                        <div class="progress-bar progress-bar-info progress-bar-striped active"
                        style="width: 100%">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal ends Here -->

</body>

</html>