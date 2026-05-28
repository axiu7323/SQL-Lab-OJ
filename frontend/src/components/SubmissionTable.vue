<template>
  <el-table :data="rows" border>
    <el-table-column prop="id" label="提交 ID" width="110" />
    <el-table-column prop="problemId" label="题目 ID" width="110" />
    <el-table-column label="状态" width="110">
      <template #default="{ row }">
        <JudgeStatusTag :status="row.status" />
      </template>
    </el-table-column>
    <el-table-column prop="score" label="得分" width="90" />
    <el-table-column prop="executeTimeMs" label="耗时(ms)" width="110" />
    <el-table-column prop="message" label="信息" min-width="180" />
    <el-table-column prop="submittedAt" label="提交时间" min-width="170" />
    <el-table-column v-if="showActions" label="操作" width="160" fixed="right">
      <template #default="{ row }">
        <el-button text type="primary" @click="$emit('view', row)">详情</el-button>
        <el-button v-if="judgeable" text type="success" @click="$emit('judge', row)">判题</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup lang="ts">
import JudgeStatusTag from './JudgeStatusTag.vue'
import type { SubmissionItem } from '@/types/submission'

withDefaults(
  defineProps<{
    rows: SubmissionItem[]
    showActions?: boolean
    judgeable?: boolean
  }>(),
  {
    showActions: true,
    judgeable: false
  }
)

defineEmits<{
  view: [row: SubmissionItem]
  judge: [row: SubmissionItem]
}>()
</script>
