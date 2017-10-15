/*存放主要交互逻辑js*/
/*js 要尽量模块化*/
var seckill = {
    //封装秒杀ajax的地址 url
    URL: {

    },
    //验证手机号
    validatePhone:function(phone){
       /* if(phone && phone.length === 11 && !isNaN(phone)){
            return true;
        }else{
            return false;
        }*/
        return !!(phone && phone.length === 11 && !isNaN(phone));
    },
    //详情页秒杀逻辑
    detail :{
        //详情页初始化
        init: function (params) {
            //手机验证和登录， 计时交互
            //规划我们的交互流程
            //在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            //未登录的时候
            if(!seckill.validatePhone(killPhone)){
                //选则该节点
                var killPhoneModal = $('#killPhoneModal');
                //显示弹出层
                killPhoneModal.modal({
                    show: true,//显示弹出层
                    backdrop: 'static',//禁止位置关闭
                    keyboard: false//禁止按esc关闭
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    if(seckill.validatePhone(inputPhone)){
                        //电话写入cookie
                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});
                        //刷新页面
                        window.location.reload();
                    }else{
                        //打印错误信息
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误</label>').show(300);
                    }
                });
            }
        }
    }
}