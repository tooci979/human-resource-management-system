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
import { getDeptIsExist, deleteApartment } from "@/api/system";


export const useApartmentStore = defineStore({
  id: "pure-apartment",
  actions: {
    /** 查询该部门的该职位是否已满*/
    async checkDeptIsExist(data) {
      return new Promise<UserInfoResult>((resolve, reject) => {
        getDeptIsExist(data)
          .then(data => {
            resolve(data);
          })
          .catch(error => {
            reject(error);
          });
      });
    },

    /** 删除， 若该部门 没有要招聘的职位，就可以删除*/
    async checkApartmentIsNull(data) {
      return new Promise<UserInfoResult>((resolve, reject) => {
        deleteApartment(data)
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

export function useApartmentStoreHook() {
  return useApartmentStore(store);
}
