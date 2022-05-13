package edu.hubu.auto.model;

import java.util.*;

import com.fasterxml.jackson.annotation.*;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.hubu.base.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.EqualsAndHashCode;

@Data
@Document
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
@ApiModel("${model.description}")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ${model.name} extends Model {
    <#list model.fields as field>

    @ApiModelProperty("${field.description}")
    private ${jt(field.type)} ${c(field.name)};
    <#if field.link??>

    @Transient
    <#if field.type == "id">
    private ${field.link} ${c(field.name)}Data;
    <#else>
    private List<${field.link}> ${c(field.name)}Data;
    </#if>
    </#if>
    <#if field.nullable>

    @JsonIgnore
    @Transient
    private boolean assign${field.name};

    public void set${field.name}(${jt(field.type)} value) {
        this.${c(field.name)} = value;
        this.assign${field.name} = true;
    }
    </#if>
    </#list>
}