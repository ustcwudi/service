import * as React from 'react'
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import SendIcon from '@mui/icons-material/Send'
import TextField from '@mui/material/TextField'
import Tabs from '@mui/material/Tabs'
import Tab from '@mui/material/Tab'
import Captcha from '@/components/captcha'

export default () => {
  const [tab, setTab] = React.useState(0)
  const [form, setForm] = React.useState({})
  const [id, setId] = React.useState('')
  return (
    <Box
      sx={{
        width: 400,
        maxWidth: '100%',
        margin: '10px auto',
      }}
    >
      <Tabs value={tab} centered sx={{ mb: 3 }}>
        <Tab label="找回密码" />
        <Tab label="返回登录" />
      </Tabs>
      <TextField fullWidth label="账号" sx={{ mb: 3 }} />
      <TextField fullWidth label="邮箱" sx={{ mb: 3 }} />
      <Captcha onChange={(e) => setForm({ ...form, captcha: `${id}:${e.target.value}` })} onUpdate={setId} />
      <Button sx={{ width: '100%', mt: 3 }} size="large" variant="contained" endIcon={<SendIcon />}>
        找回密码
      </Button>
    </Box>
  )
}
