import dayjs from "dayjs";
import editForm from "../form.vue";
import { handleTree } from "@/utils/tree";
import { message } from "@/utils/message";
import { ElMessageBox } from "element-plus";
import { usePublicHooks } from "../../hooks";
import { transformI18n } from "@/plugins/i18n";
import { addDialog } from "@/components/ReDialog";
import type { FormItemProps } from "../utils/types";
import type { PaginationProps } from "@pureadmin/table";
import { getKeyList, deviceDetection } from "@pureadmin/utils";
import { getRoleList, getRoleMenu, getRoleMenuIds, insertUser, updateState, deleteUser } from "@/api/system";
import { type Ref, reactive, ref, onMounted, h, toRaw, watch } from "vue";

import { useRoleUsertoreHook } from "@/store/modules/role_system";

export function useRole(treeRef: Ref) {
  const form = reactive({
    name: "",
    code: "",
    status: "",
    password: "",
    telephone: "",
    id: 0
  });
  const curRow = ref();
  const formRef = ref();
  const dataList = ref([]);
  const treeIds = ref([]);
  const treeData = ref([]);
  const isShow = ref(false);
  const loading = ref(true);
  const isLinkage = ref(false);
  const treeSearchValue = ref();
  const switchLoadMap = ref({});
  const isExpandAll = ref(false);
  const isSelectAll = ref(false);
  const { switchStyle } = usePublicHooks();
  const { tagStyle } = usePublicHooks();
  const treeProps = {
    value: "id",
    label: "title",
    children: "children"
  };
  const pagination = reactive<PaginationProps>({
    total: 0,
    pageSize: 7,
    currentPage: 1,
    background: true
  });
  const columns: TableColumnList = [
    {
      label: "用户编号",
      prop: "id"
    },
    {
      label: "用户名称",
      prop: "username"
    },
    {
      label: "角色标识",
      prop: "role",
      minWidth: 100,
      cellRenderer: ({ row, props }) => (
        <el-Text size={props.size} >

          {row.role === 1 ? "超级管理员" : "普通用户"}
        </el-Text>
      )
    },

    {
      label: "状态",
      cellRenderer: scope => (
        <el-switch
          size={scope.props.size === "small" ? "small" : "default"}
          loading={switchLoadMap.value[scope.index]?.loading}
          v-model={scope.row.state}
          active-value={1}
          inactive-value={0}
          active-text="已启用"
          inactive-text="已停用"
          inline-prompt
          style={switchStyle.value}
          onChange={() => onChange(scope as any)}
        />

      ),
      minWidth: 90
    },
    {
      label: "创建时间",
      prop: "createDate",
      minWidth: 160,
      formatter: ({ createDate }) =>
        dayjs(createDate).format("YYYY-MM-DD HH:mm:ss")
    },
    {
      label: "备注",
      prop: "remark",
      minWidth: 160,
    },
    {
      label: "操作",
      fixed: "right",
      width: 210,
      slot: "operation"
    }
  ];

  function onChange({ row, index }) {
    ElMessageBox.confirm(
      `确认要<strong>${row.state === 0 ? "停用" : "启用"
      }</strong><strong style='color:var(--el-color-primary)'>${row.username
      }</strong>吗?`,
      "系统提示",
      {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        dangerouslyUseHTMLString: true,
        draggable: true
      }
    )
      .then(() => {
        console.log(row.state, row.id)
        const state = row.state
        const id = row.id
        updateState({
          state, id
        })



        switchLoadMap.value[index] = Object.assign(
          {},
          switchLoadMap.value[index],
          {
            loading: true
          }
        );
        setTimeout(() => {
          switchLoadMap.value[index] = Object.assign(
            {},
            switchLoadMap.value[index],
            {
              loading: false
            }
          );
          message(`已${row.state === 0 ? "停用" : "启用"}${row.username}`, {
            type: "success"
          });
        }, 300);
      })
      .catch(() => {
        row.state === 0 ? (row.state = 1) : (row.state = 0);
        console.log(row.state)
      });
  }

  async function handleDelete(row) {
    await deleteUser(row)//传递对象，在路径上传id
    message(`您删除了用户名称为${row.username}的这条数据`, { type: "success" });
    onSearch();
  }

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

  async function onSearch() {

    const formAndpagination = reactive(
      {
        // 搜索框信息
        name: form.name,
        code: form.code,
        status: form.status,

        total: pagination.total,
        pageSize: pagination.pageSize,
        currentPage: pagination.currentPage,
        background: pagination.background
      }
    )
    loading.value = true;
    const { data, total, pageSize, currentPage } = await getRoleList(toRaw(formAndpagination));
    dataList.value = data;//dataList存储这所有查询的数据

    pagination.total = total;
    pagination.pageSize = pageSize;
    pagination.currentPage = currentPage;

    setTimeout(() => {
      loading.value = false;//加载条
    }, 500);
  }

  const resetForm = formEl => {
    if (!formEl) return;
    formEl.resetFields();
    onSearch();
  };

  function openDialog(title = "新增", row?: FormItemProps) {
    addDialog({
      title: `${title}用户`,
      props: {
        formInline: {
          id: row?.id ?? "",
          username: row?.username ?? "",
          code: row?.role ?? "",
          state: row?.state ?? "",
          password: row?.password ?? "",
          remark: row?.remark ?? "",
          telephone: row?.telephone ?? "",
          createdDate: row?.createdDate ?? ""
        }
      },
      width: "40%",
      draggable: true,
      fullscreen: deviceDetection(),
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () => h(editForm, { ref: formRef }),//打开哪个表单
      beforeSure: (done, { options }) => {   //在表单验证之前调用
        const FormRef = formRef.value.getRef();//获取表单
        const curData = options.props.formInline as FormItemProps;//获取表单数据
        function chores() {
          message(`您${title}了角色名称为${curData.username}的这条数据`, {
            type: "success"
          });
          done(); // 关闭弹框
          onSearch(); // 刷新表格数据
        }
        FormRef.validate(valid => {//看表单是否验证成功
          if (valid) {
            console.log("curData", curData);
            // 表单规则校验通过
            if (title === "新增") {
              // 实际开发先调用新增接口，再进行下面操作
              useRoleUsertoreHook().checkRoleUserIsExist({
                username: curData.username,
                role: curData.code,
                state: curData.state,
                password: curData.password,
                telephone: curData.telephone,
                remark: curData.remark,
                createdDate: new Date(),
              })
                .then(res => {
                  if (res) {
                    chores();
                  } else {
                    message(`该用户‘${curData.username}’已经存在`, {
                      type: "error"
                    });
                  }
                })

            } else {
              // 实际开发先调用修改接口，再进行下面操作
              useRoleUsertoreHook().checkRoleUserIsExist2({
                id: curData.id,
                username: curData.username,
                password: curData.password,
                state: curData.state,
                telephone: curData.telephone,
                createdDate: curData.createdDate,
                role: curData.code,
                remark: curData.remark,
              })
                .then(res => {
                  console.log(res)
                  if (res) {
                    chores();
                  } else {
                    message(`该用户‘${curData.username}’已经存在`, {
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

  /** 菜单权限 */
  async function handleMenu(row?: any) {
    const { id } = row;
    if (id) {
      curRow.value = row;
      isShow.value = true;
      const { data } = await getRoleMenuIds({ id });
      treeRef.value.setCheckedKeys(data);
    } else {
      curRow.value = null;
      isShow.value = false;
    }
  }

  /** 高亮当前权限选中行 */
  function rowStyle({ row: { id } }) {
    return {
      cursor: "pointer",
      background: id === curRow.value?.id ? "var(--el-fill-color-light)" : ""
    };
  }

  /** 菜单权限-保存 */
  function handleSave() {
    const { id, name } = curRow.value;
    // 根据用户 id 调用实际项目中菜单权限修改接口
    console.log(id, treeRef.value.getCheckedKeys());
    message(`角色名称为${name}的菜单权限修改成功`, {
      type: "success"
    });
  }

  /** 数据权限 可自行开发 */
  // function handleDatabase() {}

  const onQueryChanged = (query: string) => {
    treeRef.value!.filter(query);
  };

  const filterMethod = (query: string, node) => {
    return transformI18n(node.title)!.includes(query);
  };

  onMounted(async () => {
    onSearch();
    const { data } = await getRoleMenu();
    treeIds.value = getKeyList(data, "id");
    treeData.value = handleTree(data);
  });

  watch(isExpandAll, val => {
    val
      ? treeRef.value.setExpandedKeys(treeIds.value)
      : treeRef.value.setExpandedKeys([]);
  });

  watch(isSelectAll, val => {
    val
      ? treeRef.value.setCheckedKeys(treeIds.value)
      : treeRef.value.setCheckedKeys([]);
  });

  return {
    form,
    isShow,
    curRow,
    loading,
    columns,
    rowStyle,
    dataList,
    treeData,
    treeProps,
    isLinkage,
    pagination,
    isExpandAll,
    isSelectAll,
    treeSearchValue,
    // buttonClass,
    onSearch,
    resetForm,
    openDialog,
    handleMenu,
    handleSave,
    handleDelete,
    filterMethod,
    transformI18n,
    onQueryChanged,
    // handleDatabase,
    handleSizeChange,
    handleCurrentChange,
    handleSelectionChange
  };
}
