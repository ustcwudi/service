import * as React from 'react'
import TextField from '@mui/material/TextField'
import Autocomplete from '@mui/material/Autocomplete'

interface Props {
  fullWidth: boolean
  label: string
  defaultValue?: string | null
  map: { label: string; value: string }[]
  onChange: (value: string | null) => void
}

export default (props: Props) => {
  const [defaultValue] = React.useState(props.map.find((i) => i.value === props.defaultValue))

  return (
    <Autocomplete
      defaultValue={defaultValue}
      options={props.map}
      isOptionEqualToValue={(option, value) => option.value === value.value}
      renderInput={(params) => <TextField {...params} fullWidth={props.fullWidth} label={props.label} />}
      onChange={(e, value) => props.onChange(value ? value.value : null)}
    />
  )
}
