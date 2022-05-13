package edu.hubu.auto.request.query;

import edu.hubu.base.web.QueryRequest;
import com.fasterxml.jackson.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.*;
import lombok.Data;
import lombok.Getter;
import lombok.EqualsAndHashCode;

import java.util.*;

@Data
@Document
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
@ApiModel("${model.description}查询")
public class ${model.name}Query extends QueryRequest {
    <#list model.fields as field><#assign type = qt(field.type, field.search)><#if type != "">

    @ApiModelProperty("${field.description}")
    private ${qt(field.type, field.search)} ${c(field.name)};
    <#if field.nullable>

    @JsonIgnore
    private boolean assign${field.name};

    public void set${field.name}(${qt(field.type, field.search)} value) {
        ${c(field.name)} = value;
        assign${field.name} = true;
    }
    </#if>
    </#if></#list>
}