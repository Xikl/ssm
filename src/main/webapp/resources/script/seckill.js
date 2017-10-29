/*存放主要交互逻辑js*/
/*js 要尽量模块化*/
var seckill = {
    //封装秒杀ajax的地址 url
    URL: {
        now: function () {
            return '/seckill/time/now';
        },
        exposer: function (seckillId) {
            return '/seckill/'+seckillId+'/exposer';
        },
        execution: function (seckillId, md5) {
            return '/seckill/'+seckillId+'/'+md5+'/execution';
        }


    },
    handleSeckill: function (seckillId, node) {
        //处理秒杀逻辑
        node.hide()
            .html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');//按钮
        $.post(seckill.URL.exposer(seckillId), {}, function (result) {
            //判断结果是否存在
            if (result && result['success']){
                var exposer = result['data'];
                //exposed为真
                if (exposer['exposed']){
                    //获得加密链接
                    var md5 = exposer['md5'];
                    //开始秒杀
                    //获得秒杀地址
                    var seckillUrl = seckill.URL.execution(seckillId, md5);
                    console.log(seckillUrl);
                    /*为秒杀按钮进行时间绑定*/
                    //使用one绑定一次点击事件
                    $('#killBtn').one("click", function () {
                        //禁用按钮
                        $(this).addClass('disabled');
                        //发送ur请求
                        $.post(seckillUrl, {}, function (result) {
                            if (result){
                                //获得秒杀结果
                                var seckillResult = result['data'];
                                var state = seckillResult['state'];
                                var stateInfo = seckillResult['stateInfo'];
                                //显示秒杀结果
                                node.html('<span class="label label-success">'+stateInfo+'</span>');
                            }
                        });
                    });
                    node.show();
                }else{
                    //未开始秒杀，用户系统时间太快不对！
                    /*获得服务端的时间*/
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    //再次执行计时操作
                    seckill.countDown(seckillId, now, start, end);
                }


            }else{
                console.log('result:'+ result);
            }
        })
    },
    //验证手机号
    validatePhone: function (phone) {
        /* if(phone && phone.length === 11 && !isNaN(phone)){
             return true;
         }else{
             return false;
         }*/
        return !!(phone && phone.length === 11 && !isNaN(phone));
    },
    /*计时方法*/
    countDown: function (seckillId, nowTime, startTime, endTime) {
        /*获得该id，显示时间 进行dom操作*/
        var seckillBox = $('#seckill-box');
        //时间判断
        if(nowTime > endTime){
            //秒杀结束
            seckillBox.html("秒杀结束！")
        }else if(nowTime < startTime){
            /*进行计时操作*/
            //防止计时编译，可能慢了
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime, function (event) {
                //格式化日期
                var format = event.strftime('秒杀计时： %D天 %H时 %M分 %S秒');
                //写入html中
                seckillBox.html(format);
                /*时间完成后回调函数*/
            }).on('finish.countdown', function () {
                //获取秒杀地址，执行秒杀
                seckill.handleSeckill(seckillId, seckillBox);
            });
        }else{
            /*秒杀开始*/
            seckill.handleSeckill(seckillId, seckillBox);
        }
    },

    //详情页秒杀逻辑
    detail :{
        //详情页初始化
        init: function (params) {
            //手机验证和登录， 计时交互
            //规划我们的交互流程
            //在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
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
            //已经登录
            /*计时交互*/
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            /*jquery执行get请求*/
            $.get(seckill.URL.now(), {}, function (result) {
                if (result && result['success']){
                    var nowTime = result['data'];
                    seckill.countDown(seckillId, nowTime, startTime, endTime);
                }else{
                    console.log('result' + result);
                }
            })
        }
    }
}