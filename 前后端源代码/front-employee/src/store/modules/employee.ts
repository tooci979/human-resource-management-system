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
import { checkAvaliabelIsNotFull, checkAvaliabelIsNotFullWithUpdate } from "@/api/system";


export const useEmployeeStore = defineStore({
  id: "pure-employee",
  actions: {
    /** 查询该部门的该职位是否已满*/
    async checkEmployeeIsFull(data) {
      return new Promise<UserInfoResult>((resolve, reject) => {
        checkAvaliabelIsNotFull(data)
          .then(data => {
            resolve(data);
          })
          .catch(error => {
            reject(error);
          });
      });
    },

    async checkEmployeeIsFullWithUpdate(data) {
      return new Promise<UserInfoResult>((resolve, reject) => {
        checkAvaliabelIsNotFullWithUpdate(data)
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

export function useEmployeeStoreHook() {
  return useEmployeeStore(store);
}
