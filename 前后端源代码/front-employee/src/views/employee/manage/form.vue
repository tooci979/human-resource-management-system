<script setup lang="ts">
import { ref,watch } from "vue";
import ReCol from "@/components/ReCol";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";
import { usePublicHooks } from "../hooks";
import { useDept } from "./utils/hook";
import { getEmployeeList, getApartmentList, getAllJobByApartmetId } from "@/api/system";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
    higherDeptOptions: [],
    higherApartmentOptions:[],
    higherJobOptions:[],
    number:null,
    name:"",
    gender:null,
    telephone:"",
    email:"",
    apartmentOptions:0,
    state:null,
    createDate:null,
    employeeJobId:null,
    id:0
  })
});



const sexOptions = [
  {
    value: 0,
    label: "男"
  },
  {
    value: 1,
    label: "女"
  }
];
const ruleFormRef = ref();
const { switchStyle } = usePublicHooks();
const newFormInline = ref(props.formInline);

const {
selectAllJobByApartmentId
} = useDept();

watch(()=>newFormInline.value.apartmentOptions,async (nv,ov) => {
 console.log(nv,ov)
//  countStore.apartmentOptions=newFormInline.value.apartmentOptions
// 下拉框  根据部门显示  ，岗位显示对应部门的职业
  newFormInline.value.higherJobOptions=await selectAllJobByApartmentId(nv)
  // console.log(countStore.apartmentOptions,'countStore.apartmentOptions')
},{deep:true,immediate:true})

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
        <el-form-item label="员工编号">
          <el-input
            v-model="newFormInline.number"
            clearable
            placeholder="自增编号"
            :disabled="true"
          />
        </el-form-item>
      </re-col>
      <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="员工姓名" prop="name">
          <el-input
            v-model="newFormInline.name"
            clearable
            placeholder="请输入用户姓名"
          />
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
        <el-form-item label="邮箱" prop="email">
          <el-input
            v-model="newFormInline.email"
            clearable
            placeholder="请输入邮箱"
          />
        </el-form-item>
      </re-col>
      <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="员工性别" prop="gender">
          <el-select
            v-model="newFormInline.gender"
            placeholder="请选择员工性别"
            class="w-full"
            clearable
          >
            <el-option
              v-for="(item, index) in sexOptions"
              :key="index"
              :label="item.label"
              :value="item.label"
            />
          </el-select>
        </el-form-item>
      </re-col>
       <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="创建日期" >
          <el-input
            v-model="newFormInline.createDate"
            clearable
            placeholder="为当前日期"
            :disabled="true"
          />
        </el-form-item>
      </re-col>
      <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="归属部门" prop="apartmentOptions">
          <el-cascader
            v-model="newFormInline.apartmentOptions"
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
            placeholder="请选择归属部门"
          >
            <template #default="{ node, data }">
              <span>{{ data.department }}</span>
              <span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
            </template>
          </el-cascader>
        </el-form-item>
      </re-col>
        <re-col :value="12" :xs="24" :sm="24">
        <el-form-item label="归属职位" prop="employeeJobId">
          <el-cascader
            v-model="newFormInline.employeeJobId"
            class="w-full"
            :options="newFormInline.higherJobOptions"
            :props="{
              value: 'id',
              label: 'name',
              emitPath: false,
              checkStrictly: true
            }"
            clearable
            filterable
            placeholder="请选择归属职位"
          >
            <template #default="{ node, data }">
              <span>{{ data.name }}</span>
              <span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
            </template>
          </el-cascader>
        </el-form-item>
      </re-col>
      <re-col :value="12" :xs="24" :sm="24">
      <el-form-item label="状态：" prop="state">
        <el-select
          v-model="newFormInline.state"
          placeholder="请选择状态"
          clearable
        >
          <el-option label="正式员工" :value="1" />
          <el-option label="非正式" :value="2" />
        </el-select>
      </el-form-item>
      </re-col>
     
    </el-row>
  </el-form>
</template>
