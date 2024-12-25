import { department } from "@/router/enums";
import { defineFakeRoute } from "vite-plugin-fake-server/client";

export default defineFakeRoute([
  {
    url: "/get-card-list",
    method: "post",
    response: () => {
      return {
        success: true,
        data: {
          list: [
            { id: 1, department: "软件工程", principal: "李四", phone: "15678945612", createDate: new Date(), remark: "32132132" },

          ]
        }
      };
    }
  }
]);


