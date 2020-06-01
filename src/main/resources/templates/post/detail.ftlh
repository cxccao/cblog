<#include "/inc/layout.ftlh"/>

<@layout "文章详情">
    <#include "/inc/header-panel.ftlh">
    <div class="layui-container">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md8 content detail">
                <div class="fly-panel detail-box">
                    <h1>${post.title}</h1>
                    <div class="fly-detail-info">
                        <!-- <span class="layui-badge">审核中</span> -->
                        <span class="layui-badge layui-bg-green fly-detail-column">${post.categoryName}</span>
                        <#if post.level gt 0><span class="layui-badge layui-bg-black">置顶</span></#if>
                        <#if post.recommend><span class="layui-badge layui-bg-red">精帖</span></#if>

                        <div class="fly-admin-box" data-id="${post.id}">
                            <#if post.userId == (profile.id)!-1>
                                <span class="layui-btn layui-btn-xs jie-admin" type="del">删除</span>
                            </#if>
                            <@shiro.hasRole name = "admin">
                                <span class="layui-btn layui-btn-xs jie-admin" type="del">管理员删除</span>

                                <#if post.level == 0>
                                    <span class="layui-btn layui-btn-xs jie-admin" type="set" field="stick" rank="1">置顶</span>
                                </#if>
                                <#if post.level gt 0>
                                    <span class="layui-btn layui-btn-xs jie-admin" type="set" field="stick" rank="0" style="background-color:#ccc;">取消置顶</span>
                                </#if>

                                <#if !post.recommend>
                                    <span class="layui-btn layui-btn-xs jie-admin" type="set" field="status" rank="1">加精</span>
                                </#if>
                                <#if post.recommend>
                                    <span class="layui-btn layui-btn-xs jie-admin" type="set" field="status" rank="0" style="background-color:#ccc;">取消加精</span>
                                </#if>
                            </@shiro.hasRole>
                        </div>
                        <span class="fly-list-nums">
                            <a href="#comment"><i class="iconfont" title="回答">&#xe60c;</i> ${post.commentCount}</a>
                            <i class="iconfont" title="人气">&#xe60b;</i> ${post.viewCount}
                        </span>
                    </div>
                    <div class="detail-about">
                        <a class="fly-avatar" href="/user/${post.authorId}">
                            <img src="${post.authorAvatar}" alt="${post.authorName}">
                        </a>
                        <div class="fly-detail-user">
                            <a href="/user/${post.authorId}" class="fly-link">
                                <cite>${post.authorName}</cite>
                                <#if post.authorVip gt 0>
                                    <i class="layui-badge fly-badge-vip">VIP${post.authorVip}</i>
                                </#if>
                            </a>
                            <span>${post.created?string('yyyy-MM-dd')}</span>
                        </div>
                        <div class="detail-hits" id="LAY_jieAdmin" data-id="${post.id}">
                            <#if post.userId == (profile.id)!-1>
                                <span class="layui-btn layui-btn-xs jie-admin" type="edit"><a href="/post/edit?id=${post.id}">编辑此贴</a></span>
                            </#if>
                        </div>
                    </div>
                    <div class="detail-body photos">
                        ${post.content}
                    </div>
                </div>

                <div class="fly-panel detail-box" id="flyReply">
                    <fieldset class="layui-elem-field layui-field-title" style="text-align: center;">
                        <legend>回帖</legend>
                    </fieldset>

                    <ul class="jieda" id="jieda">
                        <#list pageData.records as comment>
                            <li data-id="${comment.id}" class="jieda-daan">
                                <a name="item-1111111111"></a>
                                <div class="detail-about detail-about-reply">
                                    <a class="fly-avatar" href="/user/${comment.authorId}">
                                        <img src="${comment.authorAvatar}" alt="${comment.authorName}">
                                    </a>
                                    <div class="fly-detail-user">
                                        <a href="/user/${comment.authorId}" class="fly-link">
                                            <cite>${comment.authorName}</cite>
                                            <#if comment.authorVip gt 0>
                                                <i class="layui-badge fly-badge-vip">VIP${comment.authorVip}</i>
                                            </#if>
                                        </a>
                                        <#if comment.authorId == post.authorId>
                                            <span>(楼主)</span>
                                        </#if>

                                        <!--
                                        <span style="color:#5FB878">(管理员)</span>
                                        <span style="color:#FF9E3F">（社区之光）</span>
                                        <span style="color:#999">（该号已被封）</span>
                                        -->
                                    </div>

                                    <div class="detail-hits">
                                        <span>${comment.created?string('yyyy-MM-dd')}</span>
                                    </div>
                                </div>

                                <div class="detail-body jieda-body photos">
                                    ${comment.content}
                                </div>
                                <div class="jieda-reply">
                                    <span class="jieda-zan zanok" type="zan">
                                        <i class="iconfont icon-zan"></i><em>${comment.voteUp}</em>
                                    </span>
                                    <span type="reply">
                                        <i class="iconfont icon-svgmoban53"></i>回复
                                    </span>
                                    <#if comment.authorId == (profile.id)!-1>
                                        <div class="jieda-admin">
                                            <span type="del">删除</span>
                                            <!-- <span class="jieda-accept" type="accept">采纳</span> -->
                                        </div>
                                    </#if>


                                </div>
                            </li>
                        </#list>

                        <!-- 无数据时 -->
                        <!-- <li class="fly-none">消灭零回复</li> -->
                    </ul>

                    <@paging pageData></@paging>

                    <div class="layui-form layui-form-pane">
                        <form action="/post/comment" method="post">
                            <div class="layui-form-item layui-form-text">
                                <a name="comment"></a>
                                <div class="layui-input-block">
                                    <textarea id="L_content" name="content" required lay-verify="required" placeholder="请输入内容"  class="layui-textarea fly-editor" style="height: 150px;"></textarea>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <input type="hidden" name="id" value="${post.id}">
                                <button class="layui-btn" lay-filter="*" lay-submit>提交回复</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <#include "/inc/right.ftlh" />
        </div>
    </div>
    <script>
        layui.cache.page = 'jie';

        $(function () {
            layui.use(['fly', 'face'], function() {
                var fly = layui.fly;
                $('.detail-body').each(function(){
                    var othis = $(this), html = othis.html();
                    othis.html(fly.content(html));
                });
            });
        });
    </script>

</@layout>