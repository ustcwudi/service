import * as React from 'react'
import AddCircleIcon from '@mui/icons-material/AddCircle'
import StringInput from './string'
import StringSelect from './string_select'
import DialogTitle from '@mui/material/DialogTitle'
import Dialog from '@mui/material/Dialog'
import IconButton from '@mui/material/IconButton'
import RemoveCircleIcon from '@mui/icons-material/RemoveCircle'
import DialogContent from '@mui/material/DialogContent'
import Box from '@mui/material/Box'
import TextField from '@mui/material/TextField'

interface Props {
  fullWidth: boolean
  label: string
  defaultValue?: string[]
  map?: { label: string; value: string }[]
  onChange: (value: string[]) => void
}

export default (props: Props) => {
  const [value, setValue] = React.useState<string[]>(props.defaultValue ? [...props.defaultValue] : [])

  const [focus, setFocus] = React.useState(false)

  const add = (index: number) => {
    switch (index) {
      case 0:
        setValue([''].concat(value))
        break
      case value.length - 1:
        setValue(value.concat(['']))
        break
      default:
        setValue(value.slice(0, index).concat('', value.slice(index, value.length)))
        break
    }
  }

  const remove = (index: number) => {
    value.splice(index, 1)
    setValue([...value])
  }

  React.useEffect(() => {
    if (value.length === 0) setValue([''])
  }, [value])

  return (
    <>
      <TextField fullWidth={props.fullWidth} label={props.label} InputProps={{ readOnly: true }} onPointerDown={() => setFocus(true)} value={value.join(' ')}></TextField>
      <Dialog
        open={focus}
        fullWidth={true}
        onClose={() => {
          setFocus(false)
          let result: string[] = []
          value.forEach((v) => {
            v = v.trim()
            if (v !== '') result.push(v)
          })
          setValue(result)
          props.onChange(result)
        }}
      >
        <DialogTitle>{props.label}</DialogTitle>
        <DialogContent>
          {value.map((i, index) => (
            <Box key={`${Math.random()}`} sx={{ mt: 2, display: 'flex' }}>
              {props.map ? (
                <StringSelect fullWidth={props.fullWidth} label={props.label} map={props.map} defaultValue={value[index]} onChange={(v) => (value[index] = v ? v : '')}></StringSelect>
              ) : (
                <StringInput fullWidth={props.fullWidth} label={props.label} defaultValue={value[index]} onChange={(v) => (value[index] = v)}></StringInput>
              )}
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
