package com.cxc.cblog.common.templates;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.Map;

/**
 * Created by cxc Cotter on 2020/5/15.
 */
public abstract class TemplateDirective implements TemplateDirectiveModel {
    protected static final String RESULTS = "RESULTS";

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        try {
            execute(new DirectiveHandler(environment, map, templateModels, templateDirectiveBody));
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new TemplateException(e, environment);
        }
    }
    public abstract String getName();
    public abstract void execute(DirectiveHandler directiveHandler) throws Exception;
}
