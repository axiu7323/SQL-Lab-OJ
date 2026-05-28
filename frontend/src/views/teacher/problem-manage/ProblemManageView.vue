<template>
  <div class="page">
    <div class="page-header">
      <div>
        <h1 class="page-title">题库管理</h1>
        <p class="page-subtitle">维护 SQL 练习题、状态和难度。</p>
      </div>
      <el-button type="primary" @click="router.push('/teacher/problems/create')">新增题目</el-button>
    </div>

    <div class="toolbar">
      <el-input v-model="query.keyword" clearable placeholder="搜索题目" style="width: 240px" />
      <el-select v-model="query.status" clearable placeholder="状态" style="width: 140px">
        <el-option label="草稿" value="DRAFT" />
        <el-option label="启用" value="ENABLED" />
        <el-option label="禁用" value="DISABLED" />
      </el-select>
      <el-button type="primary" @click="loadProblems">查询</el-button>
    </div>

    <el-table :data="rows" border>
      <el-table-column prop="id" label="ID" width="90" />
      <el-table-column prop="title" label="题目" min-width="180" />
      <el-table-column prop="categoryName" label="分类" width="130" />
      <el-table-column label="难度" width="110">
        <template #default="{ row }">
          <DifficultyTag :difficulty="row.difficulty" />
        </template>
      </el-table-column>
      <el-table-column prop="score" label="分值" width="90" />
      <el-table-column prop="status" label="状态" width="110" />
      <el-table-column label="操作" width="260" fixed="right">
        <template #default="{ row }">
          <el-button text type="primary" @click="router.push(`/teacher/problems/${row.id}/edit`)">编辑</el-button>
          <el-button text type="success" @click="handleEnable(row.id)">启用</el-button>
          <el-button text type="warning" @click="handleDisable(row.id)">禁用</el-button>
          <el-button text type="danger" @click="handleRemove(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import {
  deleteProblem as removeProblem,
  disableProblem,
  enableProblem,
  getTeacherProblems
} from '@/api/problem'
import DifficultyTag from '@/components/DifficultyTag.vue'
import type { ProblemItem, ProblemQuery } from '@/types/problem'

const router = useRouter()
const rows = ref<ProblemItem[]>([])
const query = reactive<ProblemQuery>({
  pageNo: 1,
  pageSize: 10,
  keyword: '',
  status: undefined
})

async function loadProblems() {
  const page = await getTeacherProblems(query)
  rows.value = page.records
}

async function handleEnable(problemId: number) {
  await enableProblem(problemId)
  await loadProblems()
}

async function handleDisable(problemId: number) {
  await disableProblem(problemId)
  await loadProblems()
}

async function handleRemove(problemId: number) {
  await removeProblem(problemId)
  await loadProblems()
}

onMounted(loadProblems)
</script>
