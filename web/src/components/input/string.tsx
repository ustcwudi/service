import * as React from 'react'
import TextField from '@mui/material/TextField'
import InputLabel from '@mui/material/InputLabel'
import MenuItem from '@mui/material/MenuItem'
import FormControl from '@mui/material/FormControl'
import Select from '@mui/material/Select'

interface Props {
  fullWidth: boolean
  label: string
  defaultValue?: string
  map?: { label: string; value: string }[]
  onChange: (value: string) => void
}

export default (props: Props) => {
  const [defaultValue] = React.useState(props.map ? (props.map.findIndex((i) => i.value === props.defaultValue) > -1 ? props.defaultValue : '') : props.defaultValue)

  return props.map ? (
    <FormControl fullWidth={props.fullWidth}>
      <InputLabel>{props.label}</InputLabel>
      <Select defaultValue={defaultValue} label={props.label} onChange={(e) => props.onChange(e.target.value)}>
        {props.map.map((i) => (
          <MenuItem key={i.value} value={i.value}>
            {i.label}
          </MenuItem>
        ))}
      </Select>
    </FormControl>
  ) : (
    <TextField label={props.label} fullWidth={props.fullWidth} defaultValue={defaultValue} onChange={(e) => props.onChange(e.target.value)} variant="outlined" />
  )
}
