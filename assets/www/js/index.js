function onPageLoad(){
  document.addEventListener('deviceready', onDeviceReady, false);
}

function onDeviceReady() {
  btnShowMessage('deviceready');
}

function btnShowMessage(message){
    //cordova.exec(null,null,"intbirdShowToast","toast",[message]);
    window.showtoast(message);
}

function btnShowWebView(flag){

	switch(flag){
			case 0:
			    document.getElementById("btnShowWebView").value = "EEE"
			
    window.go(".NodeActivity","www/main.html");
			break;
		    case 1:

    window.go(".NodeActivity","www/personal.html");
			break;
		default:
			break;
	}
}

function _goback(){
		window.goback();
}

function onLoadImageFail(message){
    window.showtoast(message);
}

function btnTakePicture(){
    navigator.camera.getPicture(onTakImageSuccess, onLoadImageFail,
     {
     sourceType:Camera.PictureSourceType.CAMERA,
     destinationType: Camera.DestinationType.DATA_URL
     });
}
function onTakImageSuccess(imgUrl){
    var url = "data:image/jpeg;base64," + imgUrl;  
    document.getElementById('myImage').src=url;
}

function btnLoadPicture(){
    navigator.camera.getPicture(onLoadImageSuccess, onLoadImageFail,
     {
     sourceType: Camera.PictureSourceType.SAVEDPHOTOALBUM,
     destinationType: Camera.DestinationType.FILE_URI
     });
}
function onLoadImageSuccess(imageUrl){
    document.getElementById('myImage').src=imageUrl;
}


// console test
function btnLog(){
	var ua = window.navigator.userAgent.toLowerCase();    	console.log('log  is  verbose ');
        console.debug('debug  is  debug ');
        console.error('error  is  error ');
        console.info(ua);
        console.warn('warn  is  warn ');
    
}

function msg(){

//	baidupush.BaiduPush.prototype.startWork('keep',function(e){
//				console.log(e);
//	});
    cordova.exec(function(e){
    			
    }, null, 'BaiduPush', 'startWork', ['keep']);
    
    
   
   cordova.exec(function(e){console.log(e);}, function(e){console.log(e);}, 'BaiduPush', 'listenNotificationArrived', []);
}


// websql test
function websql(){
var odb ;
var db ;
	odb = getOpenDatabase();
				console.log('---');
	
if(odb){
		 db = odb("mydb", "1.0", "",10 * 1024 * 1024,function(){
		 });
		
		 db.transaction(function (tx) {
			 // 创建表
			var createSQL = 'CREATE TABLE IF NOT EXISTS LOGS (id unique, log)';

			tx.executeSql(createSQL,[],function(tx,r){
						
							console.log('create table true');
					},function(tx,error){							
					console.info('create table false');
			});

			 // 插入数据
			 
			var save_SQL = 'INSERT INTO LOGS (log) VALUES (?)';
			tx.executeSql(save_SQL,["kee"],function(tx,r){
							//flag = true ;
								console.log('insert table true'+r.rowsAffected );
					},function(tx,error){
							//flag = false ;
							
					console.info('insert table false');
			});
				
			 // 删除数据
			var deleteSQL = 'delete from LOGS where id=1';
			tx.executeSql(deleteSQL,[],function(tx,r){
							//flag = true ;
								console.info('delete data success ----'+r.rowsAffected );
					},function(tx,error){
							//flag = false ;
							
					console.info(error);
			});

			// 修改数据
			var deleteSQL = 'update LOGS set log ="hahha" where id= 5';
			tx.executeSql(deleteSQL,[],function(tx,r){
					console.log('update data success ----'+r.rowsAffected );
					},function(tx,error){			
					console.info(error);
			});


			//查询数据
			var querySQL = 'select * from LOGS';
			tx.executeSql(querySQL,[],function(tx,r){
						var len = r.rows.length;

				 for (i = 0; i < len; i++){
						console.info(r.rows.item(i));
		

					}
					},function(tx,error){
												
					console.info(error);
			});
	
	 });
}

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


