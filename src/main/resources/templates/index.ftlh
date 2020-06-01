<#include "inc/layout.ftlh"/>

<@layout "首页">
<#include "inc/header-panel.ftlh">
    <div class="layui-container">
        <div class="layui-row layui-col-space15">

            <!--左侧栏-->
            <div class="layui-col-md8">

                <!--置顶-->
                <div class="fly-panel">
                    <div class="fly-panel-title fly-filter">
                        <a>置顶</a>
                    </div>

                    <ul class="fly-list">
                        <@postsTop>
                            <#list RESULTS.records as post>
                                <#if post.level gt 0>
                                    <@plisting post></@plisting>
                                </#if>
                            </#list>
                        </@postsTop>
                    </ul>

                </div>

                <!--内容-->
                <div class="fly-panel" style="margin-bottom: 0;">
                    <!--完结分类-->
                    <div class="fly-panel-title fly-filter">
                        <a href="" class="layui-this">文章</a>
                        <span class="fly-mid"></span>
                    </div>

                    <ul class="fly-list">
                        <#list pageData.records as post>
                            <@plisting post></@plisting>
                        </#list>
                    </ul>
                    <@paging pageData></@paging>
                </div>
            </div>
            <!--右侧栏-->
            <#include "inc/right.ftlh"/>
        </div>
    </div>
</@layout>