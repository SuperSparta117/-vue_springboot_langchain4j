//此文件为openapi和tslib的配置文件，主要用于根据后端接口文档自动生成前端请求代码和 TypeScript 类型定义。
// 如果采用传统开发方式，针对每个请求都要单独编写代码，很麻烦。推荐使用 OpenAPI 工具，直接根据后端接口文档自动生成前端请求代码即可，这种方式会比 AI生成更可控。

import { 服务器swagger地址,服务器请求地址 } from './src/stores/全局变量.js'

//此配置文件必须放置于根目录
export default {
  requestLibPath: "import request from '@/components/请求拦截'",//所有生成的代码，同一引入这个包
  //注意，schemaPath应该指向 Swagger 的 JSON 格式接口文档地址，而不是HTML 页面 http://localhost:1264/api/doc.html!
  schemaPath: 服务器swagger地址,
  serversPath: './src',
}
// 在package.json的scripts中添加“openapi2ts":"openapi2ts"
// 执行脚本即可生成请求代码，还包括 TypeScript 类型
//以后每次后端接口变更时，只需要重新生成一遍就好，非常方便。