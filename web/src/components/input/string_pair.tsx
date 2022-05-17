import * as React from 'react'
import TextField from '@mui/material/TextField'
import Box from '@mui/material/Box'
import StringInput from './string'

interface Props {
  fullWidth: boolean
  label: string
  defaultKey?: string
  defaultValue?: string
  onChange: (pair: { key?: string; value?: string }) => void
}

export default (props: Props) => {
  const [defaultKey] = React.useState(props.defaultKey)
  const [defaultValue] = React.useState(props.defaultValue)
  const [pair, setPair] = React.useState({ key: props.defaultKey, value: props.defaultValue })

  React.useEffect(() => {
    props.onChange(pair)
  }, [pair])

  return (
    <Box sx={{ display: 'flex', flex: 1 }}>
      <TextField sx={{ flex: 1 }} label={props.label + '键'} fullWidth={props.fullWidth} defaultValue={defaultKey} onChange={(e) => setPair({ ...pair, key: e.target.value })} variant="outlined" />
      <Box sx={{ flex: 0, lineHeight: '56px', pl: 1, pr: 1 }}> : </Box>
      <Box sx={{ flex: 1 }}>
        <StringInput label={props.label + '值'} fullWidth={props.fullWidth} defaultValue={defaultValue} onChange={(v) => setPair({ ...pair, value: v })}></StringInput>
      </Box>
    </Box>
  )
}
