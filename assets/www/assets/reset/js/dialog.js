/**
 * Created by g on 2015/11/20.
 * Author:zhangchao
 */
var Dialog={
    Confirm:function(title,content){
        var strHtml='<div class="dialog" id="confirm"><div class="dialog-wrap"><div class="title">'+title+'</div><div class="content"><p>'+content+'</p></div><div class="bottom"><div class="but fl" id="quxiao">取消</div><div class="but fr" id="quding">确定</div></div></div></div>';
        $('body').append(strHtml);
        $("#confirm").find("#quxiao").tap(function(){
            $("#confirm").remove();
        });
        $("#confirm").find("#quding").tap(function(){
            $("#confirm").remove();
        });
    },
    Alert:function(title,content){
        var strHtml='<div class="dialog" id="alert"><div class="dialog-wrap"><div class="title">'+title+'</div><div class="content"><p>'+content+'</p></div><div class="bottom"><div class="btn-one" id="quding">确定</div></div></div></div>';
        $('body').append(strHtml);
        $("#alert").find("#quding").tap(function(){
            $("#alert").remove();
        });
    }
}
