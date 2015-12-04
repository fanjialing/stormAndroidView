$(function(){
	$("#goback").tap(function(){
		goback();
	});
	$("#face").tap(function(){
	    navigator.camera.getPicture(onLoadImageSuccess, onLoadImageFail,{
	    	sourceType: Camera.PictureSourceType.SAVEDPHOTOALBUM,
	    	destinationType: Camera.DestinationType.FILE_URI//DATA_URL
	 	});
	});
	if(localUser&&localUser.userToken){
		$("#name").html(localUser.name);
		$("#idMsg").tap(function(){
			if(localUser.isNewUser){
				go.local("pc_info_shoucishuru_xuanzegongsi.html");
			}else{
				go.local("pc_info_shenfenxinxi.html");
			}
		});
		if(localUser.agentCertificateAvailable){
			$("#agentCertificateAvailable_State").html("已通过认证");
		}else{
			$("#agentCertificateAvailable_State").html("未通过认证");
		}
		var getIng = false;
		$("#agentCertificateAvailable").tap(function(){
			if(localUser.agentCertificateAvailable){
				if(localUser.baodairenzhengzige){
					go.local("pc_info_danbaorenzige.html");
				}else{
					if(getIng)return;
					$.ajax({
						url:platform_url+"/user/getAgentCertificate",
						type:"POST",
						dataType:"json",
						data:{
							agentCertificateNo:localUser.agentCertificateNo
						},
						beforeSend:function(){
							getIng = true;
							$("#loading").show();
						},
						success:function(data){
							getIng = false;
							$("#loading").hide();
							if(data.message){
								window.showtoast("获取保代资格证失败");
							}else{
								localUser.baodairenzhengzige = data;
								saveLocalUser(localUser);
								go.local("pc_info_danbaorenzige.html");
							}
						},
						error:function(){
							getIng = false;
							$("#loading").hide();
						}
					});
				}
			}else{
				go.local("pc_info_baodairenzigerenzheng.html");
			}
		});
		var face = localUser.face;
		if(!/^data:image\/(png|jpg);base64,/.test(face)){
			var img = document.getElementById("myImage");
			img.src = face;
			$(img).on({
				load:function(){
					var data = getBase64Image(img);
					localUser.face = "data:image/png;base64,"+data;
					localStorage.setItem("localUser",JSON.stringify(localUser));
				},
				error:function(){
					img.src = "assets/reset/images/face.jpg";
				}
			});
		}else{
			$("#myImage").attr("src",face);
		}
	}
});
function onLoadImageSuccess(imageUrl){
	if(!(/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(imageUrl) || /^(content)/.test(imageUrl))){
		window.showtoast("图片格式不正确！");
		return;
	}
	var mimg = document.getElementById('myImage');
	mimg.src = imageUrl;
	if(localUser&&localUser.userToken){
		var img = document.createElement("img");
		img.src = imageUrl;
		img.addEventListener("load",function(){
			var data = getBase64Image(img);
			$.ajax({
				url:platform_url+"/user/face/update",
				type:"POST",
				cache:false,
				data:{
					data:data,
					mobile:localUser.mobile
				},
				dataType:"json",
				success:function(data){
					if(data.state){
						window.showtoast("头像修改成功！");
						localUser.face = "data:image/png;base64,"+data;
						localStorage.setItem("localUser",JSON.stringify(localUser));
					}else{
						window.showtoast(data.msg);
					}
				},
				error:function(){
					window.showtoast("头像修改失败！");
				}
			});
		},false);
	}
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