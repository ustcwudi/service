// eslint-disable-next-line @typescript-eslint/no-unused-vars
import type { ${model.name}, ${model.name}Query } from './'
import type { TableColumn, FormColumn } from '../'
import TextField from '@mui/material/TextField'
import NumberInput from '@/components/input/number'
import StringInput from '@/components/input/string'
import StringSelect from '@/components/input/string_select'
import NumberArray from '@/components/input/number_array'
import StringArray from '@/components/input/string_array'
import StringMap from '@/components/input/string_map'
import NumberMap from '@/components/input/number_map'
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
      <#if field.type == "id[]">
      render: (props) => <>{props.value.${c(field.name)}Data?.map((i) => i.name).join('、')}</>,
      <#elseif field.type == "id">
      render: (props) => <>{props.value.${c(field.name)}Data?.name}</>,
      <#elseif field.type == "int[]" || field.type == "float[]" || field.type == "string[]">
      render: (props) => <>{props.value.${c(field.name)}?.join('、')}</>,
      <#elseif field.type == "map[string]int" || field.type == "map[string]float" || field.type == "map[string]string">
      render: (props) => <>{Object.entries(props.value.${c(field.name)}).map((i) => i[0] + ":" + i[1]).join('、')}</>,
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
<#if type == "boolean">
      render: (props) => <FormControlLabel labelPlacement="start" defaultChecked={props.defaultValue?.${c(field.name)}} control={<Switch onChange={(e) => (props.value.${c(field.name)} = e.target.checked)} />} label="${field.description}" />,
<#elseif type == "number">
      render: (props) => (
        <NumberInput
          type="<#if field.type?contains('int')>int<#else>float</#if>"
<#if field.map??>
          map={[
<#list field.map?keys as key>
            { label: '${key}', value: ${field.map["${key}"]} },
</#list>
          ]}
</#if>
          fullWidth
          label="${field.description}"
          onChange={(v) => (props.value.${c(field.name)} = v)}
          defaultValue={props.defaultValue?.${c(field.name)}}
        />
      ),
<#elseif type == "string">
              <#if field.type == "id" || field.type == "id[]">
      render: (props) => <IdSelect fullWidth table="${c(field.link)}" label="${field.description}" onChange={(v) => (props.value.${c(field.name)} = v && v.id ? v.id : null)} defaultValue={props.defaultValue?.${c(field.name)}}></IdSelect>,
              <#else>
      render: (props) => (
<#if field.map??>
        <StringSelect
          map={[
<#list field.map?keys as key>
            { label: '${key}', value: '${field.map["${key}"]}' },
</#list>
          ]}
<#else>
        <StringInput
</#if>
          fullWidth
          label="${field.description}"
          onChange={(v) => (props.value.${c(field.name)} = v)}
          defaultValue={props.defaultValue?.${c(field.name)}}
        />
      ),
              </#if>
<#elseif type == "number[]">
      render: (props) => (
        <NumberArray
          type="<#if field.type?contains('int')>int<#else>float</#if>"
<#if field.map??>
          map={[
<#list field.map?keys as key>
            { label: '${key}', value: ${field.map["${key}"]} },
</#list>
          ]}
</#if>
          fullWidth
          label="${field.description}"
          onChange={(v) => (props.value.${c(field.name)} = v)}
          defaultValue={props.defaultValue?.${c(field.name)}}
        />
      ),
<#elseif type == "string[]">
              <#if field.link??>
      render: (props) => <IdsSelect fullWidth table="${c(field.link)}" label="${field.description}" onChange={(v) => (props.value.${c(field.name)} = v.map((i) => i.id as string))} defaultValue={props.defaultValue?.${c(field.name)}}></IdsSelect>,
              <#else>
      render: (props) => (
        <StringArray
<#if field.map??>
          map={[
<#list field.map?keys as key>
            { label: '${key}', value: '${field.map["${key}"]}' },
</#list>
          ]}
</#if>
          fullWidth
          label="${field.description}"
          onChange={(v) => (props.value.${c(field.name)} = v)}
          defaultValue={props.defaultValue?.${c(field.name)}}
        />
      ),
              </#if>
<#elseif type == "{ [key: string]: string }">
      render: (props) => (
        <StringMap
          fullWidth
          label="${field.description}"
          onChange={(v) => (props.value.${c(field.name)} = v)}
          defaultValue={props.defaultValue?.${c(field.name)}}
        />
      ),
