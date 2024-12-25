<script setup lang="ts">
import { ref } from "vue";
import { useDept } from "./utils/hook";
import { PureTableBar } from "@/components/RePureTableBar";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";

import Delete from "@iconify-icons/ep/delete";
import EditPen from "@iconify-icons/ep/edit-pen";
import Refresh from "@iconify-icons/ep/refresh";
import AddFill from "@iconify-icons/ri/add-circle-line";
import { getToken, formatToken } from "@/utils/auth";

defineOptions({
  name: "employee-manage"
});

const formRef = ref();
const tableRef = ref();
const {
  form,
  loading,
  columns,
  dataList,
  onSearch,
  resetForm,
  openDialog,
  handleDelete,
  handleSelectionChange,
  pagination,
  handleSizeChange,
  handleCurrentChange,
  dataApartmentList
} = useDept();

const dataToken =getToken();
console.log(dataToken)
</script>

<template>
  <div class="main">
    <el-form
      ref="formRef"
      :inline="true"
     :model="dataApartmentList"
      :rules="formRules"
      class="search-form bg-bg_color w-[99/100] pl-8 pt-[12px] overflow-auto"
    >
      <el-form-item label="所属部门" >
          <el-cascader
            v-model="form.apartmentId"
            class="!w-[160px]"
            :options="dataApartmentList"
            :props="{
              value: 'id',
              label: 'department',
              emitPath: false,
              checkStrictly: true
            }"
            clearable
            filterable
            placeholder="请选择要添加的岗位"
          >
            <template #default="{ node, data }">
              <!-- {{data.job}} -->
              <span>{{data.department}}</span>
              <span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
            </template>
          </el-cascader>
        </el-form-item>
       <el-form-item label="岗位名称：" prop="name">
        <el-input
          v-model="form.jobName"
          placeholder="请输入岗位名称"
          clearable
          class="!w-[160px]"
        />
      </el-form-item>

       <el-form-item label="姓名：" prop="name">
        <el-input
          v-model="form.employeeName"
          placeholder="请输入姓名"
          clearable
          class="!w-[160px]"
        />
      </el-form-item>
      
      <el-form-item label="状态：" prop="status">
        <el-select
          v-model="form.state"
          placeholder="请选择状态"
          clearable
          class="!w-[160px]"
        >
          <el-option label="正式员工" :value="1" />
          <el-option label="非正式" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          :icon="useRenderIcon('ri:search-line')"
          :loading="loading"
          @click="onSearch"
        >
          搜索
        </el-button>
        <el-button :icon="useRenderIcon(Refresh)" @click="resetForm(formRef)">
          重置
        </el-button>
      </el-form-item>
    </el-form>

 <PureTableBar
      title="职员管理"
      :columns="columns"
      :tableRef="tableRef?.getTableRef()"
      @refresh="onSearch"
      
    >

      <template #buttons>
        <el-button
          type="primary"
          :icon="useRenderIcon(AddFill)"
          @click="openDialog()"
          v-if="dataToken.accessToken==='admin'"
        >
          新增职员
        </el-button>
      </template>
      <template v-slot="{ size, dynamicColumns }">
        <pure-table
          ref="tableRef"
          align-whole="center"
          adaptive
          :adaptiveConfig="{ offsetBottom: 108 }"
          row-key="id"
          showOverflowTooltip
          table-layout="auto"
          default-expand-all
          :loading="loading"
          :row-style="rowStyle"
          :size="size"
          :data="dataList"
          :columns="dynamicColumns"
          :pagination="pagination"
          :paginationSmall="size === 'small' ? true : false"
          :header-cell-style="{
            background: 'var(--el-fill-color-light)',
            color: 'var(--el-text-color-primary)'
          }"
          @selection-change="handleSelectionChange"
          @page-size-change="handleSizeChange"
          @page-current-change="handleCurrentChange"
        >
          <template #operation="{ row }">
            <el-button
              class="reset-margin"
              link
              type="primary"
              :size="size"
              :icon="useRenderIcon(EditPen)"
              @click="openDialog('修改', row)"
v-if="dataToken.accessToken==='admin'"
            >
              修改
            </el-button>
           
            <el-popconfirm
              :title="`是否确认删除这条数据？`"
              @confirm="handleDelete(row)"
            >
              <template #reference>
                <el-button
                  class="reset-margin"
                  link
                  type="primary"
                  :size="size"
                  :icon="useRenderIcon(Delete)"
v-if="dataToken.accessToken==='admin'"
                >
                  删除
                </el-button>
                <el-button plain type="danger" v-if="dataToken.accessToken==='common'" disabled>
                  无操作权限
             </el-button>
              </template>
            </el-popconfirm>
          </template>
        </pure-table>
      </template>
    </PureTableBar>
  </div>
</template>

<style lang="scss" scoped>
:deep(.el-table__inner-wrapper::before) {
  height: 0;
}

.main-content {
  margin: 24px 24px 0 !important;
}

.search-form {
  :deep(.el-form-item) {
    margin-bottom: 12px;
  }
}
</style>
