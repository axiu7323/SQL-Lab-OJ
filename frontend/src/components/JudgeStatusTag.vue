<template>
  <el-tag :type="tagType" effect="light">{{ status }}</el-tag>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import type { JudgeStatus } from '@/types/common'

const props = defineProps<{
  status: JudgeStatus
}>()

const tagType = computed(() => {
  if (props.status === 'AC') {
    return 'success'
  }
  if (props.status === 'WA' || props.status === 'CE') {
    return 'warning'
  }
  if (props.status === 'RE' || props.status === 'TLE' || props.status === 'SE') {
    return 'danger'
  }
  return 'info'
})
</script>