<#elseif type == "{ [key: string]: number }">
      render: (props) => (
        <NumberMap
          type="<#if field.type?contains('int')>int<#else>float</#if>"
          fullWidth
          label="${field.description}"
          onChange={(v) => (props.value.${c(field.name)} = v)}
          defaultValue={props.defaultValue?.${c(field.name)}}
        />
      ),
<#else>
      render: (props) => <TextField fullWidth label="${field.description}" onChange={(e) => (props.value.${c(field.name)} = e.target.value)} defaultValue={props.defaultValue?.${c(field.name)}}></TextField>,
</#if>
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
<#if field.type == "id">
      render: (props) => <IdSelect fullWidth table="${c(field.link)}" label="${field.description}" onChange={(v) => (props.value.${c(field.name)} = v && v.id ? v.id : null)} defaultValue={props.defaultValue?.${c(field.name)}}></IdSelect>,
<#elseif field.type == "id[]">
      render: (props) => <IdsSelect fullWidth table="${c(field.link)}" label="${field.description}" onChange={(v) => (props.value.${c(field.name)} = v.map((i) => i.id as string))} defaultValue={props.defaultValue?.${c(field.name)}}></IdsSelect>,
<#elseif field.type == "bool">
      render: (props) => <FormControlLabel labelPlacement="start" defaultChecked={props.defaultValue?.${c(field.name)}} control={<Switch onChange={(e) => (props.value.${c(field.name)} = e.target.checked)} />} label="${field.description}" />,
<#elseif field.type == "int" || field.type == "float">
      render: (props) => (
        <NumberInput
          type="${field.type}"
<#if field.map??>
          map={[
<#list field.map?keys as key>
            { label: '${key}', value: ${field.map["${key}"]} },
</#list>
          ]}
</#if>
          fullWidth
          label="${field.description}"
          onChange={(v) => (props.value.${c(field.name)} = v)}
          defaultValue={props.defaultValue?.${c(field.name)}}
        />
      ),
<#elseif field.type == "int[]" || field.type == "float[]">
      render: (props) => (
        <NumberArray
          type="<#if field.type?contains('int')>int<#else>float</#if>"
<#if field.map??>
          map={[
<#list field.map?keys as key>
            { label: '${key}', value: ${field.map["${key}"]} },
</#list>
          ]}
</#if>
          fullWidth
          label="${field.description}"
          onChange={(v) => (props.value.${c(field.name)} = v)}
          defaultValue={props.defaultValue?.${c(field.name)}}
        />
      ),
<#elseif field.type == "map[string]int" || field.type == "map[string]float">
      render: (props) => (
        <NumberMap
          type="<#if field.type?contains('int')>int<#else>float</#if>"
          fullWidth
          label="${field.description}"
          onChange={(v) => (props.value.${c(field.name)} = v)}
          defaultValue={props.defaultValue?.${c(field.name)}}
        />
      ),
<#elseif field.type == "string">
      render: (props) => (
<#if field.map??>
        <StringSelect
          map={[
<#list field.map?keys as key>
            { label: '${key}', value: '${field.map["${key}"]}' },
</#list>
          ]}
<#else>
        <StringInput
</#if>
          fullWidth
          label="${field.description}"
          onChange={(v) => (props.value.${c(field.name)} = v)}
          defaultValue={props.defaultValue?.${c(field.name)}}
        />
      ),
<#elseif field.type == "string[]">
      render: (props) => (
        <StringArray
<#if field.map??>
          map={[
<#list field.map?keys as key>
            { label: '${key}', value: '${field.map["${key}"]}' },
</#list>
          ]}
</#if>
          fullWidth
          label="${field.description}"
          onChange={(v) => (props.value.${c(field.name)} = v)}
          defaultValue={props.defaultValue?.${c(field.name)}}
        />
      ),
<#elseif field.type == "map[string]string">
      render: (props) => (
        <StringMap
          fullWidth
          label="${field.description}"
          onChange={(v) => (props.value.${c(field.name)} = v)}
          defaultValue={props.defaultValue?.${c(field.name)}}
        />
      ),
<#else>
      render: (props) => <TextField fullWidth label="${field.description}" onChange={(e) => (props.value.${c(field.name)} = e.target.value)} defaultValue={props.defaultValue?.${c(field.name)}}></TextField>,
</#if>
    },
</#if></#list>
  ]
}
