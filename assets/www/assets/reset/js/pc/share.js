/**
 * Created by g on 2015/11/20.
 * Author:zhangchao
 */
var share={
    init:function(){
        $(function(){
            $(".menu,.butn").tap(function(){
                $(".share-fixed").show();
            });
            $("#quxiao-share").tap(function(){
                $(".share-fixed").hide();
            });
            
            $("#goback").tap(function(){
    			goback();
            });
        });
    }
}
share.init();