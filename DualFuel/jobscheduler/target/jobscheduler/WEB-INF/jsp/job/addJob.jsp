<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="../common/common.jsp"%>


<form action="${secureSpringPath}/job/save/action.htm" method="post"
	class="form-horizontal" id="form-horizontal">
	<fieldset>
		<legend>Schedule New Job</legend>

		<div class="control-group">
			<label class="control-label" for="inputJobName">Job Name</label>
			<div class="controls">
				<input type="text" id="inputJobName" placeholder="Job Name"
					name="inputJobName" minlength="2" required class="input-xxlarge">
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="inputJobGroup">Job Group</label>
			<div class="controls">
				<input type="text" id="inputJobGroup" placeholder="Job Group"
					name="inputJobGroup" minlength="2" required class="input-medium">
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="inputTrigger">Trigger</label>
			<div class="controls">
				<input type="text" id="inputTrigger" placeholder="Trigger"
					name="inputTrigger" value="cron" required
					cronValidation=true minlength="5"> <span class="help-inline"> <a
					href="http://quartz-scheduler.org/documentation/quartz-1.x/tutorials/crontrigger"
					target="_blank">help</a></span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="inputJobType">Job Type</label>
			<div class="controls">
				<select id="inputJobType" placeholder="Job Type" name="inputJobType"
					required>
					<option value="">--- select here --</option>
					<c:forEach var="job" items="${allAvailableJob}">
						<option value="${job}">${job}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="inputRefireSec">Refire every</label>
			<div class="controls">
				<input type="text" id="inputRefireSec" placeholder=""
					name="inputRefireSec" value=""> <span class="help-inline">Refire time in seconds</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="inputRefireTill">Refire till</label>
			<div class="controls">
				<input type="text" id="inputRefireTill" placeholder=""
					name="inputRefireTill" value=""> <span class="help-inline">Refire Till time in seconds</span>
			</div>
		</div>

		<div id="jobParam"></div>
		<div class="control-group">
			<div class="controls">
				<button type="submit" class="btn btn-primary">Submit</button>
				<button type="button" class="btn btn-inverse" onclick="window.location='${secureSpringPath}/job/home.htm'">Cancel</button>
			</div>
		</div>
	</fieldset>
</form>

<script type="text/javascript">
	var response = true;
	jQuery.validator.addMethod("cronValidation", function(value, element) {

		var inputTrigger = $("#inputTrigger").val();

		$.get("${secureSpringPath}/validate/cronExpression", {
			inputCronExpression : inputTrigger
		}).done(function(data) {
			response = (data == 'FALSE') ? false : true;
		});
		return response;
	}, "* Cron Expression is wrong.");

	jQuery.validator.addClassRules({
		cronValidation : {
			cronValidation : true
		}
	});

	$(function() {

		$("#form-horizontal").validate();

		$("select").change(function() {

			var jobId = $("#inputJobType").val();

			var request = $.ajax({
				url : "${secureSpringPath}/job/param/",
				type : "POST",
				data : {
					jobId : jobId
				},
				dataType : "html"
			});

			request.done(function(msg) {
				$("#jobParam").html(msg);
			});

			request.fail(function(jqXHR, textStatus) {
				alert("Request failed: " + textStatus);
			});

		});
	});
</script>