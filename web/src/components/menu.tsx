import * as React from 'react'
import MenuList from '@mui/material/MenuList'
import MenuItem from '@mui/material/MenuItem'
import ListItemText from '@mui/material/ListItemText'
import ListItemIcon from '@mui/material/ListItemIcon'
import ContentCopy from '@mui/icons-material/ContentCopy'
import { Menu as MenuModel } from '../pages/admin/menu/index'
import { useModel, history } from 'umi'

export default (props: { menu: MenuModel[] }) => {
  return (
    <MenuList sx={{ width: 240 }}>
      {props.menu.map((i) => (
        <MenuItem key={i.id} sx={{ py: 1.5 }} onClick={() => history.push(i.path)}>
          <ListItemIcon>
            <ContentCopy fontSize="small" />
          </ListItemIcon>
          <ListItemText>{i.name}</ListItemText>
        </MenuItem>
      ))}
    </MenuList>
  )
}
