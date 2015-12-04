var localUser = getLocalUser();
function GetRequest() {
   var url = location.search; 
   var theRequest = new Object();
   if (url.indexOf("?") != -1) {
      var str = url.substr(1);
      strs = str.split("&");
      for(var i = 0; i < strs.length; i ++) {
         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
      }
   }
   return theRequest;
}
/**
 * @author 郑强
 * 验证本地用户的token，自动登录
 * @param callback 回调函数 返回一个user
 */
function checkLocalUser(callback){
	getLocalUser(function(user){
		if(user){
			$.ajax({
				url:platform_url+"/user/checkUserToken",
				cache:false,
				type:"POST",
				data:{
					token:user.userToken,
					mobile:user.mobile
				},
				dataType:"json",
				success:function(data){
					if(data&&data.userToken){
						saveLocalUser(data,function(){
							if(callback && typeof callback == "function"){
								callback(true);
							}
						});
						console.log("自动登录成功");
					}else{
						callback(false);
						console.log("登录失效");
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					callback(false);
					console.log("网络异常");
				}
			});
		}
	});
}

/**
 * @author 郑强
 * 得到本地数据库的用户
 * @param callback 回调函数
 */
function getLocalUser(callback){
	var user = JSON.parse(localStorage.getItem("localUser"));
	if(user && user.userToken &&  user.isLogin == 1){
		if(callback && typeof callback == "function"){
			callback(user);
		}
		return user;
	}else{
		if(callback && typeof callback == "function"){
			callback(null);
		}
		return null;
	}
	/*var odb = getOpenDatabase();
	if(odb){
		var db = odb("info", "", "",10 * 1024 * 1024,function(){});
		function handle(tx,r){
			var len = r.rows.length;
			if(len == 1){
				var data = r.rows.item(0);
				localStorage.setItem("localUser",JSON.stringify(data));
				if(callback && typeof callback == "function"){
					callback(data);
				}
			}else{
				if(len > 1)logout();
				if(callback && typeof callback == "function"){
					callback(null);
				}
			}
		}
		db.transaction(function (tx) {
			var querySQL = "select * from User where isLogin = '1'";
			tx.executeSql(querySQL,[],handle,function(tx,error){
				dbError(tx, error);
				if(callback && typeof callback == "function"){
					callback(null);
				}
			});
		});
	}*/
}

/**
 * 退出登录
 * @param callback
 */
function logout(callback){
	localStorage.removeItem("localUser");
	/*var odb = getOpenDatabase();
	if(odb){
		var db = odb("info", "", "",10 * 1024 * 1024,function(){});
		db.transaction(function (tx) {
			var querySQL = "update User set isLogin = '0'";
			tx.executeSql(querySQL,[],function(tx, r){
				dbSuccess(tx, r);
				if(callback && typeof callback == "function"){
					callback();
				}
			},dbError);
		});
	}*/
}

/**
 * 
 * @param user
 * @author zhengqiang
 * @discription 保存用户到本地数据库和localstorage
 */
function saveLocalUser(user,callback){
	user.isLogin = 1;
	localStorage.setItem("localUser",JSON.stringify(user));
	if(callback && typeof callback == "function"){
		callback();
	}
	/*var odb = getOpenDatabase();
	if(odb&&user){
		var db = odb("info", "", "",10 * 1024 * 1024,function(){});
		var fields = "";
		var fflap = "";
		var values = [];
		for(var field in user){
			fields += field + ",";
			fflap += "?,";
			values.push(eval("user."+field));
		}
		values.push("1");
		fields += "isLogin";
		fflap += "?";
		function doInsert(){
			db.transaction(function (tx) {
				var querySQL = 'INSERT INTO USER(' + fields + ") VALUES("+ fflap +")";
				tx.executeSql(querySQL,values,function(tx, r){
					dbSuccess(tx, r);
					localStorage.setItem("localUser",JSON.stringify(user));
					console.info("数据同步成功！");
					if(callback && typeof callback == "function"){
						callback();
					}
				},dbError);
			});
		}
		db.transaction(function (tx) {
			var querySQL = "CREATE TABLE IF NOT EXISTS USER(" + fields +")";
			tx.executeSql(querySQL,[],function(tx,r){
				dbSuccess(tx, r);
				console.info("创建表成功！");
				
				db.transaction(function (tx) {
					var querySQL = "select * from User where mobile = ?";
					tx.executeSql(querySQL,[user.mobile],function(tx,r){
						var len = r.rows.length;
						if(len > 0){
							db.transaction(function (tx) {
								var querySQL = "delete from User where mobile = ?";
								tx.executeSql(querySQL,[user.mobile],function(tx,r){
									doInsert();
								},dbError);
							});
						}else{
							doInsert();
						}
					},dbError);
				});
				
			},function(tx, error){
				dbError(tx, error);
			});
		});
	}*/
}

function setLocalUserField(field,value,callback){
	getLocalUser(function(user){
		if(user){
			eval("user."+field+"=value");
			callback = function(){
				if(callback && typeof callback == "function"){
					callback(true);
				}
			}
			saveLocalUser(user,callback);
		}else{
			if(callback && typeof callback == "function"){
				callback(false);
			}
		}
	});
}

/**
 * @author 郑强
 * @param tx
 * @param r
 * 数据库操作成功公共函数
 */
function dbSuccess(tx,r){
	console.info('coperated data success ----'+r.rowsAffected );
}

/**
 * @author 郑强
 * @param tx
 * @param error
 * 数据库操作失败公共函数
 */
function dbError(tx,error){
	console.info(error);
}

/** 判断浏览器是否支持WebSql数据库* */
function getOpenDatabase(){  
	try {  
		// 如果支持则返回数据库连接句柄  
		if (!!window.openDatabase) {  
			return window.openDatabase;
		} else {  
			return undefined;  
		}  
	} catch (e) {  
		return undefined;  
	}  
}