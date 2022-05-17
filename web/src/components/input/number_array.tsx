import * as React from 'react'
import AddCircleIcon from '@mui/icons-material/AddCircle'
import NumberInput from './number'
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
  type: 'int[]' | 'float[]'
  defaultValue?: number[]
  map?: { label: string; value: number }[]
  onChange: (value: number[]) => void
}

export default (props: Props) => {
  const [value, setValue] = React.useState<(number | null)[]>(props.defaultValue ? [...props.defaultValue] : [])

  const [focus, setFocus] = React.useState(false)

  const add = (index: number) => {
    switch (index) {
      case 0:
        setValue(([null] as (number | null)[]).concat(value))
        break
      case value.length - 1:
        setValue(value.concat([null]))
        break
      default:
        setValue(value.slice(0, index).concat([null], value.slice(index, value.length)))
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

  return (
    <>
      <TextField fullWidth={props.fullWidth} label={props.label} InputProps={{ readOnly: true }} onPointerDown={() => setFocus(true)} value={value.join(' ')}></TextField>
      <Dialog
        open={focus}
        fullWidth={true}
        onClose={() => {
          setFocus(false)
          let result: number[] = []
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
              <NumberInput
                type={props.type === 'int[]' ? 'int' : 'float'}
                fullWidth={props.fullWidth}
                label={props.label}
                map={props.map}
                defaultValue={value[index]}
                onChange={(v) => (value[index] = v)}
              ></NumberInput>
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
