<script setup lang="ts">
import { ref } from "vue";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
    username: "",
    code: "",
    state:"",
    remark: "",
    password:"",
    telephone:"",
    id:0
  })
});
const userOptions = [
  {
    value: 1,
    label: "超级管理员"
  },
  {
    value: 2,
    label: "普通用户"
  }
];
const ruleFormRef = ref();
const newFormInline = ref(props.formInline);

function getRef() {
  return ruleFormRef.value;
}

defineExpose({ getRef });
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="newFormInline"
    :rules="formRules"
    label-width="82px"
  >
    <el-form-item label="用户名称" prop="username">
      <el-input
        v-model="newFormInline.username"
        clearable
        placeholder="请输入用户名称"
      />
    </el-form-item>
   <re-col :value="12" :xs="24" :sm="24" >
        <el-form-item label="角色标识" prop="code">
          <el-select
            v-model="newFormInline.code"
            placeholder="请输入角色标识"
            class="w-full"
            clearable
          >
            <el-option
              v-for="(item, index) in userOptions"
              :key="index"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
      </re-col>

       <el-form-item label="用户密码"  prop="password">
      <el-input
        v-model="newFormInline.password"
        clearable
        type="password"
        placeholder="请输入用户密码"
      />
    </el-form-item>
        <el-form-item label="用户状态"  prop="state">
          <el-switch
            v-model="newFormInline.state"
            inline-prompt
            :active-value="1"
            :inactive-value="0"
            active-text="启用"
            inactive-text="停用"
            :style="switchStyle"
          />
        </el-form-item>
       <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="手机号" prop="telephone">
          <el-input
            v-model="newFormInline.telephone"
            clearable
            placeholder="请输入手机号"
          />
        </el-form-item>
      </re-col>
        <el-form-item label="创建日期" >
          <el-input
            v-model="newFormInline.createdDate"
            clearable
            placeholder="为当前日期"
            :disabled="true"
          />
        </el-form-item>
    <el-form-item label="备注" prop="remark">
      <el-input
        v-model="newFormInline.remark"
        placeholder="请输入备注信息"
        type="textarea"
      />
    </el-form-item>
  </el-form>
</template>
