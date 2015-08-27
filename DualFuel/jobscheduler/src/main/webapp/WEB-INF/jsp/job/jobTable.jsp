<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="../common/common.jsp"%>

<table class="table table-bordered">
	<thead>
		<tr style="background-color: #474747; color: white;">
			<th rowspan="2" style="vertical-align: middle; text-align: center;">Name</th>
			<th rowspan="2" style="vertical-align: middle; text-align: center;">Cron</th>
			<th rowspan="1" colspan="3"
				style="vertical-align: middle; text-align: center;">Last Run</th>
			<th rowspan="2" style="vertical-align: middle; text-align: center;">Next
				Run</th>
			<th rowspan="2" style="vertical-align: middle; text-align: center;"></th>
		</tr>
		<tr style="background-color: #474747; color: white;">
			<th rowspan="1" style="vertical-align: middle; text-align: center;">time</th>
			<th rowspan="1" style="vertical-align: middle; text-align: center;">status</th>
			<th rowspan="1" style="vertical-align: middle; text-align: center;">message</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="jobByGrp" items="${allJob}">
			<c:forEach var="job" items="${jobByGrp}" varStatus="index">
				<c:if test="${index.count==1 }">
					<tr style="background-color: #757575; color: #303030;">
						<td colspan="7" style="text-align: center; text-transform: uppercase;"><b>${job.jobGroup}</b></td>
					</tr>
				</c:if>
				<c:choose>
					<c:when test="${fn:contains(runningJob, job.jobName)}">
						<tr class="success">
					</c:when>
					<c:when test="${not fn:contains(scheduledJob, job.jobName)}">
						<tr class="disabled">
					</c:when>
					<c:otherwise>

						<c:choose>
							<c:when test="${empty job.latestStatus}">
								<tr>
							</c:when>
							<c:when test="${job.latestStatus.status=='SUCESS'}">
								<tr>
							</c:when>
							<c:when test="${job.latestStatus.status=='TRY_LATER'}">
								<tr class="warning">
							</c:when>
							<c:otherwise>
								<tr class="error">
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>

				<td>${job.jobName}</td>
				<td>${job.jobTrigger}</td>
				<td>${job.latestStatus.jobEndTime}</td>
				<td><c:choose>
						<c:when test="${empty job.latestStatus}">
						</c:when>
						<c:when test="${job.latestStatus.status=='SUCESS'}">
							<img src="${contextPath}/bootstrap/img/sucess.png" width="25"
								height="25" /> ${job.latestStatus.status}
							</c:when>
						<c:when test="${job.latestStatus.status=='TRY_LATER'}">
							<img src="${contextPath}/bootstrap/img/warning.png" width="25"
								height="25" /> ${job.latestStatus.status}
							</c:when>
						<c:otherwise>
							<img src="${contextPath}/bootstrap/img/error.png" width="25"
								height="25" />  ${job.latestStatus.status}
							</c:otherwise>
					</c:choose></td>

				<td><c:choose>
						<c:when test="${job.latestStatus == null}">
					${job.latestStatus.message}
				</c:when>

						<c:when test="${job.latestStatus.status=='SUCESS'}">
					${job.latestStatus.message}
				</c:when>
						<c:otherwise>
					${job.latestStatus.message} <a href="#myModal" role="button"
								data-toggle="modal" ctype="logFile"
								logid="${job.latestStatus.id}"><img
								src="${contextPath}/bootstrap/img/error_log_icon.png" width="25"
								height="25" /></a>
						</c:otherwise>
					</c:choose></td>
				<td></td>
				<td><c:choose>
						<c:when test="${fn:contains(runningJob, job.jobName)}">
							<img src="${contextPath}/bootstrap/img/loading.gif" width="25"
								height="25" />  Running...
					</c:when>
						<c:when test="${not fn:contains(scheduledJob, job.jobName)}">
							<img src="${contextPath}/bootstrap/img/warning.png" width="25"
								height="25" />
							<b>NOT scheduled</b>
						</c:when>
						<c:otherwise>
							<a href='${secureSpringPath}/job/runJob?jobId=${job.jobName}'
								defaultaction="false">Run Job</a>
						</c:otherwise>
					</c:choose></td>
				</tr>
			</c:forEach>
		</c:forEach>
		<!--  
		<tr class="error">
			<td>Ercot</td>
			<td>Invoice</td>
			<td>7/18/2013 3:00 PM</td>
			<td>7/18/2013 4:00 PM</td>
			<td>SUCESS</td>
			<td>All Good</td>
		</tr>
		-->
	</tbody>
</table>

<script type="text/javascript">
	$(function() {
		$('a').click(
				function(event) {

					var url = $(this).attr('href');
					var defaultaction = $(this).attr('defaultaction');
					var ctype = $(this).attr('ctype');

					if (defaultaction == 'false') {
						event.preventDefault();
						$.get(url, function(data) {
							$('.result').html(data);
							updateJobTableOnce();
							//alert('Request Submitted');
						});
					} else {

						if (ctype == 'logFile') {
							var logid = $(this).attr('logid');
							$.get(
									"${secureSpringPath}/job/errorLog.htm?logId="
											+ logid, function(data) {
										$('#logBody').html(data);
									});
						}

					}

				});
	});
</script>