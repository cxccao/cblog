<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- [if IE]-->
    <meta http-equiv="x-ua-compatible" content="IE=edge, chrome=1"/>
    <!-- [endif]-->

    <title>错误</title>

    <link rel="stylesheet" href="/res/layui/css/layui.css">
    <link rel="stylesheet" href="/res/css/global.css">

    <script src="/res/layui/layui.js"></script>
    <script src="/res/js/jquery.min.js"></script>

</head>
<body>
    <div class="fly-header layui-bg-black">
    <div class="layui-container">
        <a class="fly-logo" href="/">
            <img src="http://localhost:8080/res/images/logo.png" alt="layui">
        </a>
        <ul class="layui-nav fly-nav layui-hide-xs">
            <li class="layui-nav-item layui-this">
                <a href="/"><i class="iconfont icon-jiaoliu"></i>交流</a>
            </li>
            <li class="layui-nav-item">
                <a href=""><i class="iconfont icon-iconmingxinganli"></i>案例</a>
            </li>
            <li class="layui-nav-item">
                <a href="http://www.layui.com/" target="_blank"><i class="iconfont icon-ui"></i>框架</a>
            </li>
        </ul>
    </div>
</div>
    <div class="layui-container fly-marginTop">
        <div class="fly-panel">
            <div class="fly-none">
                <h2><i class="iconfont icon-tishilian"></i></h2>
                <p>${message}</p>
            </div>
        </div>
    </div>
</body>

