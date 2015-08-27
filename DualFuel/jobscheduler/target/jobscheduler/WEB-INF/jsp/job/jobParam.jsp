<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:forEach var="field" items="${jobParams}">
	<div class="control-group">
		<label class="control-label" for="${field.id}">${field.displayName}</label>
		<div class="controls">
			<c:choose>
				<c:when test="${field.fieldType == 'CERTIFICATE' }">
					<select id="${field.id}" name="${field.id}"
						placeholder="${field.displayName}" ${field.requiredStr}
						style="width: ${field.htmlWidth}px;" ${field.readOnlyStr}>
						<option value="">--select--</option>
						<c:forEach var="certAlias" items="${allCertOption}">
						<option value="${certAlias.certificateAlias }">${certAlias.certificateAlias }</option>
						</c:forEach>
					</select>
				</c:when>
				<c:otherwise>
					<input type="text" id="${field.id}" value="${field.defaultValue}"
						name="${field.id}" placeholder="${field.displayName}"
						${field.requiredStr} style="width: ${field.htmlWidth}px;"
						${field.readOnlyStr} />

				</c:otherwise>

			</c:choose>

		</div>
	</div>
</c:forEach>