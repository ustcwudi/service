package edu.hubu.generator.method;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;

import java.util.List;

public class JavaTypeMethod implements TemplateMethodModelEx {

    @Override
    public Object exec(@SuppressWarnings("rawtypes") List arguments) {
        String str = ((SimpleScalar) arguments.get(0)).getAsString();
        switch (str) {
            case "int":
                return "Integer";
            case "float":
                return "Float";
            case "int[]":
                return "List<Integer>";
            case "float[]":
                return "List<Float>";
            case "bool":
                return "Boolean";
            case "string":
            case "upload":
            case "id":
                return "String";
            case "string[]":
            case "upload[]":
            case "id[]":
                return "List<String>";
            case "map[string]string":
                return "Map<String, String>";
            case "map[string]int":
                return "Map<String, Integer>";
            case "map[string]float":
                return "Map<String, Float>";
            default:
                return "";
        }
    }
}
