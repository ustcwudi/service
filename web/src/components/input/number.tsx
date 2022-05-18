import * as React from 'react'
import TextField from '@mui/material/TextField'
import Autocomplete from '@mui/material/Autocomplete'

interface Props {
  fullWidth: boolean
  label: string
  defaultValue?: number | null
  type: 'int' | 'float'
  map?: { label: string; value: number }[]
  onChange: (value: number | null) => void
}

export default (props: Props) => {
  const [defaultValue] = React.useState(props.map ? props.map.find((i) => i.value === props.defaultValue) : props.defaultValue)
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
    <Autocomplete
      defaultValue={defaultValue as { label: string; value: number } | undefined}
      options={props.map}
      isOptionEqualToValue={(option, value) => option.value === value.value}
      renderInput={(params) => <TextField {...params} fullWidth={props.fullWidth} label={props.label} />}
      onChange={(e, value) => props.onChange(value ? value.value : null)}
    />
  ) : (
    <TextField error={error} label={props.label} fullWidth={props.fullWidth} defaultValue={defaultValue} onChange={(e) => convert(e.target.value)} variant="outlined" />
  )
}
