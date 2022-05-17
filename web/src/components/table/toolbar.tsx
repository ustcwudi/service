import Toolbar from '@mui/material/Toolbar'
import Typography from '@mui/material/Typography'
import IconButton from '@mui/material/IconButton'
import Tooltip from '@mui/material/Tooltip'
import DeleteIcon from '@mui/icons-material/Delete'
import FilterListIcon from '@mui/icons-material/FilterList'
import CancelIcon from '@mui/icons-material/Cancel'
import AddIcon from '@mui/icons-material/Add'
import { alpha } from '@mui/material/styles'

export default (props: { totalSelected: number; onCommand: (type: string, parameter?: any) => void }) => {
  const { totalSelected } = props

  return (
    <Toolbar
      sx={{
        ...(totalSelected > 0 && {
          bgcolor: (theme) => alpha(theme.palette.primary.main, theme.palette.action.activatedOpacity),
        }),
      }}
    >
      {totalSelected > 0 ? (
        <Typography sx={{ flex: '1 1 100%' }} color="inherit" variant="subtitle1" component="div">
          选中 {totalSelected} 项
        </Typography>
      ) : (
        <Typography sx={{ flex: '1 1 100%' }} variant="h6" component="div">
          表格
        </Typography>
      )}
      {totalSelected > 0 ? (
        <>
          <Tooltip title="取消选择">
            <IconButton onClick={() => props.onCommand('unselectAll')}>
              <CancelIcon />
            </IconButton>
          </Tooltip>
          <Tooltip title="删除">
            <IconButton>
              <DeleteIcon />
            </IconButton>
          </Tooltip>
        </>
      ) : (
        <>
          <Tooltip title="筛选">
            <IconButton onClick={() => props.onCommand('query')}>
              <FilterListIcon />
            </IconButton>
          </Tooltip>
          <Tooltip title="新增">
            <IconButton onClick={() => props.onCommand('add')}>
              <AddIcon />
            </IconButton>
          </Tooltip>
        </>
      )}
    </Toolbar>
  )
}
