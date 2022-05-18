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
  defaultValue?: string | null
  options?: T[]
  onChange: (value: T | null) => void
}

export default <T extends Model & { name: string }>(props: Props<T>) => {
  const [value, setValue] = React.useState<T | null>(null)
  const [options, setOptions] = React.useState<T[]>(props.options ? props.options : [])
  const queryRequest = useRequest(
    (keyword: string) => request.get(`/api/${props.table}?name=${keyword}&pageSize=10`, { useCache: true, ttl: 10 * 60 * 1000, headers: props.link ? { link: props.link } : {} }),
    {
      manual: true,
      onSuccess: (data) => {
        if (data.data?.length > 0) {
          setOptions(data.data)
        }
      },
    },
  )

  React.useEffect(() => {
    if (props.defaultValue) {
      request.get(`/api/${props.table}/id/${props.defaultValue}`, { useCache: true, ttl: 10 * 60 * 1000, headers: props.link ? { link: props.link } : {} }).then(function (response) {
        setValue(response.data)
        setOptions([response.data])
      })
    }
  }, [])

  return (
    <Autocomplete
      sx={{ flex: 1 }}
      options={options}
      value={value}
      isOptionEqualToValue={(option, value) => option.id === value.id}
      onChange={(e, value) => {
        setValue(value)
        props.onChange(value)
      }}
      getOptionLabel={(option) => option.name}
      onInputChange={(e, value) => queryRequest.run(value)}
      renderInput={(params) => <TextField {...params} fullWidth={props.fullWidth} label={props.label} />}
    />
  )
}
