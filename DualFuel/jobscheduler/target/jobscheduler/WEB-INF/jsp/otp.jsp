<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@include file="common/common.jsp"%> 
    
<div class="row-fluid marketing">

	<form id = "form-signin" class="form-signin" action="${springPath}/validateOTPLogin" method="post">
        <p class="text-info">Enter OTP sent your your email address</p>
        <h2 class="form-signin-heading">time left : <span id="timer"></span></h2>
        <p><img src="${contextPath}/private/security/image" /></p>
        <input id = "optPassword" name="optPassword" type="password" class="input-block-level" placeholder="Enter OTP">
        <button class="btn btn-large btn-primary" type="submit">CONTINUE</button>
      </form>


</div>


<script type="text/javascript">

	$(document).ready(function() {
		//alert("Hi");
		checkLogin();
		window.setTimeout(submitForm, 60000);
		runTimer();
	});
	
	var time = 61;
	function runTimer() {
		time = time-1;
		$("#timer").html(time);
		window.setTimeout(runTimer, 1000);
	}
	
	function submitForm() {
		$("form").submit();
	}
	
	function checkLogin() {
		//$("#status").html('Loading..');
	
		var urlString = "${springPath}/validOTPPage";
		$
				.ajax({
					type : 'GET',
					url : urlString,
					timeout : 5000,
					success : function(data) {
						var response = (data == 'FALSE') ? false : true;
						
						if(response == true){
					
							window.setTimeout(checkLogin, 500);
						}else{
							
							window.location =  "${contextPath}/index.jsp";
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(errorThrown);
						window.setTimeout(checkLogin, 2000);
					}
				});
	}

</script>