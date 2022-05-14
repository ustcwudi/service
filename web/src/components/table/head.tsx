import TableSortLabel from '@mui/material/TableSortLabel'
import TableHead from '@mui/material/TableHead'
import { visuallyHidden } from '@mui/utils'
import TableRow from '@mui/material/TableRow'
import TableCell from '@mui/material/TableCell'
import Checkbox from '@mui/material/Checkbox'
import Box from '@mui/material/Box'
import { Model, TableColumn } from '@/pages/admin'

type Order = 'asc' | 'desc'

interface HeadProps<T> {
  numSelected: number
  onRequestSort: (event: React.MouseEvent<unknown>, property: keyof T) => void
  onSelectAllClick: (event: React.ChangeEvent<HTMLInputElement>) => void
  order: Order
  orderBy: keyof T
  rowCount: number
  columns: TableColumn<T>[]
}

export default <T extends Model>(props: HeadProps<T>) => {
  const { onSelectAllClick, order, orderBy, numSelected, rowCount, columns, onRequestSort } = props
  const createSortHandler = (property: keyof T) => (event: React.MouseEvent<unknown>) => {
    onRequestSort(event, property)
  }

  return (
    <TableHead>
      <TableRow>
        <TableCell padding="checkbox">
          <Checkbox color="primary" indeterminate={numSelected > 0 && numSelected < rowCount} checked={rowCount > 0 && numSelected === rowCount} onChange={onSelectAllClick} />
        </TableCell>
        {columns.map((column) => (
          <TableCell key={column.key as string} align="right" padding="normal" sortDirection={orderBy === column.key ? order : false}>
            <TableSortLabel active={orderBy === column.key} direction={orderBy === column.key ? order : 'asc'} onClick={createSortHandler(column.key)}>
              {column.title}
              {orderBy === column.key ? (
                <Box component="span" sx={visuallyHidden}>
                  {order === 'desc' ? 'sorted descending' : 'sorted ascending'}
                </Box>
              ) : null}
            </TableSortLabel>
          </TableCell>
        ))}
      </TableRow>
    </TableHead>
  )
}
