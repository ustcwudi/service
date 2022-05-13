// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { NInput, NFormItem, NSwitch, NSelect, NDynamicInput, NInputNumber, NSpace, NTag, FormItemRule } from 'naive-ui'
import { TableColumn } from 'naive-ui/lib/data-table/src/interface'
import { h } from 'vue'
import { FormColumn } from '../'
import LinkSelector from '../../../components/link-selector.vue' // eslint-disable-line
import MapSelector from '../../../components/map-selector.vue' // eslint-disable-line
import type { ${model.name}, ${model.name}Query } from './'

export const tableColumns = function (): TableColumn<${model.name}>[] {
  return [
    {
      type: 'selection',
    },
<#list model.fields as field>
    {
      title: '${field.description}',
      key: '${c(field.name)}',
      <#if field.type == "id[]" && field.link??>
      render: (row) => {
        const tags = () => row.${c(field.name)}Data?.map((i) => h('span', {}, i.name))
        return h(NSpace, {}, tags)
      },
      <#elseif field.type == "id" && field.link??>
      render: (row) => {
        return row.${c(field.name)}Data?.name
      },
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
      render: (value, defaultValue) => {
        return h(
          NFormItem,
          { path: '${c(field.name)}', label: '${field.description}' },
          {
            default: () =>
<#if type == "boolean">
              h(NSwitch, {
                defaultValue: defaultValue?.${c(field.name)},
                'on-update:value': (v: boolean) => (value.${c(field.name)} = v),
              }),
<#elseif type == "string" || type == "number">
              <#if field.map??>
              h(NSelect, {
                options: [
                  <#list field.map?keys as key>
                  <#if type == "number">
                  { label: '${key}', value: ${field.map["${key}"]} },
                  <#else>
                  { label: '${key}', value: '${field.map["${key}"]}' },
                  </#if>
                  </#list>
                ],
                clearable: true,
                defaultValue: defaultValue?.${c(field.name)},
                'on-update:value': (v: <#if type == "number">number<#else>string</#if>) => (value.${c(field.name)} = v),
              }),
              <#elseif field.type == "id" && field.link??>
              h(LinkSelector, {
                table: '${u(field.link)}',
                link: '',
                defaultValue: defaultValue?.${c(field.name)},
                'on-update:value': (v: string) => (value.${c(field.name)} = v),
              }),
              <#else>
              h(<#if type == "number">NInputNumber<#else>NInput</#if>, {
                defaultValue: defaultValue?.${c(field.name)},
                'on-update:value': (v: <#if type == "number">number<#else>string</#if>) => (value.${c(field.name)} = v),
              }),
              </#if>
<#elseif type == "string[]" || type == "number[]">
              <#if field.map??>
              h(
                NDynamicInput,
                {
                  defaultValue: defaultValue?.${c(field.name)},
                  'on-update:value': (v: <#if type == "number[]">number<#else>string</#if>[]) => (value.${c(field.name)} = v),
                },
                {
                  default: (props: { value: string; index: number }) =>
                    h(MapSelector, {
                      options: [
                        <#list field.map?keys as key>
                        <#if type == "number[]">
                        { label: '${key}', value: ${field.map["${key}"]} },
                        <#else>
                        { label: '${key}', value: '${field.map["${key}"]}' },
                        </#if>
                        </#list>
                      ],
                      defaultValue: props.value,
                      'on-update:value': (v: <#if type == "number[]">number<#else>string</#if>) => {
                        if (!value.${c(field.name)}) value.${c(field.name)} = defaultValue?.${c(field.name)} ? defaultValue.${c(field.name)} : []
                        value.${c(field.name)}[props.index] = v
                      },
                    }),
                }
              ),
              h(LinkSelector, {
                table: '${u(field.link)}',
                link: '',
                defaultValue: defaultValue?.${c(field.name)},
                'on-update:value': (v: string) => (value.${c(field.name)} = v),
              }),
              <#elseif field.link??>
              h(
                NDynamicInput,
                {
                  defaultValue: defaultValue?.${c(field.name)},
                  'on-update:value': (v: string[]) => (value.${c(field.name)} = v),
                },
                {
                  default: (props: { value: string; index: number }) =>
                    h(LinkSelector, {
                      table: '${u(field.link)}',
                      link: '',
                      defaultValue: props.value,
                      'on-update:value': (v: string) => {
                        if (!value.${c(field.name)}) value.${c(field.name)} = defaultValue?.${c(field.name)} ? defaultValue.${c(field.name)} : []
                        value.${c(field.name)}[props.index] = v
                      },
                    }),
                }
              ),
              <#else>
              h(NDynamicInput, {
                defaultValue: defaultValue?.${c(field.name)},
                'on-update:value': (v: Array<<#if field.type == "int[]" || field.type == "float[]">number<#else>string</#if>>) => (value.${c(field.name)} = v),
              }),
              </#if>
<#elseif type == "{ [key: string]: string }" ||  type == "{ [key: string]: number }">
              h(NDynamicInput, {
                preset: 'pair',
                defaultValue: defaultValue
                  ? Object.entries(defaultValue?.${c(field.name)} as Record<string, unknown>).map((i) => {
                      return { key: i[0], value: i[1] }
                    })
                  : [],
                <#if field.type == "map[string]number">
                'on-update:value': (v: [{ key: string; value: string }]) =>
                  (value.${c(field.name)} = Object.fromEntries(v.map((i) => [i.key, parseFloat(i.value)]))),
                <#else>
                'on-update:value': (v: [{ key: string; value: string }]) => (value.${c(field.name)} = Object.fromEntries(v.map((i) => [i.key, i.value]))),
                </#if>
              }),
</#if>
          }
        )
      },
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
      render: (value, defaultValue, validation) => {
        return h(
          NFormItem,
          { path: '${c(field.name)}', label: '${field.description}', validationStatus: validation?.${c(field.name)}?.status, feedback: validation?.${c(field.name)}?.feedback }, // eslint-disable-line prettier/prettier
          {
            default: () =>
<#if field.type == "id" && field.link??>
              h(LinkSelector, {
                table: '${u(field.link)}',
                link: '',
                defaultValue: defaultValue?.${c(field.name)},
                'on-update:value': (v: string) => {
                  <#if !field.nullable>
                  if (validation) v == null ? (validation.${c(field.name)} = { status: 'error', feedback: '请选择${field.description}' }) : (validation.${c(field.name)} = {})
                  </#if>
                  value.${c(field.name)} = v
                },
              }),
<#elseif field.type == "id[]" && field.link??>
              h(
                NDynamicInput,
                {
                  defaultValue: defaultValue?.${c(field.name)},
                  'on-update:value': (v: string[]) => (value.${c(field.name)} = v),
                },
                {
                  default: (props: { value: string; index: number }) =>
                    h(LinkSelector, {
                      table: '${u(field.link)}',
                      link: '',
                      defaultValue: props.value,
                      'on-update:value': (v: string) => {
                        if (!value.${c(field.name)}) value.${c(field.name)} = defaultValue?.${c(field.name)} ? defaultValue.${c(field.name)} : []
                        value.${c(field.name)}[props.index] = v
                      },
                    }),
                }
              ),
<#elseif field.type == "string" || field.type == "id" || field.type == "int" || field.type == "float">
              <#if field.map??>
              h(NSelect, {
                options: [
                  <#list field.map?keys as key>
                  <#if field.type == "int" || field.type == "float">
                  { label: '${key}', value: ${field.map["${key}"]} },
                  <#else>
                  { label: '${key}', value: '${field.map["${key}"]}' },
                  </#if>
                  </#list>
                ],
                clearable: true,
                defaultValue: defaultValue?.${c(field.name)},
                'on-update:value': (v: <#if field.type == "int" || field.type == "float">number<#else>string</#if>) => {
                  <#if !field.nullable>
                  if (validation) v == null ? (validation.${c(field.name)} = { status: 'error', feedback: '请选择${field.description}' }) : (validation.${c(field.name)} = {})
                  </#if>
                  value.${c(field.name)} = v
                },
              }),
              <#else>
              h(<#if field.type == "int" || field.type == "float">NInputNumber<#else>NInput</#if>, {
                defaultValue: defaultValue?.${c(field.name)},
                'on-update:value': (v: <#if field.type == "int" || field.type == "float">number<#else>string</#if>) => {
                  <#if field.type == "int" || field.type == "float">
                  <#if !field.nullable>
                  if (validation) v == null ? (validation.${c(field.name)} = { status: 'error', feedback: '请填写${field.description}' }) : (validation.${c(field.name)} = {})
                  </#if>
                  </#if>
                  value.${c(field.name)} = v
                },
              }),
              </#if>
<#elseif field.type == "string[]" || field.type == "id[]" || field.type == "int[]" || field.type == "float[]">
              <#if field.map??>
              h(
                NDynamicInput,
                {
                  defaultValue: defaultValue?.${c(field.name)},
                  'on-update:value': (v: <#if field.type == "int[]" || field.type == "float[]">number<#else>string</#if>[]) => (value.${c(field.name)} = v),
                },
                {
                  default: (props: { value: string; index: number }) =>
                    h(MapSelector, {
                      options: [
                        <#list field.map?keys as key>
                        <#if field.type == "int[]" || field.type == "float[]">
                        { label: '${key}', value: ${field.map["${key}"]} },
                        <#else>
                        { label: '${key}', value: '${field.map["${key}"]}' },
                        </#if>
                        </#list>
                      ],
                      defaultValue: props.value,
                      'on-update:value': (v: <#if field.type == "int[]" || field.type == "float[]">number<#else>string</#if>) => {
                        if (!value.${c(field.name)}) value.${c(field.name)} = defaultValue?.${c(field.name)} ? defaultValue.${c(field.name)} : []
                        value.${c(field.name)}[props.index] = v
                      },
                    }),
                }
              ),
              <#else>
              h(NDynamicInput, {
                defaultValue: defaultValue?.${c(field.name)},
                'on-update:value': (v: Array<<#if field.type == "int[]" || field.type == "float[]">number<#else>string</#if>>) => (value.${c(field.name)} = v),
              }),
              </#if>
<#elseif field.type == "bool">
              h(NSwitch, {
                defaultValue: defaultValue?.${c(field.name)},
                'on-update:value': (v: boolean) => (value.${c(field.name)} = v),
              }),
<#elseif field.type == "map[string]string" || field.type == "map[string]int" || field.type == "map[string]float">
              h(NDynamicInput, {
                preset: 'pair',
                defaultValue: defaultValue
                  ? Object.entries(defaultValue?.${c(field.name)} as Record<string, unknown>).map((i) => {
                      return { key: i[0], value: i[1] }
                    })
                  : [],
                <#if field.type == "map[string]int">
                'on-update:value': (v: [{ key: string; value: string }]) => (value.${c(field.name)} = Object.fromEntries(v.map((i) => [i.key, parseInt(i.value)]))),
                <#elseif field.type == "map[string]float">
                'on-update:value': (v: [{ key: string; value: string }]) =>
                  (value.${c(field.name)} = Object.fromEntries(v.map((i) => [i.key, parseFloat(i.value)]))),
                <#else>
                'on-update:value': (v: [{ key: string; value: string }]) => (value.${c(field.name)} = Object.fromEntries(v.map((i) => [i.key, i.value]))),
                </#if>
              }),
<#elseif field.type == "map[string]string[]" || field.type == "map[string]int[]" || field.type == "map[string]float[]">
              h(NDynamicInput, {
                preset: 'pair',
                defaultValue: defaultValue
                  ? Object.entries(defaultValue?.${c(field.name)} as Record<string, unknown>).map((i) => {
                      return { key: i[0], value: i[1] }
                    })
                  : [],
                'on-update:value': (v: [{ key: string; value: string }]) =>
                  <#if field.type == "map[string]int[]">
                  (value.${c(field.name)} = Object.fromEntries(v.map((i) => [i.key, i.value.split(',').map((e) => parseInt(e))]))),
                  <#elseif field.type == "map[string]float[]">
                  (value.${c(field.name)} = Object.fromEntries(v.map((i) => [i.key, i.value.split(',').map((e) => parseFloat(e))]))),
                  <#else>
                  (value.${c(field.name)} = Object.fromEntries(v.map((i) => [i.key, i.value.split(',')]))),
                  </#if>
              }),
</#if>
          }
        )
      },
    },
</#if></#list>
  ]
}
