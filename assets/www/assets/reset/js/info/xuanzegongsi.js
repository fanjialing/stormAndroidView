$(function(){
	$("#loading").show();
	$.ajax({
		url:platform_url+"/user/getTopCompany",
		type:"GET",
		dataType:"json",
		success:function(data){
			$("#loading").hide();
			if(data&&data.length>0){
				for(var i = 0;i< data.length;i++){
					var li = $("<li><a onclick=\"choseCompany('"+data[i].id+"')\">"+data[i].name+"</a></li>");
					$("#list").append(li);
				}
			}else{
				window.showtoast("获取公司信息失败");
			}
		},
		error:function(){
			$("#loading").hide();
			window.showtoast("获取公司信息失败");
		}
	})
});
var isLoading = false;
function choseCompany(cid){
	if(isLoading)return;
	isLoading = true;
	$("#loading").show();
	$.ajax({
		type:'POST',
		url:registercenter_url+"/getCompanyIsDocking.htm",      
		data:{"companyId":cid},
		dataType:"json",
		beforeSend:function(){
		},
        success:function(data){
        	isLoading = false;
        	if(data != null){
        		if(data.error){
        			window.showtoast(data.error);
        		}else{
        			if(data.isDocking){
        				go.local("pc_info_shoucishuru.html?companyId="+cid);
        			}else{
        				go.local("pc_info_shenfenxinxi1.html");
        			}
        		}
        	}else{
        		window.showtoast("获取公司信息失败");
        	}
        	$("#loading").hide();
        },
        error:function(data){
        	$("#loading").hide();
        	isLoading = false;
        	window.showtoast("用户中心连接超时，请联系客服");
        }
    });
}