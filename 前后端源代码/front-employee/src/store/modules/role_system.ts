import { defineStore } from "pinia";
import {
  type userType,
  store,
  router,
  resetRouter,
  routerArrays,
  storageLocal
} from "../utils";
import {
  type UserInfoResult,
} from "@/api/user";
import { getDeptIsExist, deleteApartment, insertUser, updateUser } from "@/api/system";


export const useRoleUserStore = defineStore({
  id: "pure-role_system",
  actions: {
    /** 查询该部门的该职位是否已满*/
    async checkRoleUserIsExist(data) {
      return new Promise<UserInfoResult>((resolve, reject) => {
        insertUser(data)
          .then(data => {
            resolve(data);
          })
          .catch(error => {
            reject(error);
          });
      });
    },
    // 修改
    async checkRoleUserIsExist2(data) {
      return new Promise<UserInfoResult>((resolve, reject) => {
        updateUser(data)
          .then(data => {
            resolve(data);
          })
          .catch(error => {
            reject(error);
          });
      });
    },


  }
});

export function useRoleUsertoreHook() {
  return useRoleUserStore(store);
}
