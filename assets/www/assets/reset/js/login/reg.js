var validCode=true;
$(function(){
	$("#goback").tap(function(){
		goback();
	});
    $(".check-code-btn").tap(function () {
    	if(!validCode)return;
    	$.ajax({
    		url:platform_url+"/user/checkMobileAndGetCode",
    		type:"POST",
    		data:{
    			mobile:$("#mobile").val()
    		},
    		beforeSend:function(){
    			countdownFunc();
    		},
    		dataType:"json",
    		success:function(data){
    			if(data&&data.state){
    				window.showtoast("验证码发送成功!");
    			}else{
    				window.showtoast(data.msg);
    				endCountDown(); 
    			}
    		},
    		error:function(){
    			 endCountDown(); 
    		}
    	});
    });
    var isLogin = false;
    $("#login_bt").tap(function(){
    	if(isLogin)return;
    	var mobile = $("#mobile").val(),validateCode = $("#validateCode").val(),password = $("#password").val();
    	if(!/^0?1[3|4|5|8][0-9]\d{8}$/.test(mobile)){
    		window.showtoast("手机号码格式错误!");
    		return;
    	}
    	if(!/^[0-9]{4}$/.test(validateCode)){
    		window.showtoast("验证码格式错误!");
    		return;
    	}
    	if(!/^.{6,20}$/.test(password)){
    		window.showtoast("密码长度至少6位，最多20位!");
    		return;
    	}
    	$.ajax({
    		url:platform_url + "/user/regiterUser",
    		type:"POST",
    		dataType:"json",
    		data:{
    			mobile:mobile,
    			password:password,
    			code:validateCode
    		},
    		beforeSend:function(){
    			isLogin = true;
    			$("#loading").show();
    		},
    		success:function(data){
    			isLogin = false;
    			$("#loading").hide();
    			if(data){
    				if(data.message){
    					window.showtoast(data.message);
    				}else{
    					var mUrl = GetRequest()["murl"];
    					//window.showtoast(mUrl);
    					saveLocalUser(data);
    					go.main("main.html",mUrl);
    				}
    			}else{
    				window.showtoast("注册失败!");
    			}
    		},
    		error:function(){
    			isLogin = false;
    			$("#loading").hide();
    		}
    	});
    });
});
var countdown;
function countdownFunc(){
	 var time=60;
     var code=$(".check-code-btn");
     if (validCode) {
         validCode=false;
         code.addClass("change-bg");
         countdown = setInterval(function  () {
             code.html(time+"秒");
             if (time<=0) {
                 clearInterval(countdown);
                 code.html("重新获取");
                 validCode=true;
                 code.removeClass("change-bg");
             }
             time--;
         },1000);
     }
}
function endCountDown(){
	clearInterval(countdown);
	$(".check-code-btn").html("获取验证码");
	validCode=true;
}