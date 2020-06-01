<#include "/inc/layout.ftlh"/>

<@layout "博客分类">
    <#include "/inc/header-panel.ftlh">
    <div class="layui-container">
        <div class="layui-row layui-col-space15">

            <!--左侧栏-->
            <div class="layui-col-md8">
                <!--内容-->
                <div class="fly-panel" style="margin-bottom: 0;">
                    <!--完结分类-->
                    <div class="fly-panel-title fly-filter">
                        <a href="" class="layui-this">文章</a>
                        <span class="fly-mid"></span>
                    </div>

                    <@posts categoryId=currentCategoryId pn=pn size=5>
                        <ul class="fly-list">
                            <#list RESULTS.records as post>
                                <@plisting post></@plisting>
                            </#list>
                        </ul>
                        <@paging RESULTS></@paging>
                    </@posts>


                </div>
            </div>
            <!--右侧栏-->
            <#include "/inc/right.ftlh"/>
        </div>
    </div>
</@layout>