import React, { useState } from 'react';
import Block from '@material-ui/icons/Block';
import Menu from '@material-ui/icons/Menu';
import MenuOpen from '@material-ui/icons/MenuOpen';
import AccountCircle from '@material-ui/icons/AccountCircle';
import CardGiftcard from '@material-ui/icons/CardGiftcard';
import Apps from '@material-ui/icons/Apps';
import Settings from '@material-ui/icons/Settings';
import Lock from '@material-ui/icons/Lock';
import CalendarViewDay from '@material-ui/icons/CalendarViewDay';
import CloudUpload from '@material-ui/icons/CloudUpload';
import RecentActors from '@material-ui/icons/RecentActors';
import People from '@material-ui/icons/People';
import MenuBook from '@material-ui/icons/MenuBook';
import LocalFlorist from '@material-ui/icons/LocalFlorist';
import Assignment from '@material-ui/icons/Assignment';
import TableChart from '@material-ui/icons/TableChart';
import FontDownload from '@material-ui/icons/FontDownload';
import Group from '@material-ui/icons/Group';
import GroupWork from '@material-ui/icons/GroupWork';
import AccountBalance from '@material-ui/icons/AccountBalance';
import AccountBox from '@material-ui/icons/AccountBox';
import Input from '@material-ui/icons/Input';
import VerifiedUser from '@material-ui/icons/VerifiedUser';
import Chat from '@material-ui/icons/Chat';
import Print from '@material-ui/icons/Print';
import DynamicFeed from '@material-ui/icons/DynamicFeed';
import ExpandLess from '@material-ui/icons/ExpandLess';
import ExpandMore from '@material-ui/icons/ExpandMore';
import Add from '@material-ui/icons/Add';
import Search from '@material-ui/icons/Search';
import Send from '@material-ui/icons/Send';
import Refresh from '@material-ui/icons/Refresh';
import Publish from '@material-ui/icons/Publish';
import Delete from '@material-ui/icons/Delete';
import Edit from '@material-ui/icons/Edit';
import Replay from '@material-ui/icons/Replay';
import Close from '@material-ui/icons/Close';
import Cancel from '@material-ui/icons/Cancel';
import Check from '@material-ui/icons/Check';
import HelpOutline from '@material-ui/icons/HelpOutline';
import Telegram from '@material-ui/icons/Telegram';
import ImageOutlined from '@material-ui/icons/ImageOutlined';
import DeleteOutline from '@material-ui/icons/DeleteOutline';
import DeleteForever from '@material-ui/icons/DeleteForever';
import SettingsBackupRestore from '@material-ui/icons/SettingsBackupRestore';
import DescriptionOutlined from '@material-ui/icons/DescriptionOutlined';

export default (props: any) => {
  switch (props.name) {
    case "Replay":
      return <Replay color={props.color} className={props.classes?.icon} />
    case "Telegram":
      return <Telegram color={props.color} className={props.classes?.icon} />
    case "Close":
      return <Close color={props.color} className={props.classes?.icon} />
    case "Cancel":
      return <Cancel color={props.color} className={props.classes?.icon} />
    case "Check":
      return <Check color={props.color} className={props.classes?.icon} />
    case "HelpOutline":
      return <HelpOutline color={props.color} className={props.classes?.icon} />
    case "ImageOutlined":
      return <ImageOutlined color={props.color} className={props.classes?.icon} />
    case "DescriptionOutlined":
      return <DescriptionOutlined color={props.color} className={props.classes?.icon} />
    case "CloudUpload":
      return <CloudUpload color={props.color} className={props.classes?.icon} />
    case "Edit":
      return <Edit color={props.color} className={props.classes?.icon} />
    case "DeleteForever":
      return <DeleteForever color={props.color} className={props.classes?.icon} />
    case "SettingsBackupRestore":
      return <SettingsBackupRestore color={props.color} className={props.classes?.icon} />
    case "DeleteOutline":
      return <DeleteOutline color={props.color} className={props.classes?.icon} />
    case "Delete":
      return <Delete color={props.color} className={props.classes?.icon} />
    case "Publish":
      return <Publish color={props.color} className={props.classes?.icon} />
    case "Add":
      return <Add color={props.color} className={props.classes?.icon} />
    case "Search":
      return <Search color={props.color} className={props.classes?.icon} />
    case "Send":
      return <Send color={props.color} className={props.classes?.icon} />
    case "Refresh":
      return <Refresh color={props.color} className={props.classes?.icon} />
    case "Menu":
      return <Menu color={props.color} className={props.classes?.icon} />
    case "MenuOpen":
      return <MenuOpen color={props.color} className={props.classes?.icon} />
    case "AccountCircle":
      return <AccountCircle color={props.color} className={props.classes?.icon} />
    case "CardGiftcard":
      return <CardGiftcard color={props.color} className={props.classes?.icon} />
    case "Apps":
      return <Apps color={props.color} className={props.classes?.icon} />
    case "Settings":
      return <Settings color={props.color} className={props.classes?.icon} />
    case "Lock":
      return <Lock color={props.color} className={props.classes?.icon} />
    case "CalendarViewDay":
      return <CalendarViewDay color={props.color} className={props.classes?.icon} />
    case "CloudUpload":
      return <CloudUpload color={props.color} className={props.classes?.icon} />
    case "RecentActors":
      return <RecentActors color={props.color} className={props.classes?.icon} />
    case "People":
      return <People color={props.color} className={props.classes?.icon} />
    case "MenuBook":
      return <MenuBook color={props.color} className={props.classes?.icon} />
    case "LocalFlorist":
      return <LocalFlorist color={props.color} className={props.classes?.icon} />
    case "Assignment":
      return <Assignment color={props.color} className={props.classes?.icon} />
    case "TableChart":
      return <TableChart color={props.color} className={props.classes?.icon} />
    case "FontDownload":
      return <FontDownload color={props.color} className={props.classes?.icon} />
    case "Group":
      return <Group color={props.color} className={props.classes?.icon} />
    case "GroupWork":
      return <GroupWork color={props.color} className={props.classes?.icon} />
    case "AccountBalance":
      return <AccountBalance color={props.color} className={props.classes?.icon} />
    case "AccountBox":
      return <AccountBox color={props.color} className={props.classes?.icon} />
    case "Input":
      return <Input color={props.color} className={props.classes?.icon} />
    case "VerifiedUser":
      return <VerifiedUser color={props.color} />
    case "Chat":
      return <Chat color={props.color} className={props.classes?.icon} />
    case "Print":
      return <Print color={props.color} className={props.classes?.icon} />
    case "DynamicFeed":
      return <DynamicFeed color={props.color} className={props.classes?.icon} />
    case "ExpandLess":
      return <ExpandLess color={props.color} className={props.classes?.icon} />
    case "ExpandMore":
      return <ExpandMore color={props.color} className={props.classes?.icon} />
    default:
      return <Block color={props.color} className={props.classes?.icon} />
  }
}