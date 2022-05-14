import loginBG from '@/assets/login-bg.svg'
import loginBoxBG from '@/assets/login-box-bg.svg'
import textLogo from '@/assets/text-logo.svg'

export default (props: { children: any }) => {
  return (
    <div style={{ display: 'flex', height: '100%' }}>
      <div style={{ flex: 3, height: '100%', backgroundImage: `url(${loginBG})`, backgroundPosition: '100%', backgroundRepeat: 'no-repeat', position: 'relative' }}>
        <div
          style={{
            width: '600px',
            height: '600px',
            backgroundImage: `url(${loginBoxBG})`,
            backgroundPosition: 'center',
            backgroundRepeat: 'no-repeat',
            backgroundSize: 'contain',
            position: 'absolute',
            left: '50%',
            top: '50%',
            transform: 'translate(-50%,-55%)',
          }}
        ></div>
      </div>
      <div style={{ flex: 2, position: 'relative' }}>
        <div style={{ position: 'absolute', left: '50%', top: '50%', transform: 'translate(-50%,-65%)' }}>
          <div style={{ height: '120px', backgroundImage: `url(${textLogo})`, backgroundPosition: 'center', backgroundRepeat: 'no-repeat', backgroundSize: 'contain' }}></div>
          {props.children}
        </div>
      </div>
    </div>
  )
}
