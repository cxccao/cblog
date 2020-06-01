<#include "/inc/layout.ftlh" />

<@layout "基本设置">
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
            <li class="layui-nav-item layui-this">
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
            <div class="layui-tab layui-tab-brief" lay-filter="user">
                <ul class="layui-tab-title" id="LAY_mine">
                    <li class="layui-this" lay-id="info">我的资料</li>
                    <li lay-id="avatar">头像</li>
                    <li lay-id="pass">密码</li>
                    <@shiro.hasRole name="admin">
                        <li lay-id="es">同步ES</li>
                    </@shiro.hasRole>
                </ul>
                <div class="layui-tab-content" style="padding: 20px 0;">
                    <div class="layui-form layui-form-pane layui-tab-item layui-show">
                        <form method="post">
                            <div class="layui-form-item">
                                <label for="L_email" class="layui-form-label">邮箱</label>
                                <div class="layui-input-inline">
                                    <input type="text" id="L_email" name="email" required lay-verify="email" autocomplete="off" value="${user.email}" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label for="L_username" class="layui-form-label">昵称</label>
                                <div class="layui-input-inline">
                                    <input type="text" id="L_username" name="username" required lay-verify="required" autocomplete="off" value="${user.username}" class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <div class="layui-input-inline">
                                        <input type="radio" name="sex" value="0" checked title="男">
                                        <input type="radio" name="sex" value="1" title="女">
                                    </div>
                                </div>
                            </div>
<#--                            <div class="layui-form-item">-->
<#--                                <label for="L_city" class="layui-form-label">城市</label>-->
<#--                                <div class="layui-input-inline">-->
<#--                                    <input type="text" id="L_city" name="city" autocomplete="off" value="" class="layui-input">-->
<#--                                </div>-->
<#--                            </div>-->
                            <div class="layui-form-item layui-form-text">
                                <label for="L_sign" class="layui-form-label">签名</label>
                                <div class="layui-input-block">
                                    <textarea placeholder="随便写些什么刷下存在感" id="L_sign"  name="sign" autocomplete="off" class="layui-textarea" style="height: 80px;">${(user.sign!"")}</textarea>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <button class="layui-btn" key="set-mine" lay-filter="*" lay-submit>确认修改</button>
                            </div>
                        </form>
                    </div>

                    <div class="layui-form layui-form-pane layui-tab-item">
                        <div class="layui-form-item">
                            <div class="avatar-add">
                                <p>建议尺寸168*168，支持jpg、png、gif，最大不能超过50KB</p>
                                <button type="button" class="layui-btn upload-img">
                                    <i class="layui-icon">&#xe67c;</i>上传头像
                                </button>
                                <input class="layui-upload-file" type="file" name="file">
                                <img src="<@shiro.principal property="avatar"/>">
                                <span class="loading"></span>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form layui-form-pane layui-tab-item">
                        <form action="/user/repass" method="post">
                            <div class="layui-form-item">
                                <label for="L_nowpass" class="layui-form-label">当前密码</label>
                                <div class="layui-input-inline">
                                    <input type="password" id="L_nowpass" name="nowpass" required lay-verify="required" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label for="L_pass" class="layui-form-label">新密码</label>
                                <div class="layui-input-inline">
                                    <input type="password" id="L_pass" name="pass" required lay-verify="required" autocomplete="off" class="layui-input">
                                </div>
                                <div class="layui-form-mid layui-word-aux">6到16个字符</div>
                            </div>
                            <div class="layui-form-item">
                                <label for="L_repass" class="layui-form-label">确认密码</label>
                                <div class="layui-input-inline">
                                    <input type="password" id="L_repass" name="repass" required lay-verify="required" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <button class="layui-btn" key="set-mine" lay-filter="*" lay-submit>确认修改</button>
                            </div>
                        </form>
                    </div>

                    <@shiro.hasRole name="admin">
                        <div class="layui-form layui-form-pane layui-tab-item">
                            <form action="/admin/initEsData" method="post">
                                <button class="layui-btn" key="set-mine" lay-filter="*" lay-submit alert="true">同步ES数据</button>
                            </form>
                        </div>
                    </@shiro.hasRole>
                </div>

            </div>
        </div>
    </div>
    <script>
        layui.cache.page = 'user';
    </script>
</@layout>