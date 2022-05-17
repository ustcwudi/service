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
import Dialog from '@mui/material/Dialog'
import DialogActions from '@mui/material/DialogActions'
import DialogContent from '@mui/material/DialogContent'
import Grid from '@mui/material/Grid'
import DialogTitle from '@mui/material/DialogTitle'
import Button from '@mui/material/Button'
import Head from './head'
import { TableColumn, FormColumn, Model, QueryModel } from '../../pages/admin/index'
import request from 'umi-request'
import { useRequest } from 'ahooks'

interface Props<T, Q> {
  tableColumns: TableColumn<T>[]
  queryColumns: FormColumn<Q>[]
  formColumns: FormColumn<T>[]
  table: string
  link: string
}

export default <T extends Model, Q extends QueryModel>(props: Props<T, Q>) => {
  const [selected, setSelected] = React.useState<readonly string[]>([])
  const [pagination, setPagination] = React.useState({ page: 1, pageSize: 10 })
  const [candidate, setCandidate] = React.useState<T>({} as T)
  const [current, setCurrent] = React.useState<T | undefined>(undefined)
  const [query, setQuery] = React.useState<Q | undefined>(undefined)
  const [order, setOrder] = React.useState<{ key: keyof T; direction: 'asc' | 'desc' }>({ key: 'createTime', direction: 'asc' })

  const queryRequest = useRequest(
    () => request.post(`/api/${props.table}/query/${order.key}/${order.direction}/${pagination.page}/${pagination.pageSize}`, { data: {}, headers: { link: props.link } }),
    {
      refreshDeps: [pagination, order],
    },
  )

  const countRequest = useRequest(() => request.post(`/api/${props.table}/count`, { data: {} }))

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
      case 'unselectAll':
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
      case 'add':
        setCurrent({} as T)
        break
      case 'query':
        setQuery({} as Q)
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
            columns={props.tableColumns}
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
                  {props.tableColumns.map((c) => (
                    <TableCell align="left" key={c.key as string}>
                      <c.render value={row}></c.render>
                    </TableCell>
                  ))}
                  {/* 行按钮 */}
                  <TableCell align="right" sx={{ p: '0px 10px' }}>
                    <Stack direction="row-reverse" spacing={1}>
                      <IconButton>
                        <VisibilityIcon />
                      </IconButton>
                      <IconButton onClick={() => setCurrent(row)}>
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
              <TableCell colSpan={props.tableColumns.length + 2} padding="none">
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
      <Dialog open={current !== undefined} maxWidth="lg" onClose={() => setCurrent(undefined)}>
        <DialogTitle>{current?.id ? '修改' : '新增'}</DialogTitle>
        <DialogContent>
          <Grid container spacing={2} sx={{ mt: 0.5 }}>
            {props.formColumns.map((c) => (
              <Grid key={c.key as string} item xs={4}>
                <c.render value={candidate} defaultValue={current}></c.render>
              </Grid>
            ))}
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setCurrent(undefined)}>取消</Button>
          <Button
            onClick={() => {
              setCurrent(undefined)
              console.log(candidate)
              setCandidate({} as T)
            }}
          >
            提交
          </Button>
        </DialogActions>
      </Dialog>
      <Dialog open={query !== undefined} maxWidth="lg" onClose={() => setQuery(undefined)}>
        <DialogTitle>搜索</DialogTitle>
        <DialogContent>
          <Grid container spacing={2} sx={{ mt: 0.5 }}>
            {props.queryColumns.map((c) => (
              <Grid key={c.key as string} item xs={4}>
                <c.render value={{} as Q} defaultValue={query}></c.render>
              </Grid>
            ))}
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setQuery(undefined)}>取消</Button>
          <Button onClick={() => setQuery(undefined)}>搜索</Button>
        </DialogActions>
      </Dialog>
    </Paper>
  )
}
