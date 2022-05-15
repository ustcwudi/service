import * as React from 'react'

// 菜单项
export interface MenuItem {
  parent: string | null
  label: string
  key: string
  position: string
  href?: string
  children?: MenuItem[]
  icon?: React.FunctionComponent
}
// 表格列属性
export interface TableColumn<T> {
  key: keyof T
  title: string
  render: React.FunctionComponent<React.PropsWithChildren<{ value: T }>>
}

// 表单列属性
export interface FormColumn<T> {
  key: keyof T
  title: string
  render: React.FunctionComponent<React.PropsWithChildren<{ value: T; defaultValue?: T }>>
}

// 基类
export declare class Model {
  // ID
  id?: string
  // 创建时间
  createTime?: number
  // 修改时间
  updateTime?: number
  // 废弃时间
  deleteTime?: number
}

// 查询基类
export declare class QueryModel {
  // ID
  id?: string[]
  // 创建时间
  createTime?: [null | number, null | number]
  // 修改时间
  updateTime?: [null | number, null | number]
  // 废弃时间
  deleteTime?: [null | number, null | number]
}
