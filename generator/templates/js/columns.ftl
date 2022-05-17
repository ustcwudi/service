// eslint-disable-next-line @typescript-eslint/no-unused-vars
import type { ${model.name}, ${model.name}Query } from './'
import type { TableColumn, FormColumn } from '../'
import TextField from '@mui/material/TextField'
import IdSelect from '@/components/input/id_select'
import IdsSelect from '@/components/input/ids_select'
import FormControlLabel from '@mui/material/FormControlLabel'
import Switch from '@mui/material/Switch'

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
      render: (props) => <TextField fullWidth label="${field.description}" onChange={(e) => (props.value.${c(field.name)} = e.target.value)} defaultValue={props.defaultValue?.${c(field.name)}}></TextField>,
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
<#if field.type == "id" && field.link??>
      render: (props) => <IdSelect fullWidth table="${c(field.link)}" label="${field.description}" onChange={(v) => (props.value.${c(field.name)} = v && v.id ? v.id : null)} defaultValue={props.defaultValue?.${c(field.name)}Data}></IdSelect>,
<#elseif field.type == "id[]" && field.link??>
      render: (props) => <IdsSelect fullWidth table="${c(field.link)}" label="${field.description}" onChange={(v) => (props.value.${c(field.name)} = v.map((i) => i.id as string))} defaultValue={props.defaultValue?.${c(field.name)}Data}></IdsSelect>,
<#elseif field.type == "bool">
      render: (props) => <FormControlLabel labelPlacement="start" defaultChecked={props.defaultValue?.${c(field.name)}} control={<Switch onChange={(e) => (props.value.${c(field.name)} = e.target.checked)} />} label="${field.description}" />,
<#else>
      render: (props) => <TextField fullWidth label="${field.description}" onChange={(e) => (props.value.${c(field.name)} = e.target.value)} defaultValue={props.defaultValue?.${c(field.name)}}></TextField>,
</#if>
    },
</#if></#list>
  ]
}
