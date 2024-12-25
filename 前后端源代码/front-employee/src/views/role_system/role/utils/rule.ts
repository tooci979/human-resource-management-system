import { reactive } from "vue";
import type { FormRules } from "element-plus";
import { isPhone, isEmail } from "@pureadmin/utils";
/** 自定义表单规则校验 */
export const formRules = reactive(<FormRules>{
  username: [{ required: true, message: "用户名为必填项", trigger: "blur" }],
  code: [{ required: true, message: "角色标识为必选项", trigger: "blur" }],
  state: [{ required: true, message: "状态为必选项", trigger: "blur" }],
  remark: [{ required: true, message: "备注必选项", trigger: "blur" }],
  password: [{ required: true, message: "密码为必选项", trigger: "blur" }],
  
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
});
