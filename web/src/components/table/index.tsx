import * as React from 'react'
import TableFooter from '@mui/material/TableFooter'
import IconButton from '@mui/material/IconButton'
import Stack from '@mui/material/Stack'
import Table from '@mui/material/Table'
import TableBody from '@mui/material/TableBody'
import TableCell from '@mui/material/TableCell'
import TableContainer from '@mui/material/TableContainer'
import Pagination from '@mui/material/Pagination'
import EditIcon from '@mui/icons-material/Edit'
import VisibilityIcon from '@mui/icons-material/Visibility'
import TableRow from '@mui/material/TableRow'
import Paper from '@mui/material/Paper'
import Checkbox from '@mui/material/Checkbox'
import Toolbar from './toolbar'
import Head from './head'
import { TableColumn, Model } from '../../pages/admin/index'
import request from 'umi-request'
import { useRequest } from 'ahooks'

export default <T extends Model>(props: { columns: TableColumn<T>[] }) => {
  const [selected, setSelected] = React.useState<readonly string[]>([])
  const [pagination, setPagination] = React.useState({ page: 1, pageSize: 10 })
  const [order, setOrder] = React.useState<{ key: keyof T; direction: 'asc' | 'desc' }>({ key: 'createTime', direction: 'asc' })

  const queryRequest = useRequest(() => request.post(`/api/user/query/${order.key}/${order.direction}/${pagination.page}/${pagination.pageSize}`, { data: {}, headers: { link: 'school,role' } }), {
    refreshDeps: [pagination, order],
  })

  const countRequest = useRequest(() => request.post('/api/user/count', { data: {} }))

  const checkChange = (check: boolean, id?: string) => {
    if (id) {
      if (check) {
        setSelected(selected.concat(id))
      } else {
        const selectedIndex = selected.indexOf(id)
        setSelected(selected.slice(0, selectedIndex).concat(selected.slice(selectedIndex + 1)))
      }
    }
  }

  const isSelected = (id?: string) => (id ? selected.indexOf(id) !== -1 : false)

  const onCommand = (type: string, paramter?: any) => {
    switch (type) {
      case 'unselect':
        setSelected([])
        break
      case 'selectAll':
        let checked = paramter as boolean
        checked ? setSelected(queryRequest.data?.data?.map((row: T) => row.id)) : setSelected([])
        break
      case 'sort':
        let key = paramter as keyof T
        const isAsc = order.key === key && order.direction === 'asc'
        setOrder({ key: key, direction: isAsc ? 'desc' : 'asc' })
        break
    }
  }

  return (
    <Paper>
      <Toolbar totalSelected={selected.length} onCommand={onCommand} />
      <TableContainer>
        <Table sx={{ minWidth: 1200 }}>
          {/* 表头 */}
          <Head
            columns={props.columns}
            order={order}
            onCommand={onCommand}
            currentSelected={queryRequest.data?.data?.length ? queryRequest.data.data.map((row: T) => (isSelected(row.id) ? 1 : 0)).reduce((pre: number, cur: number) => pre + cur) : 0}
            currentSize={queryRequest.data?.data?.length ? queryRequest.data.data.length : 0}
          />
          {/* 表格 */}
          <TableBody>
            {queryRequest.data?.data?.map((row: T) => {
              const isItemSelected = isSelected(row.id)

              return (
                <TableRow hover role="checkbox" tabIndex={-1} key={row.id} selected={isItemSelected}>
                  {/* 复选框 */}
                  <TableCell padding="checkbox">
                    <Checkbox color="primary" checked={isItemSelected} onChange={(e) => checkChange(e.target.checked, row.id)} />
                  </TableCell>
                  {/* 数据 */}
                  {props.columns.map((c) => (
                    <TableCell align="right" key={c.key as string}>
                      <c.render value={row}></c.render>
                    </TableCell>
                  ))}
                  {/* 行按钮 */}
                  <TableCell align="right" sx={{ p: '0px 10px' }}>
                    <Stack direction="row-reverse" spacing={1}>
                      <IconButton>
                        <VisibilityIcon />
                      </IconButton>
                      <IconButton>
                        <EditIcon />
                      </IconButton>
                    </Stack>
                  </TableCell>
                </TableRow>
              )
            })}
          </TableBody>
          {/* 表尾 */}
          <TableFooter>
            <TableRow>
              <TableCell colSpan={props.columns.length + 2} padding="none">
                {/* 页码 */}
                <Pagination
                  showFirstButton
                  showLastButton
                  sx={{ float: 'right', m: 1.5 }}
                  count={countRequest?.data?.data ? (countRequest.data.data - (countRequest.data.data % pagination.pageSize)) / pagination.pageSize + 1 : 0}
                  shape="rounded"
                  page={pagination.page}
                  onChange={(e, page) => setPagination({ ...pagination, page: page })}
                />
              </TableCell>
            </TableRow>
          </TableFooter>
        </Table>
      </TableContainer>
    </Paper>
  )
}
