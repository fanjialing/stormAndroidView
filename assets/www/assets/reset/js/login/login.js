var mUrl = GetRequest()["murl"];
var loginIng = false;
function login(mobile,password){
	if(loginIng)return;
	$.ajax({
		url:platform_url+"/user/login",
		cache:false,
		contentType:"application/json",
		type:"POST",
		data:JSON.stringify({
			employeeId:mobile,
			password:password
		}),
		dataType:"json",
		beforeSend:function(){
			loginIng = true;
			$("#loading").show();
		},
		success:function(data){
			loginIng = false;
			if(data.msgCode){
				window.showtoast(data.message);
			}else{
				saveLocalUser(data);
				$("#loading").hide();
				go.main("main.html",mUrl);
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			loginIng = false;
			$("#loading").hide();
			window.showtoast("登录超时");
		}
	});
}
$(function(){
	$("#lg_bt").tap(function(){
		var mobile = $("#mobile").val();
		var password = $("#password").val();
		if(mobile&&password){
			login(mobile,password);
			//go.main("main.html",mUrl);
		}
	});
	
	$("#reg").tap(function(){
		go.reg("login_reg.html?murl="+mUrl);
	});
	
	$("#forgotPwd").tap(function(){
		go.forgotPwd("login_forgotPwd.html");
	});
});