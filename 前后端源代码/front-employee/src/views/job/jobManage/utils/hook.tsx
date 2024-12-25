import dayjs from "dayjs";
import sendEmail from "../sendEmail.vue";
import { handleTree } from "@/utils/tree";
import { message } from "@/utils/message";
import { getJobList, getApartmentList, deleteJob, getSendEmail, getSendEmail2 } from "@/api/system";
import { usePublicHooks } from "../../hooks";
import { addDialog } from "@/components/ReDialog";
import { reactive, ref, onMounted, h, toRaw } from "vue";
import type { FormItemProps } from "../utils/types";
import { cloneDeep, isAllEmpty, deviceDetection } from "@pureadmin/utils";
import { useJobStoreHook } from "@/store/modules/job";
import type { PaginationProps } from "@pureadmin/table";
import axios from "./axiosNew"
import { http } from "@/utils/http";

import editForm from "../form.vue";


export function useDept() {


  const form = reactive({
    apartmentId: null,
    name: "",
  });


  const pagination = reactive<PaginationProps>({
    total: 0,
    pageSize: 8,
    currentPage: 1,
    background: true
  });
  const formRef = ref();
  const dataList = ref();
  const dataApartmentList = ref();
  const loading = ref(true);
  const { tagStyle } = usePublicHooks();

  const columns: TableColumnList = [
    {
      label: "序号",
      prop: "id",
      width: 100,
    },
    {
      label: "部门名称",
      prop: "apartment.department",
      minWidth: 100
    },
    {
      label: "岗位名称",
      prop: "name",
      minWidth: 120
    },
    {
      label: "招聘人数",
      prop: "maxnum",
      minWidth: 120,
    },
    {
      label: "已招聘人数",
      prop: "available",
      minWidth: 90,
    },

    {
      label: "操作",
      fixed: "right",
      width: 210,
      slot: "operation"
    }
  ];



  function handleSizeChange(val: number) {
    onSearch();
    console.log(`${val} items per page`);
  }

  function handleCurrentChange(val: number) {
    onSearch()
    console.log(`current page: ${val}`);
  }

  function handleSelectionChange(val) {
    onSearch();
    console.log("handleSelectionChange", val);
  }
  function resetForm(formEl) {
    if (!formEl) return;
    console.log(formEl)
    form.name = ""
    form.apartmentId = null
    formEl.resetFields();
    onSearch();
  }

  async function onSearch() {
    // 把数据传给后端 做分页查询
    const formAndpagination = reactive(
      {
        name: form.name,
        apartmentId: form.apartmentId,
        total: pagination.total,
        pageSize: pagination.pageSize,
        currentPage: pagination.currentPage,
        background: pagination.background
      }
    )
    loading.value = true;

    const { data, total, pageSize, currentPage } = await getJobList(toRaw(formAndpagination)); // 这里是返回一维数组结构，前端自行处理成树结构，返回格式要求：唯一id加父节点parentId，parentId取父节点id
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


  function openDialog(title = "新增", row?: FormItemProps) {
    console.log(row)
    addDialog({
      title: `${title}岗位`,
      props: {
        formInline: {
          higherDeptOptions: formatHigherDeptOptions(cloneDeep(dataList.value)),
          higherApartmentOptions: formatHigherDeptOptions(cloneDeep(dataApartmentList.value)),
          maxnum: row?.maxnum ?? "",
          jobName: row?.name ?? "",
          options: row?.apartmentId ?? "",
          available: row?.available ?? "",
          id: row?.id ?? "",
        }
      },
      width: "40%",
      draggable: true,
      fullscreen: deviceDetection(),
      fullscreenIcon: true, //index.vue中是否显示header
      closeOnClickModal: false,
      contentRenderer: () => h(editForm, { ref: formRef }),//ref将formRef绑定到了editForm组件


      beforeSure: (done, { options }) => {
        console.log(formRef.value)
        const FormRef = formRef.value.getRef();//只要是为了调用validate
        const curData = options.props.formInline as FormItemProps;//表单信息
        function chores() {
          message(`您${title}了岗位，名称为${curData.jobName}的这条数据`, {
            type: "success"
          });
          done();
          onSearch();//刷新表格
        }
        // validate 方法是el-form自带的,这个实例上会有一个 validate 方法，用于触发表单的验证。
        FormRef.validate(valid => {
          console.log(valid)
          if (valid) {  //表单信息是否符合 验证规则
            // 表单规则校验通过
            if (title === "新增") {
              console.log(curData)
              // 实际开发先调用新增接口，再进行下面操作
              useJobStoreHook().checkByNameAndApartmentId({ apartmentId: curData.options, name: curData.jobName, maxnum: curData.maxnum })
                .then(res => {
                  if (res) {

                    chores();
                  } else {
                    message(`该岗位‘${curData.jobName}’在该部门已经存在`, {
                      type: "error"
                    });
                  }
                })

            } else {
              // 调用修改接口
              useJobStoreHook().updateJobById({
                apartmentId: curData.options,
                name: curData.jobName,
                maxnum: curData.maxnum,
                id: curData.id,
                available: curData.available
              })
                .then(res => {
                  if (res) {

                    chores();
                  } else {
                    message(`‘招聘人数’不能小于 ‘剩余可招聘人数’，或该职位在还部门已经存在`, {
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

  // 发送邮件 弹框
  function openEmailDialog(title = "发送邮件", row?: FormItemProps) {
    addDialog({
      title: `${title}`,
      props: {
        formInline: {
          username: "",
          telephone: "",
          createdDate: new Date(),
          textContext: "",
          fileList: "",
          jobName: row?.name ?? "",
          options: row?.apartmentId ?? "",
        }
      },
      width: "40%",
      draggable: true,
      fullscreen: deviceDetection(),
      fullscreenIcon: true,
      closeOnClickModal: false,

      contentRenderer: () => h(sendEmail, { ref: formRef }),


      beforeSure: (done, { options }) => {
        const FormRef = formRef.value.getRef();
        const curData = options.props.formInline as FormItemProps;
        function chores() {
          message(`邮件发送成功！`, {
            type: "success"
          });
          done(); // 关闭弹框
          onSearch(); // 刷新表格数据
        }
        FormRef.validate(valid => {
          if (valid) {
            console.log(curData)
            console.log(row)
            // 表单规则校验通过
            const file = curData.fileList.map(file => ({ raw: file.raw }))
            console.log(file[0].raw)

            let formdata = new FormData()
            formdata.append("id", curData.options);
            formdata.append("username", curData.username);
            formdata.append("telephone", curData.telephone);
            formdata.append("createdDate", new Date());
            formdata.append("textContext", curData.textContext);
            formdata.append("jobName", curData.jobName);
            formdata.append("file", file[0].raw);
            console.log(formdata)
            let res = axios.post('sendEmail/sendResume', formdata, {
              headers: { 'Content-type': 'multipart/form-data' }
            }

            )
            console.log(formdata)
            chores()
          }
        });
      }
    });
  }



  async function handleDelete(row) {
    console.log(row, 'row')
    await deleteJob(row.id);
    message(`您删除了${row.apartment.department}的${row.name}这条数据`, { type: "success" });
    onSearch();
  }

  onMounted(() => {
    onSearch();
  });

  return {
    form,
    loading,
    columns,
    dataApartmentList,
    dataList,
    /** 搜索 */
    onSearch,
    /** 重置 */
    resetForm,
    /** 新增、修改部门 */
    openDialog,
    openEmailDialog,
    /** 删除部门 */
    handleDelete,
    // handleDatabase,
    pagination,
    handleSizeChange,
    handleCurrentChange,
    handleSelectionChange
  };
}
