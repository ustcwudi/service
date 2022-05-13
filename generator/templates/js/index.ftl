import { Model, QueryModel } from '../'
<#list model.fields as field>
<#if field.link?? && field.link != model.name>
import { ${field.link} } from '../${u(field.link)}'
</#if>
</#list>

export declare class ${model.name} extends Model {
  <#list model.fields as field>
  // ${field.description}
  ${c(field.name)}: ${st(field.type)}<#if field.type == "id" | field.type == "upload" | field.type == "int" | field.type == "float"> | null<#elseif field.type == "string" && field.map??> | null</#if>
  <#if field.link??>
  ${c(field.name)}Data?: ${field.link}<#if field.type == "id[]">[]</#if>
  </#if>
  </#list>
}

export declare class ${model.name}Query extends QueryModel {
  <#list model.fields as field>
  <#if field.search??><#assign type = qt(field.type, field.search, "js")>
  // ${field.search} ${field.description}
  ${c(field.name)}: ${type}<#if type == "number"> | null<#elseif type == "string" && field.map??> | null<#elseif type == "string" && field.link??> | null</#if>
  </#if>
  </#list>
}
