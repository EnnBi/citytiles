
<div class="col-md-6 col-lg-4 grid-margin stretch-card">
	<div class="card">
		<div class="card-body">
			<div class="d-flex align-items-center justify-content-md-center">
				<div class="ml-3">
					<p class="mb-0">Today's Revenue</p>
					<h6>12569</h6>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="col-md-6 col-lg-4 grid-margin stretch-card">
	<div class="card">
		<div class="card-body">
			<div class="d-flex align-items-center justify-content-md-center">
				<div class="ml-3">
					<p class="mb-0">Today's Expenditure</p>
					<h6>2346</h6>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="col-md-6 col-lg-4 grid-margin stretch-card">
	<div class="card">
		<div class="card-body">
			<div class="d-flex align-items-center justify-content-md-center">
				<div class="ml-3">
					<p class="mb-0">Balance</p>
					<h6>896546</h6>
				</div>
			</div>
		</div>
	</div>
</div>



<div class="col-md-12  grid-margin stretch-card">
	<div class="card">
		<div class="card-body">
			<h4 class="card-title">Bar chart</h4>
			<canvas id="bar-chart"></canvas>
		</div>
	</div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.slim.min.js" integrity="sha512-6ORWJX/LrnSjBzwefdNUyLCMTIsGoNP6NftMy2UAm1JBm6PRZCO1d7OHBStWpVFZLO+RerTvqX/Z9mBFfCJZ4A==" crossorigin="anonymous"></script>
<script src="/resources/vendors/chart.js/Chart.min.js"></script>
<script>

$(document).ready(function(){

	var url="/bar-chart";
	var names=[];
	var dataSet={};
	var labels = [];
	 initChart();
	/* $.get(url,function(data){
		data.forEach(function(item,index){
			if(names.indexOf(item.name)<0)
				names.push(item.name);
			
			console.log("length  "+dataSet[item.name].length);
			dataSet[item.name].push(item.quantity);
			if(labels.indexOf(item.date)<0)
				labels.push(item.data);
		});
		
		console.log(dataSet);
	}); */
	
	function initChart(){
		var ctx = document.getElementById("bar-chart");
		var data = {
		        datasets: [{
		            type: 'bar',
		            label: 'Product 1',
		            data: [10, 20, 30, 40],
		            backgroundColor:'#00c7ff'
		        
		        }, {
		            type: 'bar',
		            label: 'Product 2',
		            data: [20, 10, 4, 15],
		            backgroundColor:'#ff003f'
		        }],
		        labels: ['January', 'February', 'March', 'April']
		    }
		    
		    var options ={
		        scales: {
		            y: {
		              beginAtZero: true
		            }
		          }
		        }
			var myBarChart = new Chart(ctx, {
			    type: 'bar',
			    data: data,
			   options: options
			});
	}
});
</script>




