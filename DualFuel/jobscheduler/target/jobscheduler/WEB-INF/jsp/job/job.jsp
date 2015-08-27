<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="../common/common.jsp"%>

<div class="jumbotron">

	<br />
	<form action="${secureSpringPath}/job/service/action.htm"
		method="post">
		<p><span style="font-size: 20px; font-weight: bold; padding: 0 20px;">SERVICE :</span>
			<c:choose>
				<c:when test="${isJobSchedularRunning}">
					<button class="btn btn-large btn btn-success" type="submit">RUNNING</button>
				</c:when>
				<c:otherwise>
					<button class="btn btn-large btn btn-warning" type="submit">STOPPED</button>
				</c:otherwise>
			</c:choose>
		</p>
	</form>

</div>


<div class="row-fluid marketing">

	<form action="${secureSpringPath}/job/add.htm" method="get">
		<p>
			<button class="btn btn-primary" type="submit">Add New Job</button>
		</p>
	</form>

	<div id="jobTable">
		<jsp:include page="jobTable.jsp"></jsp:include>
	</div>

</div>

<!-- Button to trigger modal -->
<!-- Modal -->
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">�</button>
		<h3 id="myModalLabel">ERROR LOG</h3>
	</div>
	<div class="modal-body" id ="logBody">
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
	</div>
</div>


<script type="text/javascript">

	$(document).ready(function() {
		updateJobTable();
	});
	
	function updateJobTableOnce() {
		//$("#status").html('Loading..');   
		var urlString = "${secureSpringPath}/job/update/table";

		$
				.ajax({
					type : 'GET',
					url : urlString,
					timeout : 5000,
					success : function(data) {
						$("#jobTable").html(data);
						//alert(data);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						$("#status")
								.html(
										'<div id="error">!!!! DOWN !!!! DOWN !!!!  Timeout contacting server. Make sure server is up.</div>');
						// alert(errorThrown);
					}
				});
	}
	
	function updateJobTable() {
		//$("#status").html('Loading..');   
		var urlString = "${secureSpringPath}/job/update/table";

		$
				.ajax({
					type : 'GET',
					url : urlString,
					timeout : 5000,
					success : function(data) {
						$("#jobTable").html(data);
						//alert(data);
						window.setTimeout(updateJobTable, 5000);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						$("#status")
								.html(
										'<div id="error">!!!! DOWN !!!! DOWN !!!!  Timeout contacting server. Make sure server is up.</div>');
						// alert(errorThrown);
						window.setTimeout(updateJobTable, 5000);
					}
				});
	}
</script>
