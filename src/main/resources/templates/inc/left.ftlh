<div class="layui-col-md8">

    <!--置顶-->
    <div class="fly-panel">
        <div class="fly-panel-title fly-filter">
            <a>置顶</a>
        </div>

        <ul class="fly-list">
            <@postsTop>
                <#list R.records as post>
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
        <@posts categoryId=currentCategoryId pn=pn size=5>
            <ul class="fly-list">
                <#list pageData.records as post>
                    <@plisting post></@plisting>
                </#list>
            </ul>

        </@posts>


        <div style="text-align: center">
            <div class="laypage-main">
                <a href="jie/index.html" class="laypage-next">更多求解</a>
            </div>
        </div>

    </div>
</div>