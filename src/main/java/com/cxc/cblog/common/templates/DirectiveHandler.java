package com.cxc.cblog.common.templates;

import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by cxc Cotter on 2020/4/29.
 */
public class DirectiveHandler {
    private Environment env;
    private Map<String, TemplateModel> parameters;
    private TemplateModel[] loopVars;
    private TemplateDirectiveBody body;
    private Environment.Namespace namespace;

    public DirectiveHandler(Environment env, Map<String, TemplateModel> parameters, TemplateModel[] loopVars,
                            TemplateDirectiveBody body) {
        this.env=env;
        this.parameters=parameters;
        this.loopVars=loopVars;
        this.body=body;
        this.namespace=env.getCurrentNamespace();
    }

    public void render()throws IOException, TemplateException{
        Assert.notNull(body, "must have template directive body");
        body.render(env.getOut());
    }

    public Integer getInteger(String name)throws TemplateException {
        return TemplateModeUtils.convertInteger(getModel(name));
    }

    public Integer getInteger(String name, int defaultValue) throws TemplateException{
        Integer res = getInteger(name);
        return res == null ? defaultValue : res;
    }

    public Short getShort(String name)throws TemplateException {
        return TemplateModeUtils.convertShort(getModel(name));
    }

    public Long getLong(String name)throws TemplateException {
        return TemplateModeUtils.convertLong(getModel(name));
    }

    public Long getLong(String name, long defaultValue) throws TemplateException{
        Long res = getLong(name);
        return res == null ? defaultValue : res;
    }

    public String getString(String name)throws TemplateException {
        return TemplateModeUtils.convertString(getModel(name));
    }

    public String getString(String name, String defaultValue)throws TemplateException {
        String res = getString(name);
        return res == null ? defaultValue : res;
    }

    public String[] getStringArray(String name)throws TemplateException {
        return TemplateModeUtils.convertStringArray(getModel(name));
    }

    public Boolean getBoolean(String name)throws TemplateException {
        return TemplateModeUtils.converBoolean(getModel(name));
    }

    public Date getDate(String name)throws TemplateException {
        return TemplateModeUtils.converDate(getModel(name));
    }

    public Double getDouble(String name)throws TemplateException {
        return TemplateModeUtils.convertDouble(getModel(name));
    }


    private TemplateModel getModel(String name) {
        return parameters.get(name);
    }

    public TemplateModel wrap(Object object) throws TemplateModelException {
        return env.getObjectWrapper().wrap(object);
    }

    public DirectiveHandler put(String key, Object value) throws TemplateModelException {
        namespace.put(key, wrap(value));
        return this;
    }


}
