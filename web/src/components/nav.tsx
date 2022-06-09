import * as React from 'react'
import AppBar from '@mui/material/AppBar'
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import Toolbar from '@mui/material/Toolbar'
import Typography from '@mui/material/Typography'
import IconButton from '@mui/material/IconButton'
import Badge from '@mui/material/Badge'
import FilterDramaIcon from '@mui/icons-material/FilterDrama'
import AccountCircle from '@mui/icons-material/AccountCircle'
import MailIcon from '@mui/icons-material/Mail'
import NotificationsIcon from '@mui/icons-material/Notifications'
import AcUnitIcon from '@mui/icons-material/AcUnit'
import { Menu as MenuModel } from '../pages/admin/menu/index'

export default (props: { menu: MenuModel[]; menuChanged: (id?: string) => void }) => {
  const settings = ['Profile', 'Account', 'Dashboard', 'Logout']
  return (
    <AppBar position="fixed">
      <Toolbar>
        <IconButton edge="start" color="inherit" sx={{ mr: 2 }}>
          <FilterDramaIcon />
        </IconButton>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          智慧云平台
        </Typography>
        <Box sx={{ flexGrow: 1, display: 'flex' }}>
          {props.menu.map((item) => (
            <Button startIcon={<AcUnitIcon />} key={item.id} sx={{ mx: 2, color: 'white' }} onClick={() => props.menuChanged(item.id)}>
              {item.name}
            </Button>
          ))}
        </Box>
        <Box sx={{ display: 'flex' }}>
          <IconButton size="large" color="inherit">
            <Badge badgeContent={4} color="error">
              <MailIcon />
            </Badge>
          </IconButton>
          <IconButton size="large" color="inherit">
            <Badge badgeContent={17} color="error">
              <NotificationsIcon />
            </Badge>
          </IconButton>
          <IconButton size="large" edge="end" color="inherit">
            <AccountCircle />
          </IconButton>
        </Box>
      </Toolbar>
    </AppBar>
  )
}
