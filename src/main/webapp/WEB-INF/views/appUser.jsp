<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<!-- <h4 class="card-title">Horizontal Two column</h4> -->
			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">${success}</div>
			</c:if>
			<c:if test="${not empty fail}">
				<div class="alert alert-danger" role="alert">${fail}</div>
			</c:if>

			<c:choose>
				<c:when test="${edit}">
					<c:set value="/app-user/update" var="action" />
					<c:set var="caption" value="Update" />
				</c:when>
				<c:otherwise>
					<c:set value="/app-user/save" var="action" />
					<c:set var="caption" value="Save" />

				</c:otherwise>
			</c:choose>

			<form:form action="${action}" modelAttribute="user" method="post">
				<%-- <form:hidden path="id" />
				<input type="hidden" name="p" value="${currentPage}" /> --%>
				<p class="card-description">AppUser Info</p>

				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<span class="input-group-addon label-left" id="basic-addon2">Select
								UserType</span>
							<form:select path="userType" class=" form-control">
								<form:option value="">Select any UserType *</form:option>
								<form:options items="${UserList}" itemLabel="name"
									itemValue="id" />
							</form:select>
							<form:errors class="err" path="userType" />
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Name</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control" path="name" />
								<form:errors path="name" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Contact</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control" path="contact" />
								<form:errors path="contact" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Address</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control" path="address" />
								<form:errors path="address" />
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<span class="input-group-addon label-left" id="basic-addon2">Select
								Sites</span>
							<form:select path="sites" class="form-control"
								multiple="multiple">
								<form:option value="">Select any Sites *</form:option>
								<form:options items="${siteList}" itemLabel="name"
									itemValue="id" />
							</form:select>
							<form:errors class="err" path="sites" />
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Ledger Number</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control" path="ledgerNumber" />
								<form:errors path="ledgerNumber" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Account Number</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control"
									path="accountNumber" />
								<form:errors path="accountNumber" />
							</div>
						</div>
					</div>
				</div>
				<div class="form-group row float-right">
					<input type="submit" class="btn btn-success btn-fw"
						" value="${caption}">
				</div>
			</form:form>
		</div>

	</div>
</div>

