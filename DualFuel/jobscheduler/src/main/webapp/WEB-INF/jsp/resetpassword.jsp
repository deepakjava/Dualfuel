<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="common/common.jsp"%>

<div class="row-fluid marketing">

	<form id="form-signin" class="form-signin"
		action="${secureSpringPath}/user/savePassword" method="post">
		<p class="text-info">Enter New Password</p>
		<input id="newPassword" name="newPassword" type="password"
			class="input-block-level" placeholder="Enter new password"
			minlength="5" required>
		<input id="varifyPassword" name="varifyPassword" type="password"
			class="input-block-level" placeholder="Enter new password again"
			comparePassword=true>
		<button class="btn btn-large btn-primary" type="submit">UPDATE</button>
	</form>


</div>


<script type="text/javascript">
	$(function() {
		$("#form-signin").validate();
	});
	
	jQuery.validator.addMethod("comparePassword", function(value, element) {

		var p1 = $("#newPassword").val();
		var p2 = $("#varifyPassword").val();
		if(p1 != p2){
			return false;
		}else{
			return true;
		}
	}, "* Password dont match.");

	jQuery.validator.addClassRules({
		comparePassword : {
			comparePassword : true
		}
	});
</script>