import * as React from 'react'
import Box from '@mui/material/Box'
import IconButton from '@mui/material/IconButton'
import UploadFileIcon from '@mui/icons-material/UploadFile'
import request from 'umi-request'

export default (props: { url: string; onComplete: (result: boolean) => void }) => {
  const hiddenFileInput = React.useRef<any>(null)
  const handleChange = (event: any) => {
    const fileUploaded = event.target.files[0]
    const formData = new FormData()
    formData.append('file', fileUploaded)
    request.post(props.url, { data: formData, requestType: 'form' }).then((result) => {
      props.onComplete(result.success)
    })
  }

  return (
    <IconButton onClick={() => hiddenFileInput.current.click()}>
      <UploadFileIcon />
      <input type="file" ref={hiddenFileInput} onChange={handleChange} style={{ display: 'none' }} />
    </IconButton>
  )
}
