<#include "/inc/layout.ftlh" />

<@layout "搜索 - ${q}">

    <#include "/inc/header-panel.ftlh" />

    <div class="layui-container">
        <div class="layui-row layui-col-space15">

            <div class="layui-col-md8">
                <div class="fly-panel">
                    <div class="fly-panel-title fly-filter">
                        <a>您正在搜索关键字 “ ${q} ” - 共有 <strong>${pageData.total}</strong> 条记录</a>
                    </div>
                    <ul class="fly-list">

                        <#list pageData.records as post>
                            <@plisting post></@plisting>
                        </#list>
                    </ul>
                    <@paging pageData></@paging>
                </div>
            </div>

            <#include "/inc/right.ftlh" />

        </div>
    </div>
</@layout>