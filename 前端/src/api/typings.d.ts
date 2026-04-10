declare namespace API {
  type getInfoParams = {
    id: number
  }

  type getUserByIdParams = {
    id: number
  }

  type getUserVOByIdParams = {
    id: number
  }

  type pageParams = {
    page: PageUser
  }

  type PageUser = {
    records?: User[]
    pageNumber?: number
    pageSize?: number
    totalPage?: number
    totalRow?: number
    optimizeCountQuery?: boolean
  }

  type PageUserVO = {
    records?: UserVO[]
    pageNumber?: number
    pageSize?: number
    totalPage?: number
    totalRow?: number
    optimizeCountQuery?: boolean
  }

  type qieduanfasongshujugeshishanchugongju = {
    id?: number
  }

  type qieduanjieshoushujugeshibaseresponseBoolean = {
    code?: number
    data?: boolean
    message?: string
  }

  type qieduanjieshoushujugeshibaseresponseLong = {
    code?: number
    data?: number
    message?: string
  }

  type qieduanjieshoushujugeshibaseresponsePageUserVO = {
    code?: number
    data?: PageUserVO
    message?: string
  }

  type qieduanjieshoushujugeshibaseresponseString = {
    code?: number
    data?: string
    message?: string
  }

  type qieduanjieshoushujugeshibaseresponseUser = {
    code?: number
    data?: User
    message?: string
  }

  type qieduanjieshoushujugeshibaseresponseUserVO = {
    code?: number
    data?: UserVO
    message?: string
  }

  type qieduanjieshoushujugeshibaseresponseyonghudenglutuominshujuleixingVO = {
    code?: number
    data?: yonghudenglutuominshujuleixingVO
    message?: string
  }

  type removeParams = {
    id: number
  }

  type User = {
    id?: number
    userAccount?: string
    userPassword?: string
    userName?: string
    userAvatar?: string
    userProfile?: string
    userRole?: string
    editTime?: string
    createTime?: string
    updateTime?: string
    isDelete?: number
  }

  type UserAddRequest = {
    userName?: string
    userAccount?: string
    userAvatar?: string
    userProfile?: string
    userRole?: string
  }

  type UserQueryRequest = {
    pageNum?: number
    pageSize?: number
    sortField?: string
    sortOrder?: string
    id?: number
    userName?: string
    userAccount?: string
    userProfile?: string
    userRole?: string
  }

  type UserUpdateRequest = {
    id?: number
    userName?: string
    userAvatar?: string
    userProfile?: string
    userRole?: string
  }

  type UserVO = {
    id?: number
    userAccount?: string
    userName?: string
    userAvatar?: string
    userProfile?: string
    userRole?: string
    createTime?: string
  }

  type yonghudenglushujuleixingrequest = {
    userAccount?: string
    userPassword?: string
  }

  type yonghudenglutuominshujuleixingVO = {
    id?: number
    userAccount?: string
    userName?: string
    userAvatar?: string
    userProfile?: string
    userRole?: string
    createTime?: string
    updateTime?: string
  }

  type yonghuzhuceshujuleixingrequest = {
    userAccount?: string
    userPassword?: string
    checkPassword?: string
  }
}
