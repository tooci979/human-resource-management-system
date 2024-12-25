<script setup lang="ts">
import { ref } from "vue";
import ReCol from "@/components/ReCol";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";
import { usePublicHooks } from "../hooks";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
    higherDeptOptions: [],
    higherApartmentOptions:[],
    id: 0,
    department: "",
    principal: "",
    telephone: "",
    createdDate: "",
    sex: "",
    remark: ""
  })
});
const sexOptions = [
  {
    value: 1,
    label: "男"
  },
  {
    value: 2,
    label: "女"
  }
];
const ruleFormRef = ref();
const { switchStyle } = usePublicHooks();
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
    <el-row :gutter="30">

       <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="部门名称" prop="department">
          <el-input
            v-model="newFormInline.department"
            clearable
            placeholder="请输入部门名称"
          />
        </el-form-item>
      </re-col>
      <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="负责人" prop="principal">
          <el-input
            v-model="newFormInline.principal"
            clearable
            placeholder="请输入部门负责人"
          />
        </el-form-item>
      </re-col>
        <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="性别" prop="sex" >
          <el-select
            v-model="newFormInline.sex"
            placeholder="请选择性别"
            class="w-full"
            clearable
          >
            <el-option
              v-for="(item, index) in sexOptions"
              :key="index"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
      </re-col>
      <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="手机号" prop="telephone">
          <el-input
            v-model="newFormInline.telephone"
            clearable
            placeholder="请输入手机号"
          />
        </el-form-item>
      </re-col>
      <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="创建日期" >
          <el-input
            v-model="newFormInline.createdDate"
            clearable
            placeholder="为当前日期"
            :disabled="true"
          />
        </el-form-item>
      </re-col>
      <re-col>
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="newFormInline.remark"
            placeholder="请输入备注信息"
            type="textarea"
          />
        </el-form-item>
      </re-col>
    </el-row>
  </el-form>
</template>
