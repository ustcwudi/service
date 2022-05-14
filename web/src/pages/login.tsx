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
        <Tab label="密码登录" />
        <Tab label="微信登陆" />
      </Tabs>
      <TextField fullWidth label="账号" sx={{ mb: 3 }} />
      <TextField fullWidth label="密码" type="password" sx={{ mb: 3 }} />
      <Captcha />
      <Box sx={{ mt: 2, mb: 2 }}>
        <FormControlLabel control={<Checkbox />} label="记住我" />
        <Link sx={{ float: 'right', lineHeight: '42px' }} href="#" underline="none">
          忘记密码？
        </Link>
      </Box>
      <Button sx={{ width: '100%' }} size="large" variant="contained" endIcon={<SendIcon />}>
        登录
      </Button>
    </Box>
  )
}
