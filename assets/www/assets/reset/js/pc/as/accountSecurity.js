	$(function(){
		$("#phoneBind").tap(function(){
		    go.local("pc_accoutSecurity_phoneBind.html");
		    
		});
		
		$("#modifyPwd").tap(function(){
		    go.local("pc_accoutSecurity_modifyPwd.html");
		    
		});
		
		$("#goback").tap(function(){
			goback();
		});
	});