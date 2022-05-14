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

export default () => {
  const [value, setValue] = React.useState(0)
  return (
    <Box
      sx={{
        width: 400,
        maxWidth: '100%',
        margin: '10px auto',
      }}
    >
      <Tabs value={value} centered sx={{ mb: 3 }}>
        <Tab label="找回密码" />
        <Tab label="返回登录" />
      </Tabs>
      <TextField fullWidth label="账号" sx={{ mb: 3 }} />
      <TextField fullWidth label="邮箱" sx={{ mb: 3 }} />
      <Captcha />
      <Button sx={{ width: '100%', mt: 3 }} size="large" variant="contained" endIcon={<SendIcon />}>
        找回密码
      </Button>
    </Box>
  )
}
