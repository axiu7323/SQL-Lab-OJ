<template>
  <el-tag :type="tagType" effect="plain">{{ label }}</el-tag>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import type { ProblemDifficulty } from '@/types/common'

const props = defineProps<{
  difficulty: ProblemDifficulty
}>()

const label = computed(() => {
  const labels: Record<ProblemDifficulty, string> = {
    EASY: '简单',
    MEDIUM: '中等',
    HARD: '困难'
  }
  return labels[props.difficulty]
})

const tagType = computed(() => {
  const types: Record<ProblemDifficulty, 'success' | 'warning' | 'danger'> = {
    EASY: 'success',
    MEDIUM: 'warning',
    HARD: 'danger'
  }
  return types[props.difficulty]
})
</script>
