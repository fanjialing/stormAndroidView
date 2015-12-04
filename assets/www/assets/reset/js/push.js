$(function(){
	
	//WEBSQL操作
	var odb,db ;
	odb = getOpenDatabase();
	if(odb){
		//初始化或打开数据库
 		db = odb("pushDB", "1.0", "",10 * 1024 * 1024,function(){});
 		db.transaction(function (tx) {
	 		//建表
	 		var createSQL = 'create table if not exists MessageRecord(id text,type text,title text,cover text,content text,createTime text ,isRead int) ';
	 		tx.executeSql(createSQL,[],function(tx,result){},function(){e})
 		 
 		 })
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

		//添加监听事件
		document.addEventListener('deviceready', onloadSuccess, false);

		//初始化消息列表
		toActList();
		toSysList();

		//异步获取服务器消息列表，同步至本地，同时更新本地消息
		downMessage() ;

		function onloadSuccess(){
		    cordova.exec(function(e){
		    			
		    }, null, 'BaiduPush', 'startWork', ['keep']);
		   cordova.exec(function(e){
   				//保存至本地
   				if(e.data){
	   				saveMessage(e.data) ;
	   				if(e.data.type==0){
	   					//活动信息
	   					toActList() ;
	   				}else{
	   					//系统信息
		   				toSysList() ;
	   				}
   				}
   			}, function(e){}, 'BaiduPush', 'listenNotificationArrived', []);
		   			
/*		    cordova.exec(function(e){
				var oid = e.data.id ;
				localStorage.oid = oid ;
				//活动消息
				
				if(e.data.type==0){
					$("#actList li").each(function(){
						if($(this).attr("oid") == oid){
							var alt = $(this).children("img").attr('alt') ;
							if(alt){
								go.remote(alt) ;
							}else{
								go.local("infodetail.html") ;
							}
						}
						
					})
				}
				//系统消息
				
				if(e.data.type==1){
					go.local("infodetail.html") ;
				}
				
		    }, function(e){}, 'BaiduPush', 'listenNotificationClicked', []);*/

		
		}
		//保存至本地
		function saveMessage(data){
			if(odb){
				 db.transaction(function (tx) {
				 	//数据插入
					var SQL = "insert into MessageRecord values(?,?,?,?,?,?,?)" ;
				 	tx.executeSql(SQL,[data.id,data.type,data.description,data.coverbook,data.content,data.sendTime,0],function(tx,result){},function(){e})
					//查询总记录数
					var SELECTSQL = "select * from MessageRecord order by createTime desc" ;
					tx.executeSql(SELECTSQL,[],function(tx,result){
						if(result.rows.length>100){
							//删除最老的一条数据
							var dele_id = result.rows.item(100).id ;
							var DELSQL = "delete  from MessageRecord  where id = ?" ;
							tx.executeSql(DELSQL,[dele_id],function(tx,result){},function(){e})
						}
					},function(){e})

				 })
			}
		}
		//系统信息
		function toSysList(){
			if(odb){
				 db.transaction(function (tx) {
				 	var SELECTSQL = "select * from MessageRecord where type=1 order by createTime desc" ;
				 	tx.executeSql(SELECTSQL,[],function(tx,result){
						if(result.rows){
							var list = [] ;
							for(var i=0;i<result.rows.length;i++){
								console.log(result.rows.item(i));
								list.push('<li oid='+result.rows.item(i).id+'>') ;
								list.push('<a href="javascript:void(0)" class="clear over">') ;
								list.push('<span class="fl f16">系统消息</span>') ;
								list.push('<span class="time">'+result.rows.item(i).createTime.substring(0,16)+'</span>') ;
								list.push('</a>') ;
								list.push('<p class="f14 c-9a">'+result.rows.item(i).title+'</p>') ;
								list.push('</li>')  ;
							}
							$("#infoList").empty();
							$("#infoList").prepend(list.join('')) ;  
							imglink() ;  
						}
					},function(){e})
				 })
			}
			    
		}
		//活动信息
		function toActList(){
			if(odb){
				 db.transaction(function (tx) {
				 	var SELECTSQL = "select * from MessageRecord where type=0 order by createTime desc" ;
				 	tx.executeSql(SELECTSQL,[],function(tx,result){
						if(result.rows){
							var list = [] ;
							for(var i=0;i<result.rows.length;i++){
								list.push('<ul>')
								list.push('<time>'+ result.rows.item(i).createTime.substring(0,16) + '</time>') ;
								list.push('<li oid="'+result.rows.item(i).id+'">') ;
								list.push(result.rows.item(i).cover) ;
								list.push('<a  href="javascript:void(0)" class="clear over">'+result.rows.item(i).title+'</a>') ;
								list.push('</li>') ;
								list.push('</ul>')
							}
							 $("#actList").empty();
							 $("#actList").prepend(list.join('')) ; 
							ullink() ;  
						}
					},function(){e})
				 })
			}
		}

		//异步获取服务器消息列表，同步至本地，同时更新本地消息
		function downMessage(){
			


		}
		//系统消息点击
		function imglink(){
			$("#infoList li").click(function(){
				var oid = $(this).attr('oid') ;
				localStorage.oid = oid ;
				go.local("infodetail.html") ;
			})
		}
		
		//图片点击
		function ullink(){
			$("#actList li").click(function(){
				var alt = $(this).children("img").attr('alt') ;
				if(alt){
					go.remote(alt) ;
				}else{
					var oid = $(this).attr('oid') ;
					localStorage.oid = oid ;
				 	go.local("infodetail.html") ;
				}
			})
		}

		
});


	
