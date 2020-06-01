<div class="layui-col-md4">

    <div class="fly-panel">
        <h3 class="fly-panel-title">温馨通道</h3>
        <ul class="fly-panel-main fly-list-static">
            <li>
                <a href="https://www.github.com" target="_blank">GitHub</a>
            </li>
        </ul>
    </div>

    <dl class="fly-panel fly-list-one">
        <dt class="fly-panel-title">本周热议</dt>
        <@hots>
            <#list RESULTS as post>
                <dd>
                    <a href="/post/${post.id}">${post.title}</a>
                    <span><i class="iconfont icon-pinglun1"></i>${post.commentCount} </span>
                </dd>
            </#list>
        </@hots>

        <!-- 无数据时 -->
        <!--
        <div class="fly-none">没有相关数据</div>
        -->
    </dl>

    <div class="fly-panel">
        <div class="fly-panel-title">
            这里可作为广告区域
        </div>
        <div class="fly-panel-main">
            <a href="" target="_blank" class="fly-zanzhu" time-limit="2017.09.25-2099.01.01" style="background-color: #5FB878;">广告</a>
        </div>
    </div>

    <div class="fly-panel fly-link">
        <h3 class="fly-panel-title">友情链接</h3>
        <dl class="fly-panel-main">
            <dd><a href="https://www.layui.com/" target="_blank">Layui</a><dd>
            <dd><a href="https://spring.io/" target="_blank">Spring</a><dd>
            <dd><a href="https://www.elastic.co/cn/elasticsearch/" target="_blank">Elasticsearch</a><dd>
            <dd><a href="https://www.rabbitmq.com/" target="_blank">RabbitMQ</a><dd>

        </dl>
    </div>

</div>