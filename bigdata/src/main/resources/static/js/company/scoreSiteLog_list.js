/*<![CDATA[*/
    	//添加
    	function addCom(){
    		layer.open({
    	        type: 2,
    	        title: "添加站点评价",
    	        area: ["1200px", "670px"],
    	        btn: false,
    	        content: ['/addScoreSiteLog', 'no'],
    	        end : function() {
					// 如果是通过单击关闭按钮关闭弹出层，父画面没有此表单
					if ($("#popupFormDiv").length === 1) {
						$("#popupFormDiv").submit();
					}
				}
    	    });
    	};
    	//查看
    	/*function view(id){
    		layer.open({
    	        type: 2,
    	        title: "商品详情",
    	        area: ["1200px", "670px"],
    	        btn: false,
    	        content: ['/viewArticle?id='+id, 'no'],
    	        end : function() {
					// 如果是通过单击关闭按钮关闭弹出层，父画面没有此表单
					if ($("#popupFormDiv").length === 1) {
						$("#popupFormDiv").submit();
					}
				}
    	    });
    	};*/
    	//修改
    	function update(id){
    		layer.open({
    	        type: 2,
    	        title: "修改评价信息",
    	        area: ["1200px", "670px"],
    	        btn: false,
    	        content: ['/updateScoreSiteLog?id='+id, 'no'],
    	        end : function() {
					// 如果是通过单击关闭按钮关闭弹出层，父画面没有此表单
					if ($("#popupFormDiv").length === 1) {
						$("#popupFormDiv").submit();
					}
				}
    	    });
    	};
    	// 删除
    	function deleteC(id) {
    	    layer.confirm('您确定要删除吗？', {
    	    	  btn: ['确定','取消'] //按钮
    	    	}, function(){
    	    		// 删除公司
    	            $.ajax({
    	                url:'deleteScoreSiteLog',
    	                type:'post',
    	                data:'id='+id,
    	                success:function(c){
    	                    //console.log(c)
    	                    if(c.success == "删除成功！"){
    	                    	window.location.href="/scoreSiteLogList";
    	                    }
    	                }
    	            });
    	    	  
    	    	}, function(){
    	    	  layer.close();
    	    	});
    	};
    	/*]]>*/