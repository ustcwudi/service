package edu.hubu.auto.dao;

import java.io.InputStream;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.hubu.auto.model.*;
import edu.hubu.auto.request.query.*;
import edu.hubu.base.dao.MongoDao;
import edu.hubu.base.web.RequestBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("unused")
@Repository("${model.name}MongoDao")
public class ${model.name}MongoDao extends MongoDao<${model.name}, ${model.name}Query> {

    @Autowired
    protected void setRequestBuilder(RequestBuilder<${model.name}, ${model.name}Query> builder) {
        requestBuilder = builder;
    }

    @Override
    public Collection<${model.name}> input(InputStream steam) {
        var matrix = filter(steam);
        var modelList = new ArrayList<${model.name}>();
        List<String> header = null;
        for (List<String> line : matrix) {
            if (header == null) {
                header = line;
                continue;
            }
            var model = new ${model.name}();
            for (int i = 0; i < header.size(); i++) {
                var span = line.get(i);
                switch (header.get(i)) {
                    case "id":
                        model.setId(span);
                        break;
                    <#list model.fields as field>
                    case "${u(field.name)}":
                    case "${field.description}":
                    <#if field.type == "id" ||  field.type == "string" ||  field.type == "upload">
                        model.set${field.name}(span);
                    <#elseif field.type == "id[]" ||  field.type == "string[]" ||  field.type == "upload[]">
                        model.set${field.name}(List.of(span.split(";")));
                    <#elseif field.type == "bool">
                        model.set${field.name}(Boolean.getBoolean(span));
                    <#elseif field.type == "int">
                        model.set${field.name}(Integer.parseInt(span));
                    <#elseif field.type == "float">
                        model.set${field.name}(Float.parseFloat(span));
                    <#elseif field.type == "int[]">
                        var array${field.name} = span.split(";");
                        var result${field.name} = new ArrayList<Integer>();
                        for (String element : array${field.name}) {
                            result${field.name}.add(Integer.parseInt(element));
                        }
                        model.set${field.name}(result${field.name});
                    <#elseif field.type == "float[]">
                        var array${field.name} = span.split(";");
                        var result${field.name} = new ArrayList<Float>();
                        for (String element : array${field.name}) {
                            result${field.name}.add(Float.parseFloat(element));
                        }
                        model.set${field.name}(result${field.name});
                    <#elseif field.type == "map[string]string">
                        Map<String, String> map${field.name} = new HashMap<>();
                        var pairs${field.name} = span.split(";");
                        for (var pair : pairs${field.name}) {
                            var array = pair.split(":");
                            map${field.name}.put(array[0], array[1]);
                        }
                        model.set${field.name}(map${field.name});
                    <#elseif field.type == "map[string]int">
                        Map<String, Integer> map${field.name} = new HashMap<>();
                        var pairs${field.name} = span.split(";");
                        for (var pair : pairs${field.name}) {
                            var array = pair.split(":");
                            map${field.name}.put(array[0], Integer.parseInt(array[1]));
                        }
                        model.set${field.name}(map${field.name});
                    <#elseif field.type == "map[string]float">
                        Map<String, Float> map${field.name} = new HashMap<>();
                        var pairs${field.name} = span.split(";");
                        for (var pair : pairs${field.name}) {
                            var array = pair.split(":");
                            map${field.name}.put(array[0], Float.parseFloat(array[1]));
                        }
                        model.set${field.name}(map${field.name});
                    </#if>
                        break;
                    </#list>
                }
            }
            modelList.add(model);
        }
        return add(modelList);
    }

    @Override
    public void link(String link, List<${model.name}> list) {
        switch (link) {
            <#list model.fields as field><#if field.link??>
            case "${u(field.name)}":
                list = link${field.name}(list);
                break;
            </#if></#list>
        }
    }
    <#list model.fields as field>
    <#if field.link??>
    <#if field.link != model.name>

    protected MongoDao<${field.link}, ${field.link}Query> ${c(field.name)}MongoDao;

    @Autowired
    public void set${field.name}MongoDao(MongoDao<${field.link}, ${field.link}Query> dao) {
        ${c(field.name)}MongoDao = dao;
    }
    </#if>

    protected List<${model.name}> link${field.name}(List<${model.name}> list) {
        Set<String> set = new HashSet<>();
        for (${model.name} item : list) {
            <#if field.type == "id">
            String id = item.get${field.name}();
            if (id != null)
                set.add(id);
            <#elseif field.type == "id[]">
            List<String> ids = item.get${field.name}();
            if (ids != null)
                set.addAll(ids);
            </#if>
        }
        if (set.size() > 0) {
            var array = <#if field.link != model.name>${c(field.name)}MongoDao.</#if>find(set, new String[0]);
            for (${model.name} item : list) {
                if (item.get${field.name}() != null) {
                    <#if field.type == "id">
                    item.set${field.name}Data(array.stream().filter(i -> i.getId().equals(item.get${field.name}())).findAny().orElse(null));
                    <#else>
                    item.set${field.name}Data(new ArrayList<>());
                    item.get${field.name}().forEach(id -> array.stream().filter(i -> i.getId().equals(id)).findAny().ifPresent(item.get${field.name}Data()::add));
                    </#if>
                }
            }
        }
        return list;
    }
    </#if>
    </#list>
}