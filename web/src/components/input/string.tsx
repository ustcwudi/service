import * as React from 'react'
import TextField from '@mui/material/TextField'

interface Props {
  fullWidth: boolean
  label: string
  defaultValue?: string
  onChange: (value: string) => void
}

export default (props: Props) => {
  const [defaultValue] = React.useState(props.defaultValue)

  return <TextField label={props.label} fullWidth={props.fullWidth} defaultValue={defaultValue} onChange={(e) => props.onChange(e.target.value)} variant="outlined" />
}
