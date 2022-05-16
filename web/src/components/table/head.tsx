import TableSortLabel from '@mui/material/TableSortLabel'
import TableHead from '@mui/material/TableHead'
import TableRow from '@mui/material/TableRow'
import TableCell from '@mui/material/TableCell'
import Checkbox from '@mui/material/Checkbox'
import { Model, TableColumn } from '@/pages/admin'

interface Props<T> {
  currentSelected: number
  currentSize: number
  onCommand: (type: string, parameter?: any) => void
  order: { key: keyof T; direction: 'asc' | 'desc' }
  columns: TableColumn<T>[]
}

export default <T extends Model>(props: Props<T>) => {
  const { onCommand, order, currentSelected, currentSize, columns } = props

  return (
    <TableHead>
      <TableRow>
        <TableCell padding="checkbox">
          <Checkbox
            color="primary"
            indeterminate={currentSelected > 0 && currentSelected < currentSize}
            checked={currentSelected > 0 && currentSelected === currentSize}
            onChange={(e) => onCommand('selectAll', e.target.checked)}
          />
        </TableCell>
        {columns.map((column) => (
          <TableCell key={column.key as string} align="right" padding="normal" sortDirection={order.key === column.key ? order.direction : false}>
            <TableSortLabel active={order.key === column.key} direction={order.key === column.key ? order.direction : 'asc'} onClick={() => onCommand('sort', column.key)}>
              {column.title}
            </TableSortLabel>
          </TableCell>
        ))}
        <TableCell></TableCell>
      </TableRow>
    </TableHead>
  )
}
