<template>
  <span class="h-7 text-lg font-bold">${model.description}列表</span>
  <toolbox
    :selected="selection.length > 0"
    @add="add"
    @search="search"
    @trash="trash"
    @recycler="recycler"
    @delete="remove"
    @restore="restore"
    @refresh="refresh"
  />
  <n-data-table
    ref="table"
    class="float-left my-3"
    remote
    :columns="columns()"
    :data="data"
    :pagination="pagination"
    :row-key="(row : ${model.name}) => row.id as string"
    :checked-row-keys="selection"
    @update:page="handlePageChange"
    @update-checked-row-keys="handleSelectionChange"
  />
  <n-drawer :show="querying != undefined || current !== undefined" :width="400" auto-focus>
    <n-drawer-content :title="querying ? '查询${model.description}' : current?.id ? '修改${model.description}' : '新增${model.description}'">
      <n-form>
        <renderForm />
      </n-form>
      <template #footer>
        <n-space>
          <n-button type="primary" ghost @click="submit">确认</n-button>
          <n-button class="ml-4" @click="cancel">取消</n-button>
        </n-space>
      </template>
    </n-drawer-content>
  </n-drawer>
</template>

<script lang="ts" setup>
import axios from 'axios'
import { NButton, NDataTable, NDrawer, NSpace, NDrawerContent, NForm, NIcon } from 'naive-ui'
import { h, reactive, ref, shallowReactive, watch } from 'vue'
import type { ${model.name}, ${model.name}Query } from '.'
import toolbox from '../../../components/toolbox.vue'
import { formColumns, queryColumns, tableColumns } from './columns'
import { EditOutlined, EyeOutlined } from '@vicons/antd'

const formValidation = reactive<Record<string, { status?: 'warning' | 'error'; feedback?: string }>>({})

const data = ref<${model.name}[]>([])

const pagination = shallowReactive({
  page: 1,
  pageCount: 1,
  pageSize: 10,
})

/**
 * 表格数据总数
 */
const total = ref(0)

/**
 * 回收站模式
 */
const inRecycler = ref(false)

/**
 * 切换模式
 */
const recycler = () => {
  inRecycler.value = !inRecycler.value
  selection.value = []
  fetch()
  count()
}

/**
 * 查询
 */
// eslint-disable-next-line @typescript-eslint/no-explicit-any
const query = ref<Record<string, any>>({})

/**
 * 当前查询
 */
const querying = ref<${model.name}Query | undefined>(undefined)

/**
 * 外链
 */
const link = ref('<#list model.fields as field><#if field.link??>${c(field.name)},</#if></#list>')

/**
 * 已选ID列表
 */
const selection = ref<string[]>([])

/**
 * 当前对象
 */
const current = ref<${model.name} | undefined>(undefined)

/**
 * 待编辑对象
 */
// eslint-disable-next-line @typescript-eslint/no-explicit-any
const model = ref<Record<string, any>>({})

/**
 * 表格列参数
 */
const columns = () => {
  return [
    ...tableColumns(),
    {
      title: '',
      key: 'actions',
      render(row: ${model.name}) {
        return h(
          NSpace,
          { justify: 'end', wrap: false },
          {
            default: () => [
              h(
                NButton,
                {
                  ghost: true,
                  size: 'small',
                  type: 'success',
                },
                { default: () => '详情', icon: () => h(NIcon, {}, { default: () => h(EyeOutlined) }) }
              ),
              h(
                NButton,
                {
                  ghost: true,
                  size: 'small',
                  type: 'warning',
                  onClick: () => {
                    model.value = {}
                    current.value = Object.assign(empty(), row)
                  },
                },
                { default: () => '修改', icon: () => h(NIcon, {}, { default: () => h(EditOutlined) }) }
              ),
            ],
          }
        )
      },
    },
  ]
}

/**
 * 分页请求表格数据
 */
const fetch = () => {
  query.value.trash = inRecycler.value
  axios.post('/api/${u(model.name)}/query/' + pagination.page + '/' + pagination.pageSize, query.value, { headers: { link: link.value } }).then((result) => {
    data.value = result.data
  })
}
fetch()

/**
 * 保存对象
 * @param model 待编辑对象
 */
