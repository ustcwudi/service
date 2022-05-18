package edu.hubu.generator.method;

import edu.hubu.generator.define.SearchType;
import freemarker.ext.beans.StringModel;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;

import java.util.List;

public class QueryTypeMethod implements TemplateMethodModelEx {

    /**
     * 查询参数类型转换
     * 
     * @param type Java类型
     * @return JavaScript类型
     */
    private String toJavaScriptType(String type) {
        switch (type) {
            case "Boolean":
                return "boolean";
            case "Integer":
            case "Float":
                return "number";
            case "List<Integer>":
            case "List<Float>":
                return "number[]";
            case "String":
                return "string";
            case "List<String>":
                return "string[]";
            case "Map<String, String>":
                return "{ [key: string]: string }";
            case "Map<String, Integer>":
            case "Map<String, Float>":
                return "{ [key: string]: number }";
        }
        return "";
    }

    @Override
    /**
     * 根据字段类型和查询类型确定查询参数类型，于QueryBuilder方法数量对应
     */
    public Object exec(@SuppressWarnings("rawtypes") List arguments) {
        String str = ((SimpleScalar) arguments.get(0)).getAsString();
        if (arguments.size() > 2) {
            Object type = exec(List.of(arguments.get(0), arguments.get(1)));
            return toJavaScriptType(type.toString());
        }
        if (arguments.get(1) != null) {
            SearchType type = SearchType.valueOf(((StringModel) arguments.get(1)).getAsString());
            switch (type) {
                case equal: // 13个case
                    switch (str) {
                        case "bool":
                            return "Boolean";
                        case "int":
                        case "int[]":
                            return "Integer";
                        case "float":
                        case "float[]":
                            return "Float";
                        case "id":
                        case "id[]":
                        case "string":
                        case "string[]":
                            return "String";
                        case "map[string]string":
                            return "Map<String, String>";
                        case "map[string]int":
                            return "Map<String, Integer>";
                        case "map[string]float":
                            return "Map<String, Float>";
                        default:
                            return "";
                    }
                case like: // 4个case
                    switch (str) {
                        case "string":
                        case "string[]":
                            return "String";
                        case "map[string]string":
                            return "Map<String, String>";
                        default:
                            return "";
                    }
                case in: // 6个case
                    switch (str) {
                        case "int":
                        case "int[]":
                            return "List<Integer>";
                        case "float":
                        case "float[]":
                            return "List<Float>";
                        case "string":
                        case "id":
                        case "string[]":
                        case "id[]":
                            return "List<String>";
                        default:
                            return "";
                    }
                case between: // 4个case
                    switch (str) {
                        case "int":
                        case "int[]":
                            return "List<Integer>";
                        case "float":
                        case "float[]":
                            return "List<Float>";
                        default:
                            return "";
                    }
            }
        }
        return "";
    }
}
