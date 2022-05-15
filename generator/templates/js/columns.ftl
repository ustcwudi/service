// eslint-disable-next-line @typescript-eslint/no-unused-vars
import type { ${model.name}, ${model.name}Query } from './'
import type { TableColumn, FormColumn } from '../'
import TextField from '@mui/material/TextField'

export const tableColumns = function (): TableColumn<${model.name}>[] {
  return [
<#list model.fields as field>
    {
      title: '${field.description}',
      key: '${c(field.name)}',
      <#if field.type == "id[]" && field.link??>
      render: (props) => <>{props.value.${c(field.name)}Data?.map((i) => i.name)}</>,
      <#elseif field.type == "id" && field.link??>
      render: (props) => <>{props.value.${c(field.name)}Data?.name}</>,
      <#else>
      render: (props) => <>{props.value.${c(field.name)}}</>,
      </#if>
    },
</#list>
  ]
}

export const queryColumns = function (): FormColumn<${model.name}Query>[] {
  return [
<#list model.fields as field><#if field.search??><#assign type = qt(field.type, field.search, "js")>
    {
      title: '${field.description}',
      key: '${c(field.name)}',
      render: (props) => <TextField></TextField>,
    },
</#if></#list>
  ]
}

export const formColumns = function (): FormColumn<${model.name}>[] {
  return [
<#list model.fields as field><#if field.type != "upload" && field.type != "upload[]">
    {
      title: '${field.description}',
      key: '${c(field.name)}',
      render: (props) => <TextField></TextField>,
    },
</#if></#list>
  ]
}