const save = (model: ${model.name}, id?: string) => {
  if (id) {
    axios.put('/api/${u(model.name)}/id/' + id, model).then((result) => {
      if (result.data == 1) {
        axios.get('/api/${u(model.name)}/id/' + id, { headers: { link: link.value } }).then((result) => {
          data.value.splice(
            data.value.findIndex((i) => i.id == id),
            1,
            result.data
          )
        })
      }
    })
  } else {
    axios.post('/api/${u(model.name)}', model, { headers: { link: link.value } }).then((result) => {
      data.value.push(result.data)
    })
  }
}

/**
 * 请求表格数据总数
 */
const count = () => {
  query.value.trash = inRecycler.value
  axios.post('/api/${u(model.name)}/count', query.value).then((result) => {
    total.value = result.data
  })
}
count()

/**
 * 刷新
 */
const refresh = () => {
  fetch()
  count()
}

/**
 * 页数变化
 */
watch(total, (v) => {
  pagination.pageCount = Math.ceil(v / pagination.pageSize)
  if (pagination.pageCount < 1) pagination.pageCount = 1
  if (pagination.page > pagination.pageCount) {
    pagination.page = pagination.pageCount
    fetch()
  }
})

/**
 * 删除已选项
 */
const trash = () => {
  let ids = selection.value
  axios.put('/api/${u(model.name)}/trash', { id: ids }).then(() => {
    ids.forEach((id) => {
      let index = data.value.findIndex((i) => i.id === id)
      if (index > -1) {
        data.value.splice(index, 1)
        total.value--
      }
    })
    selection.value = []
  })
}

/**
 * 还原已选项
 */
const restore = () => {
  let ids = selection.value
  axios.put('/api/${u(model.name)}/restore', { id: ids, trash: true }).then(() => {
    ids.forEach((id) => {
      let index = data.value.findIndex((i) => i.id === id)
      if (index > -1) {
        data.value.splice(index, 1)
        total.value--
      }
    })
    selection.value = []
  })
}

/**
 * 彻底删除已选项
 */
const remove = () => {
  let ids = selection.value
  axios.delete('/api/${u(model.name)}', { data: { id: ids, trash: true } }).then(() => {
    ids.forEach((id) => {
      let index = data.value.findIndex((i) => i.id === id)
      if (index > -1) {
        data.value.splice(index, 1)
        total.value--
      }
    })
    selection.value = []
  })
}

/**
 * 翻页事件
 */
const handlePageChange = (page: number) => {
  pagination.page = page
  fetch()
}

/**
 * 勾选事件
 */
const handleSelectionChange = (keys: Array<string | number>) => {
  selection.value = keys as string[]
}

/**
 * 渲染表单
 */
const renderForm = () => {
  return h(
    'div',
    {},
    querying.value
      ? queryColumns().map((column) => column.render(query.value, querying.value, formValidation))
      : current.value === undefined
      ? []
      : formColumns().map((column) => column.render(model.value, current.value, formValidation))
  )
}

/**
 * 空对象
 */
let empty = () => {
  return {
    <#list model.fields as field>
    <#if field.type == "string">
    ${c(field.name)}: '',
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

/**
 * 新增对象
 */
const add = () => {
  model.value = empty()
  current.value = empty()
}

/**
 * 查询对象
 */
const search = () => {
  querying.value = {
    <#list model.fields as field><#if field.search??><#assign type = qt(field.type, field.search, "js")>
    <#if type == "string">
    ${c(field.name)}: <#if field.link??>null<#else>''</#if>,
    <#elseif type == "boolean">
    ${c(field.name)}: false,
    <#elseif type == "number">
    ${c(field.name)}: null,
    <#elseif type == "string[]" || type == "type[]">
    ${c(field.name)}: [],
    <#else>
    ${c(field.name)}: {},
    </#if>
    </#if></#list>
    ...query.value,
  }
}

/**
 * 提交表单
 */
const submit = () => {
  if (current.value) {
    save(model.value as ${model.name}, current.value?.id)
  }
  if (querying.value) {
    pagination.page = 1
    fetch()
    count()
  }
  querying.value = undefined
  current.value = undefined
}

/**
 * 取消表单
 */
const cancel = () => {
  querying.value ? console.log(query.value) : console.log(model.value)
  querying.value = undefined
  current.value = undefined
}
</script>
