	function up(){
		$.post("/report/chklogin",
				{"verify":$("#verify").val()},
				function(result){
					alert(result);
				}
		)}
	
	function saveReportInfo(){
		var json=getJsonData();
		var url="/report/saveReportInfo";
 	    $.ajax({
	        type:"POST", 
	        url:url, 
	        dataType:"json",      
	        contentType:"application/json",               
	        data:JSON.stringify(json), 
	        success:function(result){
	        	console.log(result)
	    		alert(result);
	        } 
	     });
	}
	
	function getJsonData(){

		var buyList=new Array(19);//按照顺序为数组赋值(代买)
		var sellList=new Array(19);//按照顺序为数组赋值(销售)
		

		for(var i =0;i<sellList.length;i++){
			buyList[i]=i*100+200
			sellList[i]=500;
		}
		console.log(buyList);
		var json={"buyList":buyList,"sellList":sellList};
		return json;
	}
	
	function getByDate(){
		var url="/report/findByDate";
		$.post(url,{"date":$("#date").val()},function(result){
			console.log(result)
		})
	}