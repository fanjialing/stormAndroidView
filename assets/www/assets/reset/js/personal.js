$(function(){
	 document.addEventListener('deviceready', init(),false);
});

/*function onTakImageSuccess(imgUrl){
    var url = "data:image/jpeg;base64," + imgUrl;  
    document.getElementById('myImage').src=url;
    alert(url);
}*/
function onLoadImageSuccess(imageUrl){
	alert(imageUrl);
	var mimg = document.getElementById('myImage');
	mimg.src = imageUrl;
	getLocalUser(function(user){
		if(user&&user.userToken){
			var img = document.createElement("img");
			img.src = imageUrl;
			img.addEventListener("load",function(){
				var data = getBase64Image(img);
				$.ajax({
					url:"http://www.4mylove.cn/user/updateface/18883774456",
					type:"POST",
					cache:false,
					data:{
						data:data
					},
					success:function(msg){
						if(msg == 'success'){
							console.log("头像修改成功！");
							user.face = "data:image/png;base64,"+data;
							localStorage.setItem("localUser",JSON.stringify(user));
						}else{
							console.log(msg);
						}
					},
					error:function(){
						console.log("网络异常");
					}
				});
			},false);
		}
	});
}
function init(){
	$("#idMsg").tap(function(){
		window.go(2,"www/personal-form.html");
	});
	$("#_goback").tap(function(){
		window.goback();
	});
	$("#face").tap(function(){
	    navigator.camera.getPicture(onLoadImageSuccess, onLoadImageFail,{
	    	sourceType: Camera.PictureSourceType.SAVEDPHOTOALBUM,
	    	destinationType: Camera.DestinationType.FILE_URI//DATA_URL
	 	});
	});
	getLocalUser(function(user){
		if(user&&user.userToken){
			var face = user.face;
			if(!/^data:image\/(png|jpg);base64,/.test(face)){
				var img = document.getElementById("myImage");
				img.src = face;
				img.addEventListener("load",function(){
					var data = getBase64Image(img);
					user.face = "data:image/png;base64,"+data;
					localStorage.setItem("localUser",JSON.stringify(user));
				});
			}else{
				$("#myImage").attr("src",face);
			}
		}
	});
}

function onLoadImageFail(message){
    window.showtoast(message);
}

//获取img标签的base64
function getBase64Image(img) {
    var canvas = document.createElement("canvas");
    canvas.width = img.width;
    canvas.height = img.height;

    var ctx = canvas.getContext("2d");
    ctx.drawImage(img, 0, 0, img.width, img.height);

    var dataURL = canvas.toDataURL("image/png");
    //return dataURL
    return dataURL.replace(/^data:image\/(png|jpg);base64,/,"");
}
//生成UUID
function uuid() {
	 var s = [];
	 var hexDigits = "0123456789abcdef";
	 for (var i = 0; i < 36; i++) {
		 s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	 }
	 s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
	 s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
	 s[8] = s[13] = s[18] = s[23] = "";
	 var uuid = s.join("");
	 return uuid;
} 