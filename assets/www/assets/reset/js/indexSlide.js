/**
 * Created by g on 2015/11/13.
 * Author ZhangChao
 */
var Slide={
    startX:0,//手指起始值
    endX:0,//手指结束值
    offsetX:0,//偏移量
    startTime:0,//触点开始记时
    endTime:0,//结束记时
    speed:200,//动画时间
    $id:"",
    touchesLeft:function(ev){
        var bfbW=parseInt($(window).width()*0.15);
        var bfbWEnd=parseInt($(window).width()*0.85);
        var self = this;
        switch(ev.type){
            case 'touchstart':
                self.startTime = new Date() * 1;
                self.startX = ev.touches[0].clientX;
                ev.preventDefault();
                break;
            case 'touchmove':
                self.endX = ev.touches[0].clientX;
                self.offsetX = self.endX - self.startX;
                if(self.offsetX < 0){
                    $(".user-center-section").find(".user").css({'-webkit-transform':'translate3d('+self.offsetX+'px, 0px, 0px)'});
                }
                console.log(self.offsetX);
                break; b
            case 'touchend':
                self.endTime = new Date() * 1;
                if(self.endTime-self.startTime >50) {
                    if (self.offsetX < -20) {
                        console.log(bfbWEnd);
                        $(".user-center-section").find(".user").animate({left:'0%'},self.speed);
                        setTimeout(
                            function(){$(".user-center-section").find(".user").css({'-webkit-transform':'translate3d(0px, 0px, 0px)',transition:'-webkit-transform .2s ease-out'})},800
                        );
                        $(".user-center-section-mask").hide();
                    }else{
                        $(".user-center-section").find(".user").css({'-webkit-transform':'translate3d(0px, 0px, 0px)',transition:'-webkit-transform .2s ease-out'});
                    }
                }
                break;
        }
    },
    init:function(id){
    	document.addEventListener('deviceready', function(){
    	var h=device.height;
            //计算高度
       	 	//document.addEventListener('deviceready', oninitSuccess, false);

            //$(".info-list").height(h-44);/**/
            // console.log(Slide.touches);
            $("#tap1").tap(function(){
                $(".user-center-section").find(".user").animate({left:'100%'},self.speed);
                $(".user-center-section-mask").show(self.speed);
            });
            $("#tap2").tap(function(){
                $(".info-section").find(".bfb90").animate({right:'100%'},self.speed);
                $(".info-section").find(".sub-container").animate({right:'100%'},self.speed);
                $(".info-section-mask").css({display:'block',background:'rgba(0,0,0,.3)', transition:'background 1.5s linear'});
                $(".info-section-mask1").show();
            });
            Slide.$id = document.getElementById(id);
            Slide.$id.addEventListener('touchstart', Slide.touches, false);
            Slide.$id.addEventListener('touchmove', Slide.touches, false);
            Slide.$id.addEventListener('touchend', Slide.touches, false);
            document.getElementById("touchesLeft").addEventListener('touchstart', Slide.touchesLeft, false);
            document.getElementById("touchesLeft").addEventListener('touchmove', Slide.touchesLeft, false);
            document.getElementById("touchesLeft").addEventListener('touchend', Slide.touchesLeft, false);
            $(".user-center-section-mask").tap(function(){
                $(this).hide();
                $(".user-center-section").find(".user").animate({left: '0%'});
            });
            $(".info-section-mask1,a.back").tap(function(){
                $(".info-section-mask1").hide();
                $(".info-section-mask").hide();
                $(".info-section").find(".bfb90").animate({right:'0%'},Slide.speed);
                $(".info-section").find(".sub-container").animate({right:'0%'},Slide.speed);
            });
            //消息中心tab选项卡
            $(".tab span").tap(function(){
                var index=$(".tab span").index(this);
                $(".tab span").removeClass("this").eq(index).addClass("this");
                $(".con").addClass("none").eq(index).removeClass("none");
                if(index==1){
                    $(".tip-system").hide(200);
                }
            });
        },false);
    },
    isBool:function(start,end){
        var flag = false;
        if(end > start){
            flag = true;
            return flag;
        }
        return flag;
    },
    oninitSuccess:function(e){
  	  var h=device.height;

    },
    touches:function(ev){
        //计算容器宽度剩余的百分比
        var bfbW=parseInt($(window).width()*0.15);
        var self = this;
        //alert(bfbW);

        switch (ev.type){
            case 'touchstart':
                self.startTime = new Date() * 1;
                self.startX = ev.touches[0].clientX;
                ev.preventDefault();
                break;
            case 'touchmove':
                self.endX = ev.touches[0].clientX;
                self.offsetX = self.endX - self.startX;
               // console.log(self.offsetX);
                //向右
                if(Slide.isBool(self.startX,self.endX)){
                    $(".user-center-section").find(".user").css("left", self.offsetX+bfbW);
                    //清除右侧缝隙
                    $(".info-section").find(".bfb90").css("right",-(self.offsetX+bfbW));
                    $(".sub-container").css("right",-(self.offsetX+bfbW));
                }else{
                    //向左
                    $(".info-section").find(".bfb90").css("right",-(self.offsetX-bfbW));
                    $(".sub-container").css("right",-(self.offsetX-bfbW));
                    //清除左滑缝隙
                    $(".user-center-section").find(".user").css("left", self.offsetX-bfbW);
                }
                break;
            case "touchend":
                self.endTime = new Date() * 1;
                if(self.endTime-self.startTime >50){
                    if(self.offsetX > 100){
                        // $(".index-section").animate({left:'90%'});
                        // $(".index-section").find("header").animate({left:'90%'});
                        $(".user-center-section").find(".user").animate({left:'100%'},self.speed);
                        $(".user-center-section-mask").show(self.speed);
                        //当向右滑时清除消息中心
                        $(".info-section").find(".bfb90").animate({right:'0%'},self.speed);
                        $(".info-section").find(".sub-container").animate({right:'0%'},self.speed);
                        $(".info-section-mask").hide(self.speed);
                    }else if(self.offsetX < -100){
                        $(".info-section").find(".bfb90").animate({right:'100%'},self.speed);
                        $(".info-section").find(".sub-container").animate({right:'100%'},self.speed);
                        //$(".info-section-mask").show(500);
                        $(".info-section-mask").css({display:'block',background:'rgba(0,0,0,.3)', transition:'background 1.5s linear'});
                        $(".info-section-mask1").show(self.speed);
                        //当向左滑时清除个人中心
                        $(".user-center-section").find(".user").animate({left:'0%'},self.speed);
                        $(".user-center-section-mask").hide(self.speed);
                    } else{
                        $(".user-center-section").find(".user").animate({left: '0%'},self.speed);
                        $(".info-section").find(".bfb90").animate({right:'0%'},self.speed);
                        $(".info-section").find(".sub-container").animate({right:'0%'},self.speed);
                        $(".info-section-mask").hide(self.speed);
                        $(".info-section-mask1").hide(self.speed);
                    }
                    //清除偏移量
                    self.offsetX=0;
                }else{
                    //当时间<100毫秒时
                    // $(".index-section").animate({left:'0%'});
                    // $(".index-section").find("header").animate({left:'0%'});
                    /* */
                    $(".user-center-section").find(".user").animate({left:'0%'},self.speed);
                    $(".user-center-section-mask").hide(self.speed);
                    $(".info-section").find(".bfb90").animate({right:'0%'},self.speed);
                    $(".info-section").find(".sub-container").animate({right:'0%'},self.speed);
                    $(".info-section-mask").hide(self.speed);
                }
                ev.preventDefault();
                break;
        }
    }

}


function oninitSuccess(){

	

}