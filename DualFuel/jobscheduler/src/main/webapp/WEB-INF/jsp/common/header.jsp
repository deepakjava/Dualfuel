<%@include file="../common/common.jsp"%>

<div class="masthead">
	<ul class="nav nav-pills pull-right">
		<li class="active"><a href="${contextPath}/index.jsp">Home</a></li>
		<li><a href="#about" role="button" data-toggle="modal">About</a></li>
		<li><a href="${secureSpringPath}/logout.do" role="button" data-toggle="modal">Logout</a></li>
	</ul>
	<h3 class="muted">Job Scheduler</h3>
</div>

<!-- Modal -->
<div id="about" class="modal hide fade" tabindex="-1" role="dialog"
	aria-labelledby="aboutLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true"></button>
		<h3 id="aboutLabel">Job Scheduler V4.01</h3>
	</div>
	<div class="modal-body">
		<p>This is licensed to dualfualcorp.com</p>
		<p></p>
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
	</div>
</div>

<hr>