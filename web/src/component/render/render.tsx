import React from 'react';
import { styled } from '@material-ui/core/styles';
import Link from '@material-ui/core/Link';
import Chip from '@material-ui/core/Chip';
import Avatar from '@material-ui/core/Avatar';
import Tooltip from '@material-ui/core/Tooltip';
import Icon from '@/component/icon/icon'

const Badge = styled(Chip)({
  width: 8,
  height: 8
});

const UserAvatar = styled(Avatar)({
  width: 30,
  height: 30
});

const renderBool = (value?: boolean) => {
  return (
    value === undefined || value === null ? <Badge color="default" /> :
      value === true ?
        <Icon color="disabled" name="Check" /> :
        <Icon color="disabled" name="Close" />
  );
};

const renderSex = (value?: boolean) => {
  return (
    value === undefined || value === null ? <Badge color="default" /> :
      value === true ? '男' : '女'
  );
};

const renderString = (value?: string) => {
  return value === undefined || value === null ? <Badge color="default" /> : value
};

const renderUpload = (value?: string) => {
  if (value === undefined || value === null || value === "") return <Badge color="default" />
  else {
    if ("http" === value.substring(0, 4))
      if ("https://thirdwx.qlogo.cn/" === value.substring(0, 25))
        return <UserAvatar src={value} />
      else
        return <Link href={value} target="_blank"><Icon color="action" name="ImageOutlined" /></Link>
    else {
      let ext = value.substring(value.lastIndexOf(".") + 1, value.length);
      if (ext === "jpg" || ext === "jpeg" || ext === "gif" || ext === "png")
        return <Link href={"/" + value} target="_blank"><Icon color="action" name="ImageOutlined" /></Link>
      else
        return <Link href={"/" + value} target="_blank"><Icon color="action" name="DescriptionOutlined" /></Link>
    }
  }
};

const renderInt = (value?: number) => {
  return value === undefined || value === null ? <Badge color="default" /> : value.toString()
};

const renderFloat = (value?: number) => {
  return value === undefined || value === null ? <Badge color="default" /> : value.toString()
};

const renderID = (value?: string | { id: string, name: string }) => {
  return value === undefined || value === null ? <Badge color="default" /> :
    typeof value === "string" ? <Tooltip key={value} title={value}><Badge color="secondary" /></Tooltip>
      : <Tooltip key={value.id} title={value.id}><Chip size="small" label={value.name} /></Tooltip>
};

const renderStringArray = (value?: string[]) => {
  return value === undefined || value === null ? <Badge color="default" /> : value.map((v, i) => <Chip key={i} size="small" variant="outlined" label={v} />)
};

const renderIntArray = (value?: number[]) => {
  return value === undefined || value === null ? <Badge color="default" /> : value.map((v, i) => <Chip key={i} size="small" variant="outlined" label={v} />)
};

const renderFloatArray = (value?: number[]) => {
  return value === undefined || value === null ? <Badge color="default" /> : value.map((v, i) => <Chip key={i} size="small" variant="outlined" label={v} />)
};

const renderIDArray = (value?: (string | { id: string, name: string })[]) => {
  return value === undefined || value === null ? <Badge color="default" /> : value.map((v: string | { id: string; name: string; }) => renderID(v))
};

const renderStringMap = (value?: { [key: string]: string }) => {
  if (value === undefined || value === null) return <Badge color="default" />
  else {
    var list = []
    for (let key in value) {
      list.push(<Tooltip key={key} title={key}><Chip size="small" label={value[key]} /></Tooltip>)
    }
    return list
  }
};

const renderStringArrayMap = (value?: { [key: string]: string[] }) => {
  if (value === undefined || value === null) return <Badge color="default" />
  else {
    var list = []
    for (let key in value) {
      list.push(<Tooltip key={key} title={key}><Chip size="small" label={value[key].join(",")} /></Tooltip>)
    }
    return list
  }
};

const renderIntMap = (value?: { [key: string]: number }) => {
  if (value === undefined || value === null) return <Badge color="default" />
  else {
    var list = []
    for (let key in value) {
      list.push(<Tooltip key={key} title={key}><Chip size="small" label={value[key]} /></Tooltip>)
    }
    return list
  }
};

const renderFloatMap = (value?: { [key: string]: number }) => {
  if (value === undefined || value === null) return <Badge color="default" />
  else {
    var list = []
    for (let key in value) {
      list.push(<Tooltip key={key} title={key}><Chip size="small" label={value[key]} /></Tooltip>)
    }
    return list
  }
};

export default { renderBool, renderSex, renderString, renderUpload, renderInt, renderFloat, renderID, renderStringArray, renderIntArray, renderFloatArray, renderIDArray, renderStringMap, renderStringArrayMap, renderIntMap, renderFloatMap }