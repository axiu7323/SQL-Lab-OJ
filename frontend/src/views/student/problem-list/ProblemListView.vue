<template>
  <div class="page">
    <div class="page-header">
      <div>
        <h1 class="page-title">题目列表</h1>
        <p class="page-subtitle">选择题目进入详情或直接开始 SQL 练习。</p>
      </div>
    </div>

    <div class="toolbar">
      <el-input v-model="query.keyword" clearable placeholder="搜索题目" style="width: 240px" />
      <el-select v-model="query.difficulty" clearable placeholder="难度" style="width: 140px">
        <el-option label="简单" value="EASY" />
        <el-option label="中等" value="MEDIUM" />
        <el-option label="困难" value="HARD" />
      </el-select>
      <el-button type="primary" @click="loadProblems">查询</el-button>
    </div>

    <div class="problem-grid">
      <ProblemCard v-for="problem in problems" :key="problem.id" :problem="problem" @click="openDetail(problem.id)" />
    </div>

    <el-empty v-if="!loading && problems.length === 0" description="暂无题目" />
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { getStudentProblems } from '@/api/problem'
import ProblemCard from '@/components/ProblemCard.vue'
import type { ProblemItem, ProblemQuery } from '@/types/problem'

const router = useRouter()
const loading = ref(false)
const problems = ref<ProblemItem[]>([])
const query = reactive<ProblemQuery>({
  pageNo: 1,
  pageSize: 10,
  keyword: '',
  difficulty: undefined
})

async function loadProblems() {
  loading.value = true
  try {
    const page = await getStudentProblems(query)
    problems.value = page.records
  } finally {
    loading.value = false
  }
}

function openDetail(problemId: number) {
  router.push(`/student/problems/${problemId}`)
}

onMounted(loadProblems)
</script>

<style scoped>
.problem-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
  gap: 12px;
}
</style>
