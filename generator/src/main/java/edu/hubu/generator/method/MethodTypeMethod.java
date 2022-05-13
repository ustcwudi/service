package edu.hubu.generator.method;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;

import java.util.List;

public class MethodTypeMethod implements TemplateMethodModelEx {

    @Override
    public Object exec(@SuppressWarnings("rawtypes") List arguments) {
        String str = ((SimpleScalar) arguments.get(0)).getAsString();
        switch (str) {
            case "bool":
                return "Bool";
            case "id":
            case "upload":
            case "string":
                return "String";
            case "id[]":
            case "upload[]":
            case "string[]":
                return "StringArray";
            case "int":
                return "Int";
            case "int[]":
                return "IntArray";
            case "float":
                return "Float";
            case "float[]":
                return "FloatArray";
            case "map[string]string":
                return "StringMap";
            case "map[string]int":
                return "IntMap";
            case "map[string]float":
                return "FloatMap";
            case "map[string]string[]":
                return "StringArrayMap";
            case "map[string]int[]":
                return "IntArrayMap";
            case "map[string]float[]":
                return "FloatArrayMap";
            default:
                return "";
        }
    }
}
