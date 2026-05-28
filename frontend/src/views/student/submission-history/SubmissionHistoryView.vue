<template>
  <div class="page">
    <div class="page-header">
      <div>
        <h1 class="page-title">提交历史</h1>
        <p class="page-subtitle">查看自己的 SQL 提交记录与判题结果。</p>
      </div>
    </div>

    <div class="toolbar">
      <el-input-number v-model="query.problemId" :min="1" placeholder="题目 ID" />
      <el-select v-model="query.status" clearable placeholder="状态" style="width: 140px">
        <el-option label="AC" value="AC" />
        <el-option label="WA" value="WA" />
        <el-option label="CE" value="CE" />
        <el-option label="RE" value="RE" />
      </el-select>
      <el-button type="primary" @click="loadSubmissions">查询</el-button>
    </div>

    <SubmissionTable :rows="rows" />
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { getMySubmissions } from '@/api/submission'
import SubmissionTable from '@/components/SubmissionTable.vue'
import type { SubmissionItem, SubmissionQuery } from '@/types/submission'

const rows = ref<SubmissionItem[]>([])
const query = reactive<SubmissionQuery>({
  pageNo: 1,
  pageSize: 10,
  userId: 1,
  problemId: undefined,
  status: undefined
})

async function loadSubmissions() {
  const page = await getMySubmissions(query)
  rows.value = page.records
}

onMounted(loadSubmissions)
</script>
