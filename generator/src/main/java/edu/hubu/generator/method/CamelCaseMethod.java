package edu.hubu.generator.method;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;

import java.util.List;

public class CamelCaseMethod implements TemplateMethodModelEx {

    @Override
    public Object exec(@SuppressWarnings("rawtypes") List arguments) {
        String str = ((SimpleScalar) arguments.get(0)).getAsString();
        if (Character.isLowerCase(str.charAt(0)))
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
        else
            return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
}
