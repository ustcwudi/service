import * as React from 'react'
import Box from '@mui/material/Box'
import Toolbar from '@mui/material/Toolbar'
import Typography from '@mui/material/Typography'
import IconButton from '@mui/material/IconButton'
import Tooltip from '@mui/material/Tooltip'
import DeleteIcon from '@mui/icons-material/Delete'
import FilterListIcon from '@mui/icons-material/FilterList'
import FilterListOffIcon from '@mui/icons-material/FilterListOff'
import DeleteOutlineTwoToneIcon from '@mui/icons-material/DeleteOutlineTwoTone'
import DeleteTwoToneIcon from '@mui/icons-material/DeleteTwoTone'
import RestoreIcon from '@mui/icons-material/Restore'
import CancelIcon from '@mui/icons-material/Cancel'
import AddIcon from '@mui/icons-material/Add'
import InsertDriveFileOutlinedIcon from '@mui/icons-material/InsertDriveFileOutlined'
import Upload from '../input/upload'
import Xlsx from 'js-export-excel'
import { alpha } from '@mui/material/styles'

interface Props {
  title: string
  uploadUrl: string
  templateHeader: string[]
  totalSelected: number
  query: Record<string, any>
  onCommand: (type: string, parameter?: any) => void
  garbage: boolean
}
export default (props: Props) => {
  const { totalSelected } = props

  const toExcel = () => {
    let option: any = {}
    option.fileName = props.title + '模板'
    option.datas = [
      {
        sheetData: [],
        sheetName: '模板',
        sheetHeader: props.templateHeader,
      },
    ]
    new Xlsx(option).saveExcel()
  }

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
          {props.title + (props.garbage ? '回收站' : '')}
        </Typography>
      )}
      {totalSelected > 0 ? (
        <>
          <Tooltip title="取消选择">
            <IconButton onClick={() => props.onCommand('unselectAll')}>
              <CancelIcon />
            </IconButton>
          </Tooltip>
          {props.garbage && (
            <Tooltip title="还原">
              <IconButton onClick={() => props.onCommand('restore')}>
                <RestoreIcon />
              </IconButton>
            </Tooltip>
          )}
          <Tooltip title={props.garbage ? '彻底删除' : '删除'}>
            <IconButton onClick={() => (props.garbage ? props.onCommand('delete') : props.onCommand('trash'))}>
              <DeleteIcon />
            </IconButton>
          </Tooltip>
        </>
      ) : (
        <>
          {Object.keys(props.query).length > 0 && (
            <Tooltip title="取消筛选">
              <IconButton onClick={() => props.onCommand('unquery')}>
                <FilterListOffIcon />
              </IconButton>
            </Tooltip>
          )}
          <Tooltip title="筛选">
            <IconButton onClick={() => props.onCommand('query')}>
              <FilterListIcon />
            </IconButton>
          </Tooltip>
          {!props.garbage && (
            <>
              <Tooltip title="新增">
                <IconButton onClick={() => props.onCommand('add')}>
                  <AddIcon />
                </IconButton>
              </Tooltip>
              <Tooltip title="模板">
                <IconButton onClick={toExcel}>
                  <InsertDriveFileOutlinedIcon />
                </IconButton>
              </Tooltip>
              <Tooltip title="上传">
                <Box>
                  <Upload url={props.uploadUrl} onComplete={() => props.onCommand('refresh')}></Upload>
                </Box>
              </Tooltip>
            </>
          )}
          <Tooltip title={props.garbage ? '退出回收站' : '进入回收站'}>
            <IconButton onClick={() => props.onCommand('garbage')}>{props.garbage ? <DeleteTwoToneIcon /> : <DeleteOutlineTwoToneIcon />}</IconButton>
          </Tooltip>
        </>
      )}
    </Toolbar>
  )
}
