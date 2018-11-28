/*<![CDATA[*/
    	//添加
    	function addCom(){
    		layer.open({
    	        type: 2,
    	        title: "添加店铺",
    	        area: ["1200px", "670px"],
    	        btn: false,
    	        content: ['/addShop', 'no'],
    	        end : function() {
					// 如果是通过单击关闭按钮关闭弹出层，父画面没有此表单
					if ($("#popupFormDiv").length === 1) {
						$("#popupFormDiv").submit();
					}
				}
    	    });
    	};
    	//查看
    	function view(id){
    		layer.open({
    	        type: 2,
    	        title: "店铺详情",
    	        area: ["1200px", "670px"],
    	        btn: false,
    	        content: ['/viewShop?id='+id, 'no'],
    	        end : function() {
					// 如果是通过单击关闭按钮关闭弹出层，父画面没有此表单
					if ($("#popupFormDiv").length === 1) {
						$("#popupFormDiv").submit();
					}
				}
    	    });
    	};
    	//修改
    	function update(id){
    		layer.open({
    	        type: 2,
    	        title: "修改店铺信息",
    	        area: ["1200px", "670px"],
    	        btn: false,
    	        content: ['/updateShop?id='+id, 'no'],
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
    	                url:'deleteShop',
    	                type:'post',
    	                data:'id='+id,
    	                success:function(c){
    	                    //console.log(c)
    	                    if(c.success == "删除成功！"){
    	                    	window.location.href="/shopList";
    	                    }
    	                }
    	            });
    	    	  
    	    	}, function(){
    	    	  layer.close();
    	    	});
    	};
    	/*]]>*/