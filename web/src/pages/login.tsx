import * as React from 'react'
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import SendIcon from '@mui/icons-material/Send'
import TextField from '@mui/material/TextField'
import Tabs from '@mui/material/Tabs'
import Tab from '@mui/material/Tab'
import Captcha from '@/components/captcha'
import FormControlLabel from '@mui/material/FormControlLabel'
import Checkbox from '@mui/material/Checkbox'
import Link from '@mui/material/Link'
import request from 'umi-request'
import { useRequest } from 'ahooks'

export default () => {
  const [tab, setTab] = React.useState(0)
  const [form, setForm] = React.useState({})
  const [id, setId] = React.useState('')

  const { data, loading, run } = useRequest(() => request.post('/api/user/login', { data: form }), {
    onSuccess: (data) => {},
    manual: true,
  })

  return (
    <Box
      sx={{
        width: 400,
        maxWidth: '100%',
        margin: '10px auto',
      }}
    >
      <Tabs value={tab} centered sx={{ mb: 3 }}>
        <Tab label="密码登录" />
        <Tab label="微信登陆" />
      </Tabs>
      <TextField onChange={(e) => setForm({ ...form, account: e.target.value })} fullWidth label="账号" sx={{ mb: 3 }} />
      <TextField onChange={(e) => setForm({ ...form, password: e.target.value })} fullWidth label="密码" type="password" sx={{ mb: 3 }} />
      <Captcha onChange={(e) => setForm({ ...form, captcha: `${id}:${e.target.value}` })} onUpdate={setId} />
      <Box sx={{ mt: 2, mb: 2 }}>
        <FormControlLabel control={<Checkbox />} label="记住我" />
        <Link sx={{ float: 'right', lineHeight: '42px' }} href="#" underline="none">
          忘记密码？
        </Link>
      </Box>
      <Button sx={{ width: '100%' }} size="large" variant="contained" endIcon={<SendIcon />} onClick={run}>
        登录
      </Button>
    </Box>
  )
}
