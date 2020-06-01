<#include "/inc/layout.ftlh" />
<@layout "我的主页">
    <div class="fly-home fly-panel" style="background-image: url();">
        <img src="${user.avatar}" alt="${user.username}">
        <h1>
            ${user.username}
            <i class="iconfont icon-nan"></i>
            <!-- <i class="iconfont icon-nv"></i>  -->
            <i class="layui-badge fly-badge-vip">${user.vipLevel}</i>
            <!--
            <span style="color:#c00;">（管理员）</span>
            <span style="color:#5FB878;">（社区之光）</span>
            <span>（该号已被封）</span>
            -->
        </h1>

        <p class="fly-home-info">
            <i class="iconfont icon-shijian"></i><span>${user.created?string('yyyy-MM-dd')} 加入</span>
            <i class="iconfont icon-chengshi"></i><span>来自太阳系</span>
        </p>

        <p class="fly-home-sign">（${(user.sign)!'这个人好懒，什么都没留下！'}）</p>

    </div>

    <div class="layui-container">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md6 fly-home-jie">
                <div class="fly-panel">
                    <h3 class="fly-panel-title">${user.username} 最近的文章</h3>
                    <ul class="jie-row">
                        <#list posts as post>
                            <li>
                                <#if post.recommend>
                                    <span class="fly-jing">精</span>
                                </#if>
                                <a href="/post/${post.id}" class="jie-title">${post.title}</a>
                                <i>${post.created?string('yyyy-MM-dd')}</i>
                                <em class="layui-hide-xs">${post.viewCount}阅/${post.commentCount}答</em>
                            </li>
                        </#list>
                        <#if posts?size == 0>
                            <div class="fly-none" style="min-height: 50px; padding:30px 0; height:auto;">
                                <i style="font-size:14px;">没有发表任何文章</i>
                            </div>
                        </#if>
                    </ul>
                </div>
            </div>

<#--            TODO-->
            <div class="layui-col-md6 fly-home-da">
                <div class="fly-panel">
                    <h3 class="fly-panel-title">${user.username} 最近的评论</h3>
                    <ul class="home-jieda">
                        <#list comments as comment>
                            <li>
                                <p>
                                    <span>${comment.created?string('yyyy-MM-dd')}</span>
                                    在<a href="/post/${comment.postId}" target="_blank">${comment.postTitle}</a>中评论：
                                </p>
                                <div class="home-dacontent">
                                    ${comment.content}
                                </div>
                            </li>
                        </#list>
                        <#if comments?size == 0>
                            <div class="fly-none" style="min-height: 50px; padding:30px 0; height:auto;">
                                <div class="fly-none" style="min-height: 50px; padding:30px 0; height:auto;"><span>没有任何评论</span></div>

                            </div>
                        </#if>


                    </ul>
                </div>
            </div>
        </div>
    </div>
</@layout>