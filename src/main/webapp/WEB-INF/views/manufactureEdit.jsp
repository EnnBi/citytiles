<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="col-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<h4 class="card-title">Manufacturing</h4>
			<form:form action="/manufacture/save" modelAttribute="manufacture"
				method="post">
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Product</label>
							<div class="col-sm-9">
								<form:hidden path="id"/>
								<form:select path="product" class=" form-control" id="product">
									<form:option value="">Select any Product</form:option>
									<form:options items="${products}" itemLabel="name"
										itemValue="id" />
								</form:select>
								<form:errors class="err" path="product" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Date</label>
							<div class="col-sm-9">
								<form:input type="date" class="form-control" path="date" required="required"/>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Size</label>
							<div class="col-sm-9">
								<select class="form-control" id="size" name="size">
									<option value="">Select size</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group row">
							<label class="col-sm-6 col-form-label">CPU</label>
							<div class="col-sm-6">
								<form:input class="form-control" placeholder="Cost Per Unit" path="cpu" id="cpu" required="required"/>
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group row">
							<label class="col-sm-6 col-form-label">Cement</label>
							<div class="col-sm-6">
								<form:input class="form-control" placeholder="Cement" path="cement" required="required"/>
							</div>
						</div>
					</div>
				</div>
				<hr>
				<div class="row">
					<div class="col-md-12">
						<div class="form-group row">
							<div class="col-sm-11">
								<p class="card-description">Labour Details</p>
							</div>
							<div class="float-right">
								<button type="button" class="btn btn-primary" id="add">Add</button>
							</div>
						</div>
					</div>
				</div>
				<hr>
				<c:forEach items="${manufacture.labourInfo}" var="labourInfo" varStatus="loop">
				
				<div class="labour-info">
					<div class="row">
						<div class="col-md-4">
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">Quantity</label>
								<div class="col-sm-9"> 
									<form:input class="form-control quantity" placeholder="Quantity" path="labourInfo[${loop.index}].quantity" required="required"/>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group row">
								<label class="col-sm-6 col-form-label">Total Amount</label>
								<div class="col-sm-6">
									<form:input class="form-control totalAmt" placeholder="Total Amount" path="labourInfo[${loop.index}].totalAmount" readonly="true"/>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group row">
								<label class="col-sm-7 col-form-label">Amount Per Head</label>
								<div class="col-sm-5">
									<form:input class="form-control amountPerHead" placeholder="Amount Per Head" path="labourInfo[${loop.index}].amountPerHead" readonly="true"/>
								</div>
							</div>
						</div>
						<div class="col-sm-1">
							<button type="button" class="btn btn-danger delete">
								<i class="mdi mdi-delete"></i>
							</button>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group row">
								<label class="col-sm-1 col-form-label">Labours</label>
								<div class="col-sm-11">
									<form:select path="labourInfo[${loop.index}].labours" class=" form-control labors"  required="required"
										multiple="multiple">
										<form:options items="${labours}" itemLabel="name"
											itemValue="id" />
									</form:select>
									<form:errors class="err" path="labourInfo[${loop.index}].labours" />
								</div>
							</div>
						</div>
					</div>
				</div>
				
				</c:forEach>
				
				<div class="row">

					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Total Amount</label>
							<div class="col-sm-9">
								<form:input path="totalAmount" class="form-control" id="totalAmount" readonly="true"/>
								<form:errors class="err" path="totalAmount" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Total Quantity</label>
							<div class="col-sm-9">
								<form:input path="totalQuantity" class="form-control" id="totalQuantity" readonly="true"/>
								<form:errors class="err" path="totalQuantity" />
							</div>
						</div>
					</div>
					
				</div>
			
			<div class="form-group row float-right">
							<input type="submit" class="btn btn-success btn-fw"
								value="Submit">
						
			</div>
		</form:form>
		</div>
	</div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.slim.min.js" integrity="sha512-6ORWJX/LrnSjBzwefdNUyLCMTIsGoNP6NftMy2UAm1JBm6PRZCO1d7OHBStWpVFZLO+RerTvqX/Z9mBFfCJZ4A==" crossorigin="anonymous"></script>

<script type="text/javascript">
$(document).ready(function(){
	$("#add").click(function(){
		$(".labour-info:last").clone().insertAfter(".labour-info:last");
		$(".labour-info:last").find(':input')
		.val('');
		updateIndex()
	});
	
	$(document).on('click','.delete',function(e){
		if($('.labour-info').length>1)
			$(this).parent().parent().parent().remove();
		
		updateIndex();
		 updateAmountAndQuantity();
	})
	
	$('#product').change(function(){
		var id = $(this).val();
		var url = "/product/" + id + "/sizes";
		$.get(url,function(data){
			$('#size').find('option').not(':first')
			.remove();
			$.each(data, function(key, value) {
				$('#size').append($(
						"<option></option>").attr(
						"value", value.id).text(
						value.name));
			});
		});
	})
	
	$(document).on('change','#cpu ,.quantity,.labors',function(){
		 updateAmountAndQuantity();
	});
	
	function updateAmountAndQuantity(){
		var cpu = Number($('#cpu').val());
		var totalQuantity=0,totalAmount=0;
		$('.quantity').get().forEach(function(entry, index, array) {
			var labourInfoDiv = entry.closest(".labour-info")
			var quantity = Number($(entry).val());
			var totalAmt = Number(cpu*quantity);
			$(labourInfoDiv).find(".totalAmt").val(totalAmt.toFixed(2));
			var length  =  $(labourInfoDiv).find(".labors :selected").length;
			aph = length>0?Number(totalAmt/length):0;
			$(labourInfoDiv).find('.amountPerHead').val(aph.toFixed(2));
			totalAmount=totalAmount+totalAmt;
			totalQuantity=totalQuantity+quantity;
		});
		$('#totalQuantity').val(Number(totalQuantity.toFixed(2)));
		$('#totalAmount').val(Number(totalAmount.toFixed(2)));
	}
	
	function updateIndex() {
		$('.labour-info').get().forEach(function(entry, index, array) {
				$(entry).find('input, select').each(
						function() {
							var name = $(this).attr(
									'name').replace(
									/\[(.+?)\]/g,
									"[" + index + "]");
							$(this).attr('name', name)
						});
			});
	}
});
</script>
