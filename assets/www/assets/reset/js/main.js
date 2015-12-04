$(function(){
	//暂时不做每次进入应用的登录验证
	/*var first_in = sessionStorage.getItem("first_in");
	if(first_in)checkLocalUser(function(state){
		if(!state){
			logout();
			unlogined();
		}else{
			logined();
		}
	});*/
	$("#pInfo").tap(function(){
		if(localUser){
			go.local("pc_info.html");
		}else{
			 go.login("login.html?murl=pc_info.html");
		}
	});
	$("#accountSecurity").tap(function(){
		if(localUser){
			go.local("pc_accountSecurity.html");
		}else{
			go.login("login.html?murl=pc_accountSecurity.html");
		}
	});
	
	$("#a1").tap(function(){
	    go.remote("http://www.baidu.com");
	});
	
	$("#aboutBaofeng").tap(function(){
	    go.local("pc_aboutBaofeng.html");
	});
	
	$("#shareApp").tap(function(){
	    go.local("pc_share.html");
	});
});