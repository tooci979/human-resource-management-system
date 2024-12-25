import { reactive } from "vue";
import type { FormRules } from "element-plus";
import { isPhone, isEmail } from "@pureadmin/utils";

/** 自定义表单规则校验 */
export const formRules = reactive(<FormRules>{
  fileList: [{ required: true, message: '附件不能为空', trigger: "blur" }],
  textContext: [{ required: true, message: '信件内容不能为空', trigger: "blur" }],
  name: [{ required: true, message: "员工姓名为必填项", trigger: "blur" }],
  options: [{ required: true, message: "所属部门为必选项", trigger: "blur" }],
  jobName: [{ required: true, message: "新增岗位为必选项", trigger: "blur" }],
  username: [{ required: true, message: "员工姓名为必填项", trigger: "blur" }],

  telephone: [
    {
      validator: (rule, value, callback) => {
        if (value === "") {
          callback();
        } else if (!isPhone(value)) {
          callback(new Error("请输入正确的手机号码格式"));
        } else {
          callback();
        }
      },
      trigger: "blur"
      // trigger: "click" // 如果想在点击确定按钮时触发这个校验，trigger 设置成 click 即可
    }
  ],
  email: [
    {
      validator: (rule, value, callback) => {
        if (value === "") {
          callback();
        } else if (!isEmail(value)) {
          callback(new Error("请输入正确的邮箱格式"));
        } else {
          callback();
        }
      },
      trigger: "blur"
    }
  ]
});
