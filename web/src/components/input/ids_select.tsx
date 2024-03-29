import * as React from 'react'
import AddCircleIcon from '@mui/icons-material/AddCircle'
import IdSelect from './id_select'
import DialogTitle from '@mui/material/DialogTitle'
import Dialog from '@mui/material/Dialog'
import IconButton from '@mui/material/IconButton'
import RemoveCircleIcon from '@mui/icons-material/RemoveCircle'
import DialogContent from '@mui/material/DialogContent'
import Box from '@mui/material/Box'
import TextField from '@mui/material/TextField'
import { Model } from '@/pages/admin'
import request from 'umi-request'

interface Props<T> {
  fullWidth: boolean
  table: string
  link?: string
  label: string
  defaultValue?: string[]
  option?: T[]
  onChange: (value: T[]) => void
}

export default <T extends Model & { name: string }>(props: Props<T>) => {
  const [value, setValue] = React.useState<(T | null)[]>([])

  const [focus, setFocus] = React.useState(false)

  const add = (index: number) => {
    switch (index) {
      case 0:
        setValue(([null] as (T | null)[]).concat(value))
        break
      case value.length - 1:
        setValue(value.concat([null]))
        break
      default:
        setValue(value.slice(0, index).concat(null, value.slice(index, value.length)))
        break
    }
  }

  const remove = (index: number) => {
    value.splice(index, 1)
    setValue([...value])
  }

  React.useEffect(() => {
    if (value.length === 0) setValue([null])
  }, [value])

  React.useEffect(() => {
    if (props.defaultValue && props.defaultValue.length > 0) {
      request.get(`/api/${props.table}?id=${props.defaultValue.join(',')}`, { useCache: true, ttl: 10 * 60 * 1000, headers: props.link ? { link: props.link } : {} }).then(function (response) {
        setValue(response.data)
      })
    }
  }, [])

  return (
    <>
      <TextField fullWidth={props.fullWidth} label={props.label} InputProps={{ readOnly: true }} onPointerDown={() => setFocus(true)} value={value.map((i) => i?.name).join(' ')}></TextField>
      <Dialog
        open={focus}
        fullWidth={true}
        onClose={() => {
          setFocus(false)
          let result: T[] = []
          value.forEach((v) => {
            if (v !== null) result.push(v)
          })
          setValue(result)
          props.onChange(result)
        }}
      >
        <DialogTitle>{props.label}</DialogTitle>
        <DialogContent>
          {value.map((i, index) => (
            <Box key={`${Math.random()}`} sx={{ mt: 2, display: 'flex' }}>
              <IdSelect
                fullWidth={props.fullWidth}
                table={props.table}
                link={props.link}
                label={props.label}
                defaultValue={value[index]?.id}
                onChange={(v) => (value[index] = v as T | null)}
              ></IdSelect>
              <Box sx={{ flex: 0, ml: 1 }}>
                <IconButton sx={{ mt: '11px' }} size="small" onClick={() => add(index)}>
                  <AddCircleIcon />
                </IconButton>
              </Box>
              <Box sx={{ flex: 0, mr: -1 }}>
                <IconButton sx={{ mt: '11px' }} size="small" onClick={() => remove(index)}>
                  <RemoveCircleIcon />
                </IconButton>
              </Box>
            </Box>
          ))}
        </DialogContent>
      </Dialog>
    </>
  )
}
