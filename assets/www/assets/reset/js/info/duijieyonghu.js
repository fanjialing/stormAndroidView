var companyId = GetRequest()["companyId"];
$(function(){
	var isLoading = false;
	$("#submit_bt").tap(function(){
		if(isLoading)return;
		var username = $.trim($("#account").val()),idCard6 = $.trim($("#idCard6").val());
		if(username==null||username.length==0){
			isLoading = false;
			window.showtoast("请输入员工账号");
			return;
		}
		if(idCard6==null||idCard6.length!=6){
			isLoading = false;
			window.showtoast("身份证后6位不正确");
			return;
		}
		$.ajax({
			url:registercenter_url+"/validateUserByNameAndIdCard6",
			type:"POST",
			dataType:"json",
			data:{
				userName:username,
				idCard6:idCard6
			},
			beforeSend:function(){
				isLoading = true;
				$("#loading_msg").html("正在验证账号...");
				$("#loading").show();
			},
			success:function(data){
				isLoading = false;
				$("#loading").hide();
				if(data!=null){
					if(data.error){
						window.showtoast(data.error);
					}else{
						getCompanyInfo(username,companyId);
					}
				}else{
					window.showtoast("操作失败");
				}
			},
			error:function(){
				isLoading = false;
				$("#loading").hide();
				window.showtoast("操作失败");
			}
		});
	});
});

function getCompanyInfo(userName,companyId){
	$.ajax({
		url:registercenter_url+"/confirmUserInformation",
		type:"POST",
		dataType:"json",
		data:{
			userName:userName,
			companyId:companyId,
			mobile:""
		},
		beforeSend:function(){
			$("#loading_msg").html("正在获取身份信息...");
			$("#loading").show();
		},
		success:function(data){
			$("#loading").hide();
			if(data!=null){
				if(data.error){
					window.showtoast(data.error);	
				}else{
					localUser.companyInfo = data;
					saveLocalUser(localUser);
					go.local("pc_info_shenfenxinxi_baocun.html");
				}
			}else{
				window.showtoast("操作失败");
			}
		},
		error:function(){
			$("#loading").hide();
			window.showtoast("操作失败");
		}
	});
}