    <#include "/inc/layout.ftlh">
<@layout "我的消息">
    <div class="layui-container fly-marginTop fly-user-main">
        <ul class="layui-nav layui-nav-tree layui-inline" lay-filter="user">
            <li class="layui-nav-item">
                <a href="/user/home">
                    <i class="layui-icon">&#xe609;</i>
                    我的主页
                </a>
            </li>
            <li class="layui-nav-item">
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
            <li class="layui-nav-item layui-this">
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
            <div class="layui-tab layui-tab-brief" lay-filter="user" id="LAY_msg" style="margin-top: 15px;">
                <button class="layui-btn layui-btn-danger" id="LAY_delallmsg">清空全部消息</button>
                <div  id="LAY_minemsg" style="margin-top: 10px;">
                    <!--<div class="fly-none">您暂时没有最新消息</div>-->
                    <ul class="mine-msg">
                        <#list pageData.records as page>
                            <li data-id="${page.id}">
                                <blockquote class="layui-elem-quote">
                                    <#if page.type == 0>
                                        系统消息：${page.content}
                                    </#if>
                                    <#if page.type == 1>
                                        <a href="/user/${page.fromUserId}" target="_blank"><cite>${page.fromUserName}</cite></a>评论了你的文章<a target="_blank" href="/post/${page.postId}"><cite>${page.postTitle}</cite></a>
                                    </#if>
                                    <#if page.type == 2>
                                        <a href="/user/${page.fromUserId}" target="_blank"><cite>${page.fromUserName}</cite></a>回复了你:<a target="_blank" href="/post/${page.postId}"><cite>${page.content}</cite></a>

                                    </#if>
                                </blockquote>
                                <p><span>${page.created?string('yyyy-MM-dd')}</span><a href="javascript:;" class="layui-btn layui-btn-small layui-btn-danger fly-delete">删除</a></p>
                            </li>
                        </#list>
                    </ul>
                    <@paging pageData></@paging>
                </div>
            </div>
        </div>
    </div>
    <script>
        layui.cache.page = 'user';
    </script>
</@layout>