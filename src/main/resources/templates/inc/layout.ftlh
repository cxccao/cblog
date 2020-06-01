<#-- layout -->
<#macro layout title>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- [if IE]-->
    <meta http-equiv="x-ua-compatible" content="IE=edge, chrome=1"/>
    <!-- [endif]-->

    <title>${title?default('cblog')}</title>

    <link rel="stylesheet" href="/res/layui/css/layui.css">
    <link rel="stylesheet" href="/res/css/global.css">

    <script src="/res/layui/layui.js"></script>
    <script src="/res/js/jquery.min.js"></script>
    <script src="/res/js/sockjs.js"></script>
    <script src="/res/js/stomp.js"></script>

</head>
<body>
<#include "common.ftlh" />
<#include "header.ftlh"/>

<#nested>

<#include "footer.ftlh"/>
<script>
    layui.cache.user = {
        username: '${(profile.username)!"游客"}'
        ,uid: ${(profile.id)!-1}
        ,avatar: '${(profile.avatar)!"http://localhost:8080/res/images/avatar/0.jpg"}'
        ,experience: 83
        ,sex: '${(profile.sex)!"未知"}'
    };
    layui.config({
        version: "3.0.0"
        ,base: '/res/mods/'
    }).extend({
        fly: 'index'
    }).use('fly').use('jie').use('user');

</script>
<script>
    function showTips(count) {
        var msg = $('<a class="fly-nav-msg" href="javascript:;">' + count + '</a>');
        var elemUser = $('.fly-nav-user');
        elemUser.append(msg);
        msg.on('click', function () {
            location.href = '/user/message';
        });
        layer.tips('你有'+count+'条未读消息', msg, {
            tips: 3
            ,tipsMore: true
            ,fixed: true
        });
        msg.on('mouseenter', function () {
            layer.closeAll('tips');
        })
    }

    $(function () {
        var elemUser = $('.fly-nav-user');
        if (layui.cache.user.uid !== -1 && elemUser[0]) {
            var socket = new SockJS('/websocket');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                stompClient.subscribe("/user/" + ${(profile.id)!} + "/messCount", function (res) {
                    console.log(res);
                    showTips(res.body);
                })
            });
        }
    });
</script>
</body>
</html>
</#macro>