import React, { useState } from 'react'
import TextField from '@mui/material/TextField'
import request from 'umi-request'
import { useRequest } from 'ahooks'

export default (props: { onUpdate: (value: string) => any; onChange: React.ChangeEventHandler<HTMLTextAreaElement | HTMLInputElement> | undefined }) => {
  const [blob, setBlob] = useState<string | undefined>(undefined)
  const { run } = useRequest(
    () =>
      request.get('/api/captcha', {
        responseType: 'blob',
        getResponse: true,
      }),
    {
      onSuccess: (data) => {
        setBlob(URL.createObjectURL(new Blob([data.data])))
        let id = data.response.headers.get('id')
        id && props.onUpdate(id)
      },
    },
  )
  return (
    <div style={{ position: 'relative' }}>
      <TextField onChange={props.onChange} fullWidth label="验证码" variant="outlined" />
      <img style={{ position: 'fixed', right: 0, height: '56px', cursor: 'pointer' }} src={blob} alt="验证码" onClick={run} />
    </div>
  )
}
