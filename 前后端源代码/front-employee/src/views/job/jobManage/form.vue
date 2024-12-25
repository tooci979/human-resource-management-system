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
    maxnum:1,
    jobName:"",
    options:0,
    available:0,
    id:0
  })
});

const ruleFormRef = ref();
const { switchStyle } = usePublicHooks();
const newFormInline = ref(props.formInline);
console.log(newFormInline)
function getRef() {
  console.log(ruleFormRef.value)
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
      <re-col :value="12" :xs="24" :sm="24" >
      <el-form-item label="所属部门" prop="options">
        <!--  options: row?.apartmentId ?? "", -->
          <el-cascader
            v-model="newFormInline.options"
            class="w-full"
            :options="newFormInline.higherApartmentOptions"
            :props="{
              value: 'id',
              label: 'department',
              emitPath: false,
              checkStrictly: true
            }"
            clearable
            filterable
            placeholder="请选择要添加的岗位"
          >
            <template #default="{ node, data }">
              <span>{{ data.department }}</span>
              <span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
            </template>
          </el-cascader>
        </el-form-item>
      </re-col>
      <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="岗位名称" prop="jobName">
          <el-input
            v-model="newFormInline.jobName"
            clearable
            placeholder="请输入要添加的岗位"
          />
        </el-form-item>
      </re-col>
      
      <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="招聘数量">
          <el-input-number
            v-model="newFormInline.maxnum"
            class="!w-full"
            :min="1"
            :max="9999"
            controls-position="right"
          />
        </el-form-item>
      </re-col>
    </el-row>
  </el-form>
</template>
