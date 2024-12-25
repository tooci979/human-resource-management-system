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
  type UserResult,
  type RefreshTokenResult,
  type UserInfoResult,
  getLogin,
  refreshTokenApi
} from "@/api/user";
import { useMultiTagsStoreHook } from "./multiTags";
import { type DataInfo, setToken, removeToken, userKey } from "@/utils/auth";
import { checkJob, updateJobById } from "@/api/system";


export const useJobStore = defineStore({
  id: "pure-job",
  actions: {
    /** 查询该部门是否存在该职位 存在就不添加，不存在就添加*/
    async checkByNameAndApartmentId(data) {
      return new Promise<UserInfoResult>((resolve, reject) => {
        checkJob(data)
          .then(data => {
            resolve(data);
          })
          .catch(error => {
            reject(error);
          });
      });
    },
    async updateJobById(data) { //处理异步
      console.log(data, 'updateJobById')
      return new Promise<UserInfoResult>((resolve, reject) => {
        updateJobById(data)
          .then(data => {
            console.log(data, 'data')
            resolve(data);
          })
          .catch(error => {
            reject(error);
          });
      });
    },

  }
});

export function useJobStoreHook() {
  return useJobStore(store);
}
