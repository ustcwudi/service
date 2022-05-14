import Table from '@/components/table/index'
import { tableColumns } from './columns'

export default () => {
  return <Table columns={tableColumns()}></Table>
}
