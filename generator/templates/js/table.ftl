import DataTable from '@/components/table/index'
import { ${model.name} } from '.'
import { tableColumns, queryColumns, formColumns } from './columns'

/**
 * 空对象
 */
let empty: () => ${model.name} = () => {
  return {
<#list model.fields as field>
<#if field.type == "string">
<#if field.map??>
    ${c(field.name)}: null,
<#else>
    ${c(field.name)}: '',
</#if>
<#elseif field.type == "bool">
    ${c(field.name)}: false,
<#elseif field.type == "upload">
    ${c(field.name)}: null,
<#elseif field.type == "int" || field.type == "float" || field.type == "id">
    ${c(field.name)}: null,
<#elseif field.type == "string[]" || field.type == "id[]" || field.type == "int[]" || field.type == "float[]" || field.type == "upload[]">
    ${c(field.name)}: [],
<#else>
    ${c(field.name)}: {},
</#if>
</#list>
  }
}

export default () => {
  return <DataTable title="${model.description}" emptyModel={empty} tableColumns={tableColumns()} queryColumns={queryColumns()} formColumns={formColumns()} table="${c(model.name)}" link="<#list model.fields as field><#if field.link??>${c(field.name)},</#if></#list>"></DataTable>
}
