// @ts-ignore
/* eslint-disable */
import 自己封装的拦截请求的request from '@/components/请求拦截'

/** 此处后端没有提供注释 GET /health/ */
export async function healthCheck(options?: { [key: string]: any }) {
  return 自己封装的拦截请求的request<API.qieduanjieshoushujugeshiresponseString>('/health/', {
    method: 'GET',
    ...(options || {}),
  })
}
