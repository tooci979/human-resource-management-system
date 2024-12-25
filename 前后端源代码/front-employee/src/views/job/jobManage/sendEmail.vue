<script setup lang="ts">
import { ref ,reactive } from "vue";
import ReCol from "@/components/ReCol";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";
import { usePublicHooks } from "../hooks";
import { formUpload } from "@/api/mock";
import { message } from "@/utils/message";
import { createFormData } from "@pureadmin/utils";
import UploadIcon from "@iconify-icons/ri/upload-2-line";



const uploadRef = ref();


const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
    username:"",
    telephone:"",
    createdDate:new Date(),
    textContext:"",
    fileList:"",
    jobName:"",
    options:""
  }),
});


const formRef = ref();
const ruleFormRef = ref();
const { switchStyle } = usePublicHooks();
const newFormInline = ref(props.formInline);
function getRef() {
  return ruleFormRef.value;
}
const updateFile=(v)=>{
  console.log(v)
}

const resetForm = formEl => {
  if (!formEl) return;
  formEl.resetFields();
};

//附件超出限制的提示
const uploadExceed = ()=>{
   message(`已经超出最大文件数！`, {
            type: "warning"
          });
}


defineExpose({ getRef });
</script>

<template>

  <el-form
  :rules="formRules"
  ref="ruleFormRef"
  :model="newFormInline"
   label-width="82px"
  >
  <el-form-item label="部门编号" >
      <el-input
        v-model="newFormInline.options"
        clearable
        disabled
      />
  </el-form-item>
  <el-form-item label="投递岗位" >
      <el-input
        v-model="newFormInline.jobName"
        clearable
        disabled
      />
  </el-form-item>


  <el-form-item label="姓名" prop="username">
      <el-input
        v-model="newFormInline.username"
        clearable
        placeholder="请输入你的姓名"
      />
  </el-form-item>
    <el-form-item label="电话" prop="telephone">
      <el-input
        v-model="newFormInline.telephone"
        clearable
        placeholder="请输入你的电话"
      />
    </el-form-item>
     <el-form-item label="发送日期">
      <el-input
        v-model="newFormInline.createdDate"
        clearable
        disabled
         placeholder="默认为当前发送时间"
      />
    </el-form-item>
            <el-form-item label="信件内容" prop="textContext">
          <el-input
            v-model="newFormInline.textContext"
            placeholder="请输入信件内容"
            type="textarea"
          />
        </el-form-item>
  <el-form-item label="附件"  prop="fileList">
      <el-upload
        
        v-model:file-list="newFormInline.fileList"
        class="upload-demo"
        action="#"
        multiple
        :http-request="updateFile"
        enctype="multipart/form-data"
        :limit="1"
        :on-exceed="uploadExceed"
      >
        <el-button type="primary">Click to upload</el-button>
        <template #tip>
          <div class="el-upload__tip">
            jpg/png files with a size less than 500KB.
          </div>
        </template>
      </el-upload>
  </el-form-item>
  
  </el-form>
</template>
