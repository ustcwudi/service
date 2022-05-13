package edu.hubu.generator.method;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnderScoreCaseMethod implements TemplateMethodModelEx {

    public String exec(String value) {
        Matcher matcher = Pattern.compile("[A-Z]").matcher(value);
        StringBuilder buffer = new StringBuilder();
        boolean first = true;
        while (matcher.find()) {
            if (first) {
                matcher.appendReplacement(buffer, matcher.group(0).toLowerCase());
                first = false;
            } else {
                matcher.appendReplacement(buffer, "_" + matcher.group(0).toLowerCase());
            }
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    @Override
    public Object exec(@SuppressWarnings("rawtypes") List arguments) {
        return exec(((SimpleScalar) arguments.get(0)).getAsString());
    }
}
