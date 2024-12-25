import { reactive } from "vue";
import type { FormRules } from "element-plus";
import { isPhone, isEmail } from "@pureadmin/utils";
import { department } from "@/router/enums";

/** 自定义表单规则校验 */
export const formRules = reactive(<FormRules>{
  department: [{ required: true, message: "部门名称为必填项", trigger: "blur" }],
  principal: [{ required: true, message: "部门负责人为必选项", trigger: "blur" }],
  sex: [{ required: true, message: "性别为必选项", trigger: "blur" }],
  remark: [{ required: true, message: "备注必选项", trigger: "blur" }],
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
