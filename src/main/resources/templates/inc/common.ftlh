<#macro plisting post>
<li>
    <a href="/user/${post.authorId}" class="fly-avatar">
        <img src="${post.authorAvatar}" alt="${post.authorName}">
    </a>
    <h2>
        <a class="layui-badge">${post.categoryName}</a>
        <a href="/post/${post.id}">${post.title}</a>
    </h2>
    <div class="fly-list-info">
        <a href="/user/${post.authorId}" link>
            <cite>${post.authorName}</cite>

            <#if ((post.authorVip)!-1) gt 0>
                <i class="layui-badge fly-badge-vip">VIP${post.authorVip}</i>
            </#if>
        </a>
        <span>${post.created?string('yyyy-MM-dd')}</span>

        <span class="fly-list-nums">
            <i class="iconfont icon-pinglun1" title="回答"></i> ${post.commentCount}
        </span>
    </div>
    <div class="fly-list-badge">
        <#if (post.recommend)!false><span class="layui-badge layui-bg-red">精帖</span></#if>
        <#if ((post.level)!-1) gt 0><span class="layui-badge layui-bg-black">置顶</span> </#if>
    </div>
</li>
</#macro>


<#macro paging pageData>
<div style="text-align: center">
    <div id="laypage-main">

    </div>
    <script>
        layui.use('laypage', function () {
            var laypage = layui.laypage;

            laypage.render({
                elem: 'laypage-main',
                count: ${pageData.total},
                curr: ${pageData.current},
                limit: ${pageData.size},
                jump: function (obj, first) {
                    if(!first){
                        location.href = "?pn=" + obj.curr;
                    }
                }
            });
        });
    </script>
</div>
</#macro>