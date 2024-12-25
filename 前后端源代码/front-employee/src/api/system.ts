import { http } from "@/utils/http";

type Result = {
  success: boolean;
  data?: Array<any>;
};
type isT = {
  success: boolean;
};
type ResultdataApartment = {
  success: boolean;
  data?: Array<any>;
};

type ResultTable = {
  success: boolean;
  data?: {
    /** 列表数据 */
    list: Array<any>;
    /** 总条目数 */
    total?: number;
    /** 每页显示条目个数 */
    pageSize?: number;
    /** 当前页数 */
    currentPage?: number;
  };
};



/** 获取系统管理-用户管理列表 */
export const getRoleList = (data?: object) => {
  return http.request<ResultTable>("post", `/api/user/findUserPage`, { data });
};

/** 新增用户-用户管理列表 */
export const insertUser = (data?: object) => {
  return http.request<isT>("post", `/api/user/insertUser`, { data });
};
/** 更新用户-用户管理列表 */
export const updateUser = (data?: object) => {
  return http.request<isT>("post", `/api/user/updateUser`, { data });
};
// 删除deleteUser
export const deleteUser = (data?: object) => {
  return http.request<Result>("get", `/api/user/deleteUser?id=${data.id}`, { data });
};

// 更新state
export const updateState = (data?: object) => {
  return http.request<Result>("post", `/api/user/updateState`, { data });
};







/** 获取职员管理-职员管理列表 */
export const getEmployeeList = (data?: object) => {
  return http.request<ResultTable>("post", "/api/employee/findEmployeeAllPage", { data });
};
/*查询对应部门的所有岗位 */
export const getAllJobByApartmetId = (data?: object) => {
  return http.request<Result>("post", "/api/employee/findJobNameByApartmentId", { data });
};
/** 查询该部门的该职位是否已满*/
export const checkAvaliabelIsNotFull = (data?: object) => {
  return http.request<Result>("post", `/api/employee/avaliabelIsNotFull`, { data });
};
/** 查询该部门的该职位是否已满 Update*/
export const checkAvaliabelIsNotFullWithUpdate = (data?: object) => {
  // return http.request<Result>("get", `/api/employee/avaliabelIsNotFull?apartmentId=${data.apartmentId}&&employeeJobId=${data.employeeJobId}`, { data });
  return http.request<Result>("post", `/api/employee/avaliabelIsNotFullWithUpdate`, { data });
};
/** 查询该部门的该职位是否已满 Update*/
export const deleteEmployee = (data?: object) => {
  // return http.request<Result>("get", `/api/employee/avaliabelIsNotFull?apartmentId=${data.apartmentId}&&employeeJobId=${data.employeeJobId}`, { data });
  return http.request<Result>("get", `/api/employee/deleteElemployee?id=${data}`, { data });
};

/** 添加*/
export const addEmployee = (data?: object) => {
  return http.request<Result>("post", `/api/employee/addEmployee`, { data });
};

/** 修改*/
export const UpdateEmployee = (data?: object) => {
  return http.request<Result>("post", `/api/employee/UpdateEmployee`, { data });
};

/** 获取岗位管理-岗位管理列表 */
export const getJobList = (data?: object) => {
  return http.request<ResultTable>("post", "/api/job/findJobAll", { data });
};
/** 查询该部门是否存在该职位 */
export const checkJob = (data?: object) => {
  return http.request<Result>("post", "/api/job/JobIsExist", { data });
};
// 删除jobbyid
export const deleteJob = (data?: object) => {
  return http.request<Result>("get", `/api/job/deleteJob?id=${data}`, { data });
};
// 修改job表 byid
export const updateJobById = (data?: object) => {
  return http.request<Result>("post", `/api/job/updateJobById`, { data });
};


/** 获取有几个部门 */
export const getApartmentList = (data?: object) => {
  return http.request<ResultdataApartment>("post", "/api/apartment/deptList", { data });
};

/** 获取系统管理-部门管理列表 */
export const getDeptList = (data?: object) => {
  return http.request<Result>("post", "/api/apartment/deptListQueryWrapper", { data });
};

// 该部门是否存在  =》apartment
export const getDeptIsExist = (data?: object) => {
  return http.request<Result>("get", `/api/apartment/deptIsExist?department=${data.department}`, { data });
};
// 插入数据  =》apartment
export const insertApartment = (data?: object) => {
  return http.request<Result>("post", `/api/apartment/insertApartment?department`, { data });
};
// 更新数据  =》apartment
export const updateApartment = (data?: object) => {
  return http.request<Result>("post", `/api/apartment/updateApartment`, { data });
};

// 删除数据  =》apartment
export const deleteApartment = (data?: object) => {
  return http.request<Result>("get", `/api/apartment/deleteApartment?id=${data.id}`, { data });
};




/** 发送邮件 */
export const getSendEmail = (data?: object) => {
  return http.request<Result>("post", "/api/sendEmail/sendResume", { data });
};

export const getSendEmail2 = (data?: object) => {
  return http.post("/api/sendEmail/sendResume", { data });
};




/** 获取系统监控-在线用户列表 */
export const getOnlineLogsList = (data?: object) => {
  return http.request<ResultTable>("post", "/online-logs", { data });
};

/** 获取系统监控-登录日志列表 */
export const getLoginLogsList = (data?: object) => {
  return http.request<ResultTable>("post", "/login-logs", { data });
};

/** 获取系统监控-操作日志列表 */
export const getOperationLogsList = (data?: object) => {
  return http.request<ResultTable>("post", "/operation-logs", { data });
};

/** 获取系统监控-系统日志列表 */
export const getSystemLogsList = (data?: object) => {
  return http.request<ResultTable>("post", "/system-logs", { data });
};

/** 获取系统监控-系统日志-根据 id 查日志详情 */
export const getSystemLogsDetail = (data?: object) => {
  return http.request<Result>("post", "/system-logs-detail", { data });
};

/** 获取角色管理-权限-菜单权限 */
export const getRoleMenu = (data?: object) => {
  return http.request<Result>("post", "/role-menu", { data });
};

/** 获取角色管理-权限-菜单权限-根据角色 id 查对应菜单 */
export const getRoleMenuIds = (data?: object) => {
  return http.request<Result>("post", "/role-menu-ids", { data });
};

/** 获取系统管理-用户管理列表 */
export const getUserList = (data?: object) => {
  return http.request<ResultTable>("post", "/user", { data });
};

/** 系统管理-用户管理-获取所有角色列表 */
export const getAllRoleList = () => {
  return http.request<Result>("get", "/list-all-role");
};

/** 系统管理-用户管理-根据userId，获取对应角色id列表（userId：用户id） */
export const getRoleIds = (data?: object) => {
  return http.request<Result>("post", "/list-role-ids", { data });
};

/** 获取系统管理-菜单管理列表 */
export const getMenuList = (data?: object) => {
  return http.request<Result>("post", "/menu", { data });
};