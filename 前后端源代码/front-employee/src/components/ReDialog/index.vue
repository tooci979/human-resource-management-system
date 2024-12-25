<script setup lang="ts">
import {
  type EventType,
  type ButtonProps,
  type DialogOptions,
  closeDialog,
  dialogStore
} from "./index";
import { ref, computed } from "vue";
import { isFunction } from "@pureadmin/utils";
import Fullscreen from "@iconify-icons/ri/fullscreen-fill";
import ExitFullscreen from "@iconify-icons/ri/fullscreen-exit-fill";

defineOptions({
  name: "ReDialog"
});

const fullscreen = ref(false);
// 根据参数的内容返回一个按钮数组
const footerButtons = computed(() => {
  
  return (options: DialogOptions) => {
  console.log(options)
    return ([
          {
            label: "取消",
            text: true,
            bg: true,
            btnClick: ({ dialog: { options, index } }) => {
              const done = () =>
                closeDialog(options, index, { command: "cancel" });
              if (options?.beforeCancel && isFunction(options?.beforeCancel)) {
                options.beforeCancel(done, { options, index });
              } else {
                done();
              }
            }
          },
          {
            label: "确定",
            type: "primary",
            text: true,
            bg: true,
            // popconfirm: options?.popconfirm,
            btnClick: ({ dialog: { options, index } }) => {

              const done = () =>
                closeDialog(options, index, { command: "sure" });
              // 判断options是否有beforeSure，并且beforeSure是否为一个函数
              if (options?.beforeSure && isFunction(options?.beforeSure)) {
                console.log('btnClick方法被执行了')
                options.beforeSure(done, { options, index });//对话框
              } else {
                
                console.log('没有执行beforeSure')
                done();
              }
            }
          }
        ] as Array<ButtonProps>);
  };
});

const fullscreenClass = computed(() => {
  return [
    "el-icon",
    "el-dialog__close",
    "-translate-x-2",
    "cursor-pointer",
    "hover:!text-[red]"
  ];
});

function eventsCallBack(
  event: EventType,
  options: DialogOptions,
  index: number,
  isClickFullScreen = false
) {
  if (!isClickFullScreen) fullscreen.value = options?.fullscreen ?? false;
  if (options?.[event] && isFunction(options?.[event])) {
    return options?.[event]({ options, index });
  }
}

function handleClose(
  options: DialogOptions,
  index: number,
  args = { command: "close" }
) {
  closeDialog(options, index, args);
  eventsCallBack("close", options, index);
}
</script>

<template>
<!-- 叉x 是内置的 通过 v-model="options.visible"来管理是否关闭表单-->
  <el-dialog
    v-for="(options, index) in dialogStore"
    :key="index"
    v-bind="options"
    v-model="options.visible"
    class="pure-dialog"
    :fullscreen="fullscreen ? true : options?.fullscreen ? true : false"
    @closed="handleClose(options, index)"
    @opened="eventsCallBack('open', options, index)"
    @openAutoFocus="eventsCallBack('openAutoFocus', options, index)"
    @closeAutoFocus="eventsCallBack('closeAutoFocus', options, index)"
  >
    <template
      v-if="options?.fullscreenIcon || options?.headerRenderer"
      #header="{ close, titleId, titleClass }"
    >
      <div
        v-if="options?.fullscreenIcon"
        class="flex items-center justify-between"
      >
      <!-- 新增/修改 {--} -->
        <span :id="titleId" :class="titleClass">{{ options?.title }}</span>
        <!-- 放大框  -->
        <i
          v-if="!options?.fullscreen"
          :class="fullscreenClass"
          @click="
            () => {
              fullscreen = !fullscreen;
              eventsCallBack(
                'fullscreenCallBack',
                { ...options, fullscreen },
                index,
                true
              );
            }
          "
        >
        <!-- 放大框svg -->
          <IconifyIconOffline
            class="pure-dialog-svg"
            :icon="
              options?.fullscreen
                ? ExitFullscreen
                : fullscreen
                  ? ExitFullscreen
                  : Fullscreen
            "
          />
        </i>
      </div>
      <component
        :is="options?.headerRenderer({ close, titleId, titleClass })"
        v-else
      />
    </template>

    <component
      v-bind="options?.props"
      :is="options.contentRenderer({ options, index })"  
      @close="args => handleClose(options, index, args)"
    />
    <!-- footer -->
    <!-- <!- 是否隐藏 `Dialog` 按钮操作区的内容 hideFooter?: boolean;-> -->
    <template #footer> 
      <span>
        <template v-for="(btn, key) in footerButtons(options)" :key="key">
          <el-button
            v-bind="btn"
            @click="
              btn.btnClick({
                dialog: { options, index },//dialogStore传过来的addDialog里的对象和index
                button: { btn, index: key } //btn原对象，index唯一标识符
              })
            ">
            {{ btn?.label }}
          </el-button>
        </template>
      </span>
    </template>
    
  </el-dialog>
</template>
