import * as React from 'react'
import TextField from '@mui/material/TextField'
import InputLabel from '@mui/material/InputLabel'
import MenuItem from '@mui/material/MenuItem'
import FormControl from '@mui/material/FormControl'
import Select from '@mui/material/Select'

interface Props {
  fullWidth: boolean
  label: string
  defaultValue?: number | null
  type: 'int' | 'float'
  map?: { label: string; value: number }[]
  onChange: (value: number | null) => void
}

export default (props: Props) => {
  const [defaultValue] = React.useState(props.map ? (props.map.findIndex((i) => i.value === props.defaultValue) > -1 ? props.defaultValue : '') : props.defaultValue)
  const [error, setError] = React.useState(false)
  const convert = (value: string) => {
    let result = props.type === 'int' ? parseInt(value) : parseFloat(value)
    if (isNaN(result)) {
      setError(true)
      props.onChange(null)
    } else {
      setError(false)
      props.onChange(result)
    }
  }

  return props.map ? (
    <FormControl fullWidth={props.fullWidth}>
      <InputLabel>{props.label}</InputLabel>
      <Select defaultValue={defaultValue} label={props.label} onChange={(e) => props.onChange(e.target.value as number | null)}>
        {props.map.map((i) => (
          <MenuItem key={i.value} value={i.value}>
            {i.label}
          </MenuItem>
        ))}
      </Select>
    </FormControl>
  ) : (
    <TextField error={error} label={props.label} fullWidth={props.fullWidth} defaultValue={defaultValue} onChange={(e) => convert(e.target.value)} variant="outlined" />
  )
}
