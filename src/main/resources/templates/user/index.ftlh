<#include "/inc/layout.ftlh" />
<@layout "用户中心">
    <div class="layui-container fly-marginTop fly-user-main">
        <ul class="layui-nav layui-nav-tree layui-inline" lay-filter="user">
            <li class="layui-nav-item">
                <a href="/user/home">
                    <i class="layui-icon">&#xe609;</i>
                    我的主页
                </a>
            </li>
            <li class="layui-nav-item layui-this">
                <a href="/user/index">
                    <i class="layui-icon">&#xe612;</i>
                    用户中心
                </a>
            </li>
            <li class="layui-nav-item">
                <a href="/user/set">
                    <i class="layui-icon">&#xe620;</i>
                    基本设置
                </a>
            </li>
            <li class="layui-nav-item">
                <a href="/user/message">
                    <i class="layui-icon">&#xe611;</i>
                    我的消息
                </a>
            </li>
        </ul>

        <div class="site-tree-mobile layui-hide">
            <i class="layui-icon">&#xe602;</i>
        </div>
        <div class="site-mobile-shade"></div>

        <div class="site-tree-mobile layui-hide">
            <i class="layui-icon">&#xe602;</i>
        </div>
        <div class="site-mobile-shade"></div>


        <div class="fly-panel fly-panel-user" pad20>
            <!--
            <div class="fly-msg" style="margin-top: 15px;">
              您的邮箱尚未验证，这比较影响您的帐号安全，<a href="activate.html">立即去激活？</a>
            </div>
            -->
            <div class="layui-tab layui-tab-brief" lay-filter="user">
                <ul class="layui-tab-title" id="LAY_mine">
                    <li data-type="mine-jie" lay-id="index" class="layui-this">我发的帖（<span>${posts?size}</span>）</li>
                    <li data-type="collection" data-url="/collection/find/" lay-id="collection">我收藏的帖（<span>${collections.records?size}</span>）</li>
                </ul>
                <div class="layui-tab-content" style="padding: 20px 0;">
                    <div class="layui-tab-item layui-show">
                        <ul class="mine-view jie-row">
                            <#list posts as post>
                                <li>
                                    <a class="jie-title" href="/post/${post.id}" target="_blank">${post.title}</a>
                                    <i>${post.created?string('yyyy-MM-dd')}</i>
                                    <a class="mine-edit" href="/post/edit/${post.id}">编辑</a>
                                    <em>${post.viewCount}阅/${post.commentCount}答</em>
                                </li>
                            </#list>

                        </ul>
                        <div id="LAY_page"></div>
                    </div>

                    <div class="layui-tab-item">
                        <ul class="mine-view jie-row">
                            <#list collections.records as collection>
                                <li>
                                    <a class="jie-title" href="/post/${collection.postId}" target="_blank">${collection.title}</a>
                                    <i>收藏于${collection.created?string('yyyy-MM-dd')}</i>
                                </li>
                            </#list>
                        </ul>
                        <div id="LAY_page1"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        layui.cache.page="user";
    </script>
</@layout>