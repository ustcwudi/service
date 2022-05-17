import Table from '@/components/table/index'
import { tableColumns, queryColumns, formColumns } from './columns'

export default () => {
  return <Table tableColumns={tableColumns()} queryColumns={queryColumns()} formColumns={formColumns()} table="${c(model.name)}" link="<#list model.fields as field><#if field.link??>${c(field.name)},</#if></#list>"></Table>
}
