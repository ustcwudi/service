import * as React from 'react'
import AddCircleIcon from '@mui/icons-material/AddCircle'
import StringPair from './string_pair'
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
  defaultValue?: Record<string, string>
  onChange: (value: Record<string, string>) => void
}

export default (props: Props) => {
  const [value, setValue] = React.useState<{ key?: string; value?: string }[]>(
    props.defaultValue
      ? Object.entries(props.defaultValue).map((i) => {
          return { key: i[0], value: i[1] }
        })
      : [],
  )

  const [focus, setFocus] = React.useState(false)

  const add = (index: number) => {
    switch (index) {
      case 0:
        setValue([{ key: '', value: '' } as { key?: string; value?: string }].concat(value))
        break
      case value.length - 1:
        setValue(value.concat([{ key: '', value: '' }]))
        break
      default:
        setValue(value.slice(0, index).concat({ key: '', value: '' }, value.slice(index, value.length)))
        break
    }
  }

  const remove = (index: number) => {
    value.splice(index, 1)
    setValue([...value])
  }

  React.useEffect(() => {
    if (value.length === 0) setValue([{ key: '', value: '' }])
  }, [value])

  return (
    <>
      <TextField
        fullWidth={props.fullWidth}
        label={props.label}
        InputProps={{ readOnly: true }}
        onPointerDown={() => setFocus(true)}
        value={value.map((i) => `${i.key}:${i.value ? i.value : ''}`).join(' ')}
      />
      <Dialog
        open={focus}
        fullWidth={true}
        onClose={() => {
          setFocus(false)
          let result: { key: string; value: string }[] = []
          value.forEach((p) => {
            if (p.key && p.value) result.push({ key: p.key, value: p.value })
          })
          setValue(result)
          props.onChange(Object.fromEntries(result.map((i) => [i.key, i.value])))
        }}
      >
        <DialogTitle>{props.label}</DialogTitle>
        <DialogContent>
          {value.map((i, index) => (
            <Box key={`${Math.random()}`} sx={{ mt: 2, display: 'flex' }}>
              <StringPair fullWidth={props.fullWidth} label={props.label} defaultKey={i.key} defaultValue={i.value} onChange={(v) => (value[index] = v)} />
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
