import * as React from 'react'
import TextField from '@mui/material/TextField'
import Box from '@mui/material/Box'
import NumberInput from './number'

interface Props {
  fullWidth: boolean
  label: string
  type: 'int' | 'float'
  defaultKey?: string
  defaultValue?: number | null
  onChange: (pair: { key: string; value: number | null }) => void
}

export default (props: Props) => {
  const [defaultKey] = React.useState(props.defaultKey)
  const [defaultValue] = React.useState(props.defaultValue)
  const [pair, setPair] = React.useState({ key: props.defaultKey ? props.defaultKey : '', value: props.defaultValue === null || props.defaultValue === undefined ? null : props.defaultValue })

  React.useEffect(() => {
    props.onChange(pair)
  }, [pair])

  return (
    <Box sx={{ display: 'flex', flex: 1 }}>
      <TextField sx={{ flex: 1 }} label={props.label + '键'} fullWidth={props.fullWidth} defaultValue={defaultKey} onChange={(e) => setPair({ ...pair, key: e.target.value })} variant="outlined" />
      <Box sx={{ flex: 0, lineHeight: '56px', pl: 1, pr: 1 }}> : </Box>
      <Box sx={{ flex: 1 }}>
        <NumberInput type={props.type} label={props.label + '值'} fullWidth={props.fullWidth} defaultValue={defaultValue} onChange={(v) => setPair({ ...pair, value: v })}></NumberInput>
      </Box>
    </Box>
  )
}
