import dayjs from "dayjs";
import editForm from "../form.vue";
import { handleTree } from "@/utils/tree";
import { message } from "@/utils/message";
import { getEmployeeList, getApartmentList, getAllJobByApartmetId, UpdateEmployee, addEmployee, deleteEmployee } from "@/api/system";
import { usePublicHooks } from "../../hooks";
import { addDialog } from "@/components/ReDialog";
import { reactive, ref, onMounted, h, toRaw, watch } from "vue";
import type { FormItemProps } from "../utils/types";
import { cloneDeep, isAllEmpty, deviceDetection } from "@pureadmin/utils";
import type { PaginationProps } from "@pureadmin/table";
import { useEmployeeStoreHook } from "@/store/modules/employee";

import {
  hideTextAtIndex,
} from "@pureadmin/utils";
import { da } from "@faker-js/faker";
import { number } from "echarts";
import { func } from "vue-types";

export function useDept() {
  const form = reactive({
    employeeName: "",
    jobName: "",
    apartmentId: null,
    state: null
  });
  const formRef = ref();
  const dataList = ref([]);
  const loading = ref(true);
  const { tagStyle } = usePublicHooks();
  const dataApartmentList = ref();
  const JobNameList = ref();


  const pagination = reactive<PaginationProps>({
    total: 0,
    pageSize: 8,
    currentPage: 1,
    background: true
  });

  const columns: TableColumnList = [
    {
      label: "ID",
      prop: "id",
      width: 70,
    },
    {
      label: "编号",
      prop: "number",
      minWidth: 100
    },
    {
      label: "员工",
      prop: "name",
      width: 100,
    },
    {
      label: "性别",
      prop: "gender",
      minWidth: 70,
      cellRenderer: ({ row, props }) => (
        <el-tag
          size={props.size}
          type={row.gender === '女' ? "danger" : null}
          effect="plain"
        >
          {row.gender === '女' ? "女" : "男"}
        </el-tag>
      )
    },
    {
      label: "部门",
      prop: "job.apartment.department",
      minWidth: 100
    },
    {
      label: "岗位",
      prop: "job.name",
      minWidth: 70
    },
    {
      label: "电话号码",
      prop: "telephone",
      minWidth: 120,
      formatter: ({ telephone }) => hideTextAtIndex(telephone, { start: 3, end: 6 })
    },
    {
      label: "邮箱",
      prop: "email",
      minWidth: 120
    },
    {
      label: "状态",
      prop: "state",
      minWidth: 100,
      cellRenderer: ({ row, props }) => (
        <el-tag size={props.size} style={tagStyle.value(row.state)}>
          {row.state === 1 ? "正式员工" : "非正式"}
        </el-tag>
      )
    },
    {
      label: "创建时间",
      minWidth: 160,
      prop: "createDate",
      formatter: ({ createDate }) =>
        dayjs(createDate).format("YYYY-MM-DD HH:mm:ss")
    },
    {
      label: "操作",
      fixed: "right",
      width: 180,
      slot: "operation"
    }
  ];

  function handleSelectionChange(val) {
    console.log("handleSelectionChange", val);
  }

  function resetForm(formEl) {
    if (!formEl) return;
    formEl.resetFields();
    form.employeeName = "",
      form.jobName = "",
      form.apartmentId = null,
      form.state = null
    onSearch();
  }

  function handleCurrentChange(val: number) {
    onSearch()
    console.log(`current page: ${val}`);
  }
  function handleSizeChange(val: number) {
    onSearch();
    console.log(`${val} items per page`);
  }

  async function onSearch() {

    loading.value = true;

    const formAndpagination = reactive(
      {
        employeeName: form.employeeName,
        jobName: form.jobName,
        apartmentId: form.apartmentId,
        state: form.state,

        total: pagination.total,
        pageSize: pagination.pageSize,
        currentPage: pagination.currentPage,
        background: pagination.background
      }
    )

    const { data, total, pageSize, currentPage } = await getEmployeeList(toRaw(formAndpagination)); // 这里是返回一维数组结构，前端自行处理成树结构，返回格式要求：唯一id加父节点parentId，parentId取父节点id

    const { data: dataApart } = await getApartmentList();//获取有几个部门

    let newdataApart = dataApart
    let newData = data;
    // 分页
    pagination.total = total;
    pagination.pageSize = pageSize;
    pagination.currentPage = currentPage;


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


  async function selectAllJobByApartmentId(id) {
    if (id == undefined) {
      const a = ref([])
      return a.value;
    }
    const { data } = await getAllJobByApartmetId(id);
    return data
  }
  async function openDialog(title = "新增", row?: FormItemProps) {
    console.log(row, 'row')
    addDialog({
      title: `${title}职员`,
      props: {
        formInline: {
          // index.vue发出修改申请时 ， row不为空，而是将当前行的数据传过来；这里将当前行的数据返回到form表单上
          higherDeptOptions: formatHigherDeptOptions(cloneDeep(dataList.value)),
          higherApartmentOptions: formatHigherDeptOptions(cloneDeep(dataApartmentList.value)),
          higherJobOptions: await selectAllJobByApartmentId(row?.job.apartmentId ?? ""),
          number: row?.number ?? "",
          name: row?.name ?? "",
          gender: row?.gender ?? "",
          telephone: row?.telephone ?? "",
          email: row?.email ?? "",
          apartmentOptions: row?.job.apartmentId ?? "",
          state: row?.state ?? "",
          createDate: row?.createDate ?? "",
          employeeJobId: row?.job.id ?? "",
          id: row?.id ?? "",
        }
      },

      width: "40%",
      draggable: true,
      fullscreen: deviceDetection(),
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () => h(editForm, { ref: formRef }),

      beforeSure: async (done, { options }) => {
        const FormRef = formRef.value.getRef();
        const curData = options.props.formInline as FormItemProps;
        function chores() {
          message(`您${title}了职员名称为${curData.name}的这条数据`, {
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
              // 实际开发先调用新增接口，再进行下面操作
              // checkAvaliabelIsNotFull({ apartmentId: curData.apartmentOptions, employeeJobId: curData.employeeJobId });
              useEmployeeStoreHook().checkEmployeeIsFull({ apartmentId: curData.apartmentOptions, employeeJobId: curData.employeeJobId })
                .then(res => {
                  if (res) {
                    console.log("执行了1")
                    addEmployee({
                      name: curData.name,
                      jobId: curData.employeeJobId,
                      gender: curData.gender,
                      telephone: curData.telephone,
                      state: curData.state,
                      email: curData.email,
                    })
                    chores();
                  } else {
                    message(`该岗位‘${curData.name}’已满`, {
                      type: "error"
                    });
                  }
                })

            } else {
              console.log("curData", curData);
              //修改时也要判断 该部门的该岗位 是否已满
              useEmployeeStoreHook().checkEmployeeIsFullWithUpdate({ apartmentId: curData.apartmentOptions, employeeJobId: curData.employeeJobId, employeeId: curData.id })
                .then((res) => {
                  if (res) {
                    console.log("执行了2")
                    async function a() {
                      await UpdateEmployee({
                        id: curData.id,
                        number: curData.number,
                        name: curData.name,
                        gender: curData.gender,
                        jobId: curData.employeeJobId,
                        telephone: curData.telephone,
                        email: curData.email,
                        createDate: new Date(curData.createDate),
                        state: curData.state,
                      })
                      chores();
                    }
                    a();

                  } else {
                    message(`该岗位‘${curData.name}’已满`, {
                      type: "error"
                    });
                  }
                })
            }
          }
        });
      }
    });
  }

  async function handleDelete(row) {
    console.log(row, 'row')
    await deleteEmployee(row.id);
    message(`您删除了员工为${row.name}的这条数据`, { type: "success" });
    onSearch();
  }
  // 在组件挂载到 DOM 后立即执行
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
    handleSelectionChange,
    handleSizeChange,
    handleCurrentChange,
    pagination,
    dataApartmentList,
    selectAllJobByApartmentId
  };
}
