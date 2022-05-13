package edu.hubu.auto.request.builder;

import edu.hubu.base.web.QueryType;
import edu.hubu.base.web.RequestBuilder;
import edu.hubu.auto.model.*;
import edu.hubu.auto.request.query.*;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component("${model.name}QueryBuilder")
public class ${model.name}Builder extends RequestBuilder<${model.name}, ${model.name}Query> {

    @Override
    public Query buildQuery(${model.name}Query query) {
        Query result = super.buildQuery(query);
        <#list model.fields as field><#assign type = qt(field.type, field.search)><#if type != "">
        if (query.get${field.name}() != null) {
            CriteriaDefinition criteria = builder.build${mt(field.type)}("${c(field.name)}", query.get${field.name}(), QueryType.${field.search});
            if (criteria != null)
                result.addCriteria(criteria);
        }
        <#if field.nullable>
        if (query.get${field.name}() == null && query.isAssign${field.name}()) {
            result.addCriteria(builder.buildNull("${c(field.name)}"));
        }
        </#if>
        </#if></#list>
        return result;
    }

    @Override
    public Update buildUpdate(${model.name} model) {
        Update update = super.buildUpdate(model);
        <#list model.fields as field>
        if (model.get${field.name}() != null)
            update.set("${c(field.name)}", model.get${field.name}());
        <#if field.nullable>
        if (model.get${field.name}() == null && model.isAssign${field.name}())
            update.unset("${c(field.name)}");
        </#if>
        </#list>
        return update;
    }

    @Override
    public void buildInsert(${model.name} model) {
        super.buildInsert(model);
    }
}