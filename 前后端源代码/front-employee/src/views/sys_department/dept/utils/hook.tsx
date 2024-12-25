import dayjs from "dayjs";
import editForm from "../form.vue";
import { handleTree } from "@/utils/tree";
import { message } from "@/utils/message";
import { getDeptList, getApartmentList, insertApartment, updateApartment } from "@/api/system";
import { usePublicHooks } from "../../hooks";
import { addDialog } from "@/components/ReDialog";
import { reactive, ref, onMounted, h } from "vue";
import type { FormItemProps } from "../utils/types";
import { cloneDeep, isAllEmpty, deviceDetection } from "@pureadmin/utils";
import { useApartmentStoreHook } from "@/store/modules/apartment";

import {
  hideTextAtIndex,
} from "@pureadmin/utils";
export function useDept() {
  const form = reactive({
    department: "",
    principal: ""
  });

  const formRef = ref();
  const dataList = ref();
  const loading = ref(true);
  const dataApartmentList = ref();
  const { tagStyle } = usePublicHooks();

  const columns: TableColumnList = [
    {
      label: "序号",
      prop: "id",
      width: 100,
    },
    {
      label: "部门名称",
      prop: "department",
      minWidth: 100
    },
    {
      label: "负责人",
      prop: "principal",
      minWidth: 120
    },
    {
      label: "性别",
      prop: "sex",
      minWidth: 120,
      cellRenderer: ({ row, props }) => (
        <el-tag
          size={props.size}
          type={row.sex === 2 ? "danger" : null}
          effect="plain"
        >
          {row.sex === 2 ? "女" : "男"}
        </el-tag>
      )
    },

    {
      label: "手机号码",
      prop: "telephone",
      minWidth: 90,
      formatter: ({ telephone }) => hideTextAtIndex(telephone, { start: 3, end: 6 })
    },
    {
      label: "创建时间",
      minWidth: 160,
      prop: "createDate",
      formatter: ({ createDate }) =>
        dayjs(createDate).format("YYYY-MM-DD HH:mm:ss")
    },
    {
      label: "备注",
      prop: "remark",
      minWidth: 150
    },
    {
      label: "操作",
      fixed: "right",
      width: 210,
      slot: "operation"
    }
  ];

  function handleSelectionChange(val) {
    console.log("handleSelectionChange", val);
  }

  function resetForm(formEl) {
    if (!formEl) return;
    console.log(formEl)
    form.principal = ""
    formEl.resetFields();
    onSearch();
  }

  async function onSearch() {
    loading.value = true;
    console.log(form)
    const { data } = await getDeptList(form); // 这里是返回一维数组结构，前端自行处理成树结构，返回格式要求：唯一id加父节点parentId，parentId取父节点id
    const { data: dataApart } = await getApartmentList();//获取有几个部门

    let newdataApart = dataApart
    let newData = data;

    dataList.value = handleTree(newData); // 处理成树结构
    dataApartmentList.value = handleTree(newdataApart); // 处理成树结构
    setTimeout(() => {
      loading.value = false;
    }, 500);
  }

  function formatHigherDeptOptions(treeList) {
    // 根据返回数据的status字段值判断追加是否禁用disabled字段，返回处理后的树结构，用于上级部门级联选择器的展示（实际开发中也是如此，不可能前端需要的每个字段后端都会返回，这时需要前端自行根据后端返回的某些字段做逻辑处理）
    if (!treeList || !treeList.length) return;
    const newTreeList = [];
    for (let i = 0; i < treeList.length; i++) {
      treeList[i].disabled = treeList[i].status === 0 ? true : false;
      formatHigherDeptOptions(treeList[i].children);
      newTreeList.push(treeList[i]);
    }
    return newTreeList;
  }

  function openDialog(title = "新增", row?: FormItemProps) {
    addDialog({
      title: `${title}部门`,
      props: {
        formInline: {
          higherDeptOptions: formatHigherDeptOptions(cloneDeep(dataList.value)),
          higherApartmentOptions: formatHigherDeptOptions(cloneDeep(dataApartmentList.value)),
          //若title是修改的话，会讲起传过来的数据给到对应的“键”；row=datalist
          id: row?.id ?? "",
          department: row?.department ?? "",
          principal: row?.principal ?? "",
          telephone: row?.telephone ?? "",
          createdDate: row?.createdDate ?? "",
          sex: row?.sex ?? "",
          remark: row?.remark ?? "",
        }
      },
      width: "40%",
      draggable: true,
      fullscreen: deviceDetection(),
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () => h(editForm, { ref: formRef }),
      beforeSure: (done, { options }) => {
        const FormRef = formRef.value.getRef();
        const curData = options.props.formInline as FormItemProps;
        function chores() {
          message(`您${title}了部门名称为'${curData.department}'的这条数据`, {
            type: "success"
          });

          done(); // 关闭弹框
          onSearch(); // 刷新表格数据
        }
        FormRef.validate(valid => {
          if (valid) {
            console.log("curData", curData);
            // 表单规则校验通过
            if (title === "新增") {

              useApartmentStoreHook().checkDeptIsExist(curData)
                .then((res) => {
                  if (res) {
                    async function a() {
                      await insertApartment({
                        id: null,
                        department: curData.department,
                        principal: curData.principal,
                        sex: curData.sex,
                        telephone: curData.telephone,
                        remark: curData.remark,
                        createdDate: new Date(),
                      })
                      console.log(123)
                      console.log(123)
                      console.log(123)
                      console.log(123)
                      console.log(123)
                      console.log(123)
                      console.log(123)
                      chores();
                    }
                    a();
                  } else {
                    message(`该部门‘${curData.department}’已存在`, {
                      type: "error"
                    });
                  }
                })

            } else {

              async function a() {
                await updateApartment({
                  id: curData.id,
                  department: curData.department,
                  principal: curData.principal,
                  sex: curData.sex,
                  telephone: curData.telephone,
                  remark: curData.remark,
                  createdDate: new Date(curData.createDate),
                })
                chores();
              }
              a();
            }
          }
        });
      }
    });
  }

  function handleDelete(row) {
    console.log(row)
    useApartmentStoreHook().checkApartmentIsNull(row)
      .then((res) => {
        if (res) {
          onSearch();
          message(`您删除了部门名称为${row.department}的这条数据`, { type: "success" });
        } else {
          message(`该部门存在 招聘职位，无法删除`, {
            type: "error"
          });
        }
      })
  }

  onMounted(() => {
    onSearch();
  });

  return {
    form,
    loading,
    columns,
    dataList,
    /** 搜索 */
    onSearch,
    /** 重置 */
    resetForm,
    /** 新增、修改部门 */
    openDialog,
    /** 删除部门 */
    handleDelete,
    handleSelectionChange
  };
}
