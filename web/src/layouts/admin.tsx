import Nav from '@/components/nav'
import Menu from '@/components/menu'

export default (props: { children: any }) => {
  return (
    <>
      <Nav />
      <div style={{ display: 'flex', height: '100%' }}>
        <div style={{ flex: 0, padding: '60px 10px 10px' }}>
          <Menu />
        </div>
        <div style={{ flex: 1, padding: '70px 20px 20px', backgroundColor: '#eee' }}>
          <div style={{ flex: 'auto', height: '100%' }}>{props.children}</div>
        </div>
      </div>
    </>
  )
}
