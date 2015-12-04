$(function(){
	
	document.addEventListener('deviceready', function(){
		
		
		 cordova.exec(function(data){
				    console.info('-----------------  true  ---------------' + data);
					//toInfoDetail(data) ;
		    }, null, 'BaiduPush', 'messageGet', []);

	
	}, false);
	
	
	var odb,db ;
	odb = getOpenDatabase();
	if(odb){
 		db = odb("pushDB", "1.0", "",10 * 1024 * 1024,function(){});
 		db.transaction(function (tx) {
	 		var SQL = 'select * from MessageRecord where id = ?';
			var oid = localStorage.oid ;
	 		tx.executeSql(SQL,[oid],function(tx,result){
				if(result.rows.length>0){
					//toInfoDetail(result.rows.item(0))
				}
			},function(){e})
 		 })
	}

	$("#goback").tap(function(){
		goback();
	})
	function toInfoDetail(data){
		if(data.type==0){
			$("#acttitle").show();
		}
		if(data.type==1){
			$("#systitle").show();
		}
		$(".tc").text(data.title) ;
		$(".content").html(data.content) ;
	}
	function getOpenDatabase(){  
		try {  	  
			if (!!window.openDatabase) {  
				return window.openDatabase;
			} else {  
				return undefined;  
			}  
		} catch (e) {  
			return undefined;  
		}  
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
		
		

		
});


/**
 * 获取url参数 (name 参数名),查询不到返回null
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var url = window.location.search.replace(/&amp;/gi,'&');
    var r = decodeURI(url).substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}


	
