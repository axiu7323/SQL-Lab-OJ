<template>
  <div class="page">
    <div class="page-header">
      <div>
        <h1 class="page-title">提交记录</h1>
        <p class="page-subtitle">按学生、题目和状态筛选提交，并可手动触发判题。</p>
      </div>
    </div>

    <div class="toolbar">
      <el-input-number v-model="query.studentId" :min="1" placeholder="学生 ID" />
      <el-input-number v-model="query.problemId" :min="1" placeholder="题目 ID" />
      <el-select v-model="query.status" clearable placeholder="状态" style="width: 140px">
        <el-option label="PENDING" value="PENDING" />
        <el-option label="AC" value="AC" />
        <el-option label="WA" value="WA" />
      </el-select>
      <el-button type="primary" @click="loadSubmissions">查询</el-button>
    </div>

    <SubmissionTable :rows="rows" judgeable @judge="handleJudge" />
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { judgeSubmission } from '@/api/judge'
import { getTeacherSubmissions } from '@/api/submission'
import SubmissionTable from '@/components/SubmissionTable.vue'
import type { SubmissionItem, SubmissionQuery } from '@/types/submission'

const rows = ref<SubmissionItem[]>([])
const query = reactive<SubmissionQuery>({
  pageNo: 1,
  pageSize: 10,
  studentId: undefined,
  problemId: undefined,
  status: undefined
})

async function loadSubmissions() {
  const page = await getTeacherSubmissions(query)
  rows.value = page.records
}

async function handleJudge(row: SubmissionItem) {
  await judgeSubmission(row.id)
  await loadSubmissions()
}

onMounted(loadSubmissions)
</script>
