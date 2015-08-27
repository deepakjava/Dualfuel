<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@include file="common/common.jsp"%>
    
<div class="row-fluid marketing">

	<form class="form-signin" action="${springPath}/validateLogin">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input id = "emailAddress" name="emailAddress" type="text" class="input-block-level" placeholder="Email address">
        <input id = "password"  name="password" type="password" class="input-block-level" placeholder="Password">
        <button class="btn btn-large btn-primary" type="submit">Sign in</button>
      </form>


</div>