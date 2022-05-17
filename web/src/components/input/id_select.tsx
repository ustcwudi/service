import * as React from 'react'
import Autocomplete from '@mui/material/Autocomplete'
import TextField from '@mui/material/TextField'
import request from 'umi-request'
import { useRequest } from 'ahooks'
import { Model } from '@/pages/admin'

interface Props<T> {
  fullWidth: boolean
  table: string
  link?: string
  label: string
  defaultValue?: T | null
  onChange: (value: T | null) => void
}

export default <T extends Model & { name: string }>(props: Props<T>) => {
  const [defaultValue] = React.useState(props.defaultValue)
  const [options, setOptions] = React.useState<T[]>(props.defaultValue ? [props.defaultValue] : [])
  const queryRequest = useRequest((keyword: string) => request.post(`/api/${props.table}/query/1/10`, { data: { name: keyword }, headers: props.link ? { link: props.link } : {} }), {
    manual: true,
    onSuccess: (data) => {
      if (data.data?.length > 0) {
        setOptions(data.data)
      }
    },
  })

  return (
    <Autocomplete
      sx={{ flex: 1 }}
      options={options}
      defaultValue={defaultValue}
      isOptionEqualToValue={(option, value) => option.id === value.id}
      onChange={(e, value) => props.onChange(value)}
      getOptionLabel={(option) => option.name}
      onInputChange={(e, value) => queryRequest.run(value)}
      renderInput={(params) => <TextField {...params} fullWidth={props.fullWidth} label={props.label} />}
    />
  )
}
