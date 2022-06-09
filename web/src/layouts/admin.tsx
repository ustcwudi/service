import React, { useState } from 'react'
import Nav from '@/components/nav'
import Menu from '@/components/menu'
import { useModel } from 'umi'
import request from 'umi-request'
import { useRequest } from 'ahooks'
import { Menu as MenuModel } from '../pages/admin/menu/index'

export default (props: { children: any }) => {
  const { user } = useModel('user', (model) => ({ user: model.user }))
  const [topMenu, setTopMenu] = useState<MenuModel[]>([])
  const [menu, setMenu] = useState<MenuModel[]>([])
  const [leftMenu, setLeftMenu] = useState<MenuModel[]>([])
  useRequest(
    () =>
      request.post('/api/menu/query', {
        data: { role: user?.role },
      }),
    {
      onSuccess: (data) => {
        setMenu(data.data as MenuModel[])
        setTopMenu((data.data as MenuModel[]).filter((i) => !i.parent))
      },
    },
  )
  return (
    <>
      <Nav menu={topMenu} menuChanged={(id) => setLeftMenu(menu.filter((i) => i.parent === id))} />
      <div style={{ display: 'flex', height: '100%' }}>
        <div style={{ flex: 0, padding: '76px 10px 10px' }}>
          <Menu menu={leftMenu} />
        </div>
        <div style={{ flex: 1, padding: '86px 20px 20px', backgroundColor: '#eee' }}>
          <div style={{ flex: 'auto', height: '100%' }}>{props.children}</div>
        </div>
      </div>
    </>
  )
}
