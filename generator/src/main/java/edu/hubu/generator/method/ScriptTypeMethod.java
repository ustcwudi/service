package edu.hubu.generator.method;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;

import java.util.List;

public class ScriptTypeMethod implements TemplateMethodModelEx {

    @Override
    public Object exec(@SuppressWarnings("rawtypes") List arguments) {
        String str = ((SimpleScalar) arguments.get(0)).getAsString();
        switch (str) {
            case "bool":
                return "boolean";
            case "int":
            case "float":
                return "number";
            case "int[]":
            case "float[]":
                return "number[]";
            case "string":
            case "upload":
            case "id":
                return "string";
            case "string[]":
            case "upload[]":
            case "id[]":
                return "string[]";
            case "map[string]string":
                return "{ [key: string]: string }";
            case "map[string]int":
            case "map[string]float":
                return "{ [key: string]: number }";
            case "map[string]string[]":
                return "{ [key: string]: string[] }";
            case "map[string]int[]":
            case "map[string]float[]":
                return "{ [key: string]: number[] }";
            default:
                return "";
        }
    }
}
