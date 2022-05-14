import Admin from './admin'
import Frame from './frame'
export default function (props: { location: { pathname: string }; children: any }) {
  if (props.location.pathname.startsWith('/admin')) {
    return <Admin>{props.children}</Admin>
  } else {
    return <Frame>{props.children}</Frame>
  }
}
