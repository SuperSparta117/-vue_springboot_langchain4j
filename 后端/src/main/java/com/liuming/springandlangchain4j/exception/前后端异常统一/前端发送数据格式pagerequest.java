package com.liuming.springandlangchain4j.exception.前后端异常统一;
import lombok.Data;

// 这个“请求包装类"你可以理解成!
// 不是返回给前端的。
// 而是专门用来接收前端传过来的参数。
 
//前端传的格式：
// {
//   "pageNum": 1,
//   "pageSize": 5,
//   "sortField": "createTime",
//   "sortOrder": "descend"
// }
// //后端接收
// @PostMapping("/list/page")
// public BaseResponse<?> listUserByPage(@RequestBody PageRequest pageRequest) {
//     System.out.println(pageRequest.getPageNum());
//     System.out.println(pageRequest.getPageSize());
//     System.out.println(pageRequest.getSortField());
//     System.out.println(pageRequest.getSortOrder());

//     return ResultUtils.success("查询成功");
// }
@Data
public class 前端发送数据格式pagerequest {

    /**
     * 当前页号
     */
    private int pageNum = 1;

    /**
     * 页面大小
     */
    private int pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认降序）
     */
    private String sortOrder = "descend";
}
