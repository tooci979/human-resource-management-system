// 模拟后端动态生成路由
import { defineFakeRoute } from "vite-plugin-fake-server/client";
import { system, monitor, permission, frame, tabs, employee, department, job, role } from "@/router/enums";

/**
 * roles：页面级别权限，这里模拟二种 "admin"、"common"
 * admin：管理员角色
 * common：普通角色
 */

const systemManagementRouter = {
  path: "/system",
  meta: {
    icon: "ri:settings-3-line",
    title: "menus.pureSysManagement",
    rank: system
  },
  children: [
    {
      path: "/system/user/index",
      name: "SystemUser",
      meta: {
        icon: "ri:admin-line",
        title: "menus.pureUser",
        roles: ["admin"]
      }
    },
    {
      path: "/system/role/index",
      name: "SystemRole",
      meta: {
        icon: "ri:admin-fill",
        title: "menus.pureRole",
        roles: ["admin"]
      }
    },
    {
      path: "/system/menu/index",
      name: "SystemMenu",
      meta: {
        icon: "ep:menu",
        title: "menus.pureSystemMenu",
        roles: ["admin"]
      }
    },
    {
      path: "/system/dept/index",
      name: "SystemDept",
      meta: {
        icon: "ri:git-branch-line",
        title: "menus.pureDept",
        roles: ["admin"]
      }
    }
  ]
};

const systemMonitorRouter = {
  path: "/monitor",
  meta: {
    icon: "ep:monitor",
    title: "menus.pureSysMonitor",
    rank: monitor
  },
  children: [
    {
      path: "/monitor/online-user",
      component: "monitor/online/index",
      name: "OnlineUser",
      meta: {
        icon: "ri:user-voice-line",
        title: "menus.pureOnlineUser",
        roles: ["admin", "common"]
      }
    },
    {
      path: "/monitor/login-logs",
      component: "monitor/logs/login/index",
      name: "LoginLog",
      meta: {
        icon: "ri:window-line",
        title: "menus.pureLoginLog",
        roles: ["admin", "common"]
      }
    },
    {
      path: "/monitor/operation-logs",
      component: "monitor/logs/operation/index",
      name: "OperationLog",
      meta: {
        icon: "ri:history-fill",
        title: "menus.pureOperationLog",
        roles: ["admin", "common"]
      }
    },
    {
      path: "/monitor/system-logs",
      component: "monitor/logs/system/index",
      name: "SystemLog",
      meta: {
        icon: "ri:file-search-line",
        title: "menus.pureSystemLog",
        roles: ["admin", "common"]
      }
    }
  ]
};

const permissionRouter = {
  path: "/permission",
  meta: {
    title: "menus.purePermission",
    icon: "ep:lollipop",
    rank: permission
  },
  children: [
    {
      path: "/permission/page/index",
      name: "PermissionPage",
      meta: {
        title: "menus.purePermissionPage",
        roles: ["admin", "common"]
      }
    },
    {
      path: "/permission/button/index",
      name: "PermissionButton",
      meta: {
        title: "menus.purePermissionButton",
        roles: ["admin", "common"],
        auths: [
          "permission:btn:add",
          "permission:btn:edit",
          "permission:btn:delete"
        ]
      }
    }
  ]
};

// 职员
const employeeRouter = {
  path: "/employee",
  meta: {
    icon: "ri:bookmark-2-line",
    title: "menus.pureEmployee",
    rank: employee,
  },
  children: [
    {
      path: "/employee/manage/index",
      name: "employee-manage",
      meta: {
        title: "menus.employee-manage",
        roles: ["admin", "common"],

      }
    },

  ]
};

// 部门
const departmentRouter = {
  path: "/sys_department",
  meta: {
    icon: "ri:settings-3-line",
    title: "menus.pureSysManagement",
    rank: department
  },
  children: [
    {
      path: "/sys_department/dept/index",
      name: "department-manage",
      meta: {
        icon: "ri:git-branch-line",
        title: "menus.pureDepartmentManage",
        roles: ["admin", "common"]
      }
    }
  ]
};
// 岗位管理
const jobRouter = {
  path: "/job",
  meta: {
    icon: "ri:settings-3-line",
    title: "menus.pureJobManagement",
    rank: job
  },
  children: [

    {
      path: "/job/jobManage/index",
      name: "job",
      meta: {
        icon: "ri:admin-fill",
        title: "menus.pureJob",
        roles: ["admin", "common"],

      }
    },

  ]
};
// 系统角色
const sysRoleRouter = {
  path: "/role_system",
  meta: {
    icon: "ri:settings-3-line",
    title: "menus.pureRoleManagement",
    rank: role
  },
  children: [

    {
      path: "/role_system/role/index",
      name: "SystemRole",
      meta: {
        icon: "ri:admin-fill",
        title: "menus.pureRole",
        roles: ["admin"]
      }
    }
  ]
}




export default defineFakeRoute([
  {
    url: "/get-async-routes",
    method: "get",
    response: () => {
      return {
        success: true,
        data: [
          employeeRouter, departmentRouter, jobRouter, sysRoleRouter,
        ]
      };
    }
  }
]);
