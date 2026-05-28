<template>
  <div class="result-viewer">
    <div class="result-head">
      <JudgeStatusTag :status="status" />
      <strong>{{ score }} 分</strong>
      <span>{{ executeTimeMs }} ms</span>
    </div>
    <p v-if="message" class="message">{{ message }}</p>
    <pre v-if="detail" class="detail">{{ detail }}</pre>
  </div>
</template>

<script setup lang="ts">
import JudgeStatusTag from './JudgeStatusTag.vue'
import type { JudgeStatus } from '@/types/common'

withDefaults(
  defineProps<{
    status: JudgeStatus
    score?: number
    executeTimeMs?: number
    message?: string
    detail?: string
  }>(),
  {
    score: 0,
    executeTimeMs: 0,
    message: '',
    detail: ''
  }
)
</script>

<style scoped>
.result-viewer {
  padding: 14px;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  background: #fff;
}

.result-head {
  display: flex;
  align-items: center;
  gap: 12px;
}

.message {
  margin: 12px 0 0;
  color: #374151;
}

.detail {
  margin: 12px 0 0;
  padding: 12px;
  overflow: auto;
  border-radius: 6px;
  background: #f8fafc;
  color: #475569;
}
</style>
