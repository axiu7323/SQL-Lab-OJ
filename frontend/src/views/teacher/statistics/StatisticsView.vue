<template>
  <div class="page">
    <div class="page-header">
      <div>
        <h1 class="page-title">练习统计</h1>
        <p class="page-subtitle">查看题目通过率和学生练习情况。</p>
      </div>
      <el-button @click="loadStatistics">刷新</el-button>
    </div>

    <div class="two-column">
      <section class="section">
        <h2 class="section-title">题目通过率</h2>
        <el-table :data="problemRows" border>
          <el-table-column prop="title" label="题目" />
          <el-table-column prop="acceptedStudentCount" label="通过人数" width="100" />
          <el-table-column prop="passRate" label="通过率" width="100" />
        </el-table>
      </section>

      <section class="section">
        <h2 class="section-title">学生得分</h2>
        <el-table :data="studentRows" border>
          <el-table-column prop="realName" label="学生" />
          <el-table-column prop="passedCount" label="通过题数" width="100" />
          <el-table-column prop="totalScore" label="总分" width="100" />
        </el-table>
      </section>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { getProblemStatistics, getStudentStatistics } from '@/api/statistics'
import type { ProblemStatistics, StudentStatistics } from '@/types/statistics'

const problemRows = ref<ProblemStatistics[]>([])
const studentRows = ref<StudentStatistics[]>([])

async function loadStatistics() {
  const [problems, students] = await Promise.all([getProblemStatistics(), getStudentStatistics()])
  problemRows.value = problems
  studentRows.value = students
}

onMounted(loadStatistics)
</script>
