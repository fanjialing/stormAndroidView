$(function(){
	if(localUser&&localUser.userToken){
		if(localUser.baodairenzhengzige){
			var card = localUser.baodairenzhengzige;
			$("#name").html(card.name);
			$("#gender").html(card.gender);
			$("#category").html(card.category);
			$("#secondCategory").html(card.secondCategory);
			$("#credential").html(card.credential);
			if(card.indate!=null&&card.indate.length>0){
				$("#indate").html(card.indate);
			}else{
				$("#indate").html("永久有效");
			}
		}
	}
});