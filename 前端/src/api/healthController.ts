// @ts-ignore
/* eslint-disable */
import request from '@/components/请求拦截'

/** 此处后端没有提供注释 GET /health/ */
export async function healthCheck(options?: { [key: string]: any }) {
  return request<API.qieduanjieshoushujugeshibaseresponseString>('/health/', {
    method: 'GET',
    ...(options || {}),
  })
}
