/**
 * Created by g on 2015/11/13.
 * Author ZhangChao
 * description:表单的验证及密码显示明文插件
 */
var form={
    init:function(){

    },
    ZhaohuiPassword:function(){

    },
    formLoading:function(flag){
        $(function(){
            if(flag==1){
                $("#loading").show();
            }else if(flag==0){
                $("#loading").hide();
            }
        });
    },
    PasswordTabBtn:function(){
        $(function(){
            //调用插件
            $('#password').togglePassword({
                el:'#togglePassword'
            });
        });
    },
    validCode:function(){
        $(function(){
            var validCode=true;
            $(".check-code-btn").tap(function () {
                var time=120;
                var code=$(this);
                if (validCode) {
                    validCode=false;
                    code.addClass("change-bg");
                    var t=setInterval(function  () {
                        time--;
                        code.html(time+"秒");
                        if (time==0) {
                            clearInterval(t);
                            code.html("重新获取");
                            validCode=true;
                            code.removeClass("change-bg");
                        }
                    },1000);
                }
            });
        });
    }
}
;(function ($) {
$.fn.togglePassword = function( options ) {
    var s = $.extend( $.fn.togglePassword.defaults, options ),
        input = $( this );
    $( s.el ).on( s.ev, function() {
        "password" == $( input ).attr( "type" ) ?
            $( input ).attr( "type", "text" ) :
            $( input ).attr( "type", "password" );
        if($(s.el).find(".off").attr("class")=="off"){
            $(s.el).addClass("abc_bg");
            $(s.el).find("span.off").addClass("on");
            $(s.el).find("span#txt").text("abc");
        }else{
            $(s.el).removeClass("abc_bg");
            $(s.el).find(".off").removeClass("on");
            $(s.el).find("span#txt").text("*****");
        }
    });
};
$.fn.togglePassword.defaults = {
    ev: "tap"
};
}( Zepto ));