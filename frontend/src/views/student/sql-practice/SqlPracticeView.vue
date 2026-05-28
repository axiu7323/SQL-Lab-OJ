<template>
  <div class="page">
    <div class="page-header">
      <div>
        <h1 class="page-title">{{ detail?.title || 'SQL 练习' }}</h1>
        <p class="page-subtitle">右侧编写 SELECT / WITH 查询并提交判题。</p>
      </div>
      <el-button type="primary" :loading="submitting" @click="handleSubmit">提交 SQL</el-button>
    </div>

    <div class="two-column">
      <div class="stack">
        <section class="section">
          <h2 class="section-title">题目描述</h2>
          <p class="description">{{ detail?.description || '正在加载题目...' }}</p>
        </section>
        <section class="section">
          <h2 class="section-title">表结构</h2>
          <SchemaViewer :content="firstCase?.schemaSql" />
        </section>
        <section class="section">
          <h2 class="section-title">样例数据</h2>
          <SampleDataViewer :content="firstCase?.initDataSql" />
        </section>
      </div>

      <div class="stack">
        <section class="section">
          <h2 class="section-title">SQL 编辑器</h2>
          <SqlEditor v-model="sql" height="420px" />
        </section>
        <ResultViewer
          :status="result.status"
          :score="result.score"
          :message="result.message"
          :detail="result.detail"
          :execute-time-ms="result.executeTimeMs"
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import { useRoute } from 'vue-router'
import { getStudentProblemDetail } from '@/api/problem'
import { submitSql } from '@/api/submission'
import ResultViewer from '@/components/ResultViewer.vue'
import SampleDataViewer from '@/components/SampleDataViewer.vue'
import SchemaViewer from '@/components/SchemaViewer.vue'
import SqlEditor from '@/components/SqlEditor.vue'
import { useUserStore } from '@/store/user'
import type { JudgeStatus } from '@/types/common'
import type { ProblemDetail } from '@/types/problem'

const route = useRoute()
const userStore = useUserStore()
const problemId = computed(() => Number(route.params.id))
const detail = ref<ProblemDetail>()
const firstCase = computed(() => detail.value?.cases[0])
const sql = ref('SELECT * FROM student;')
const submitting = ref(false)
const result = reactive({
  status: 'PENDING' as JudgeStatus,
  score: 0,
  message: '等待提交',
  detail: '',
  executeTimeMs: 0
})

async function loadDetail() {
  detail.value = await getStudentProblemDetail(problemId.value)
}

async function handleSubmit() {
  submitting.value = true
  try {
    const response = await submitSql({
      userId: userStore.user?.id || 1,
      problemId: problemId.value,
      submitSql: sql.value
    })
    result.status = response.status
    result.score = response.score
    result.message = response.message
    result.executeTimeMs = response.executeTimeMs
  } finally {
    submitting.value = false
  }
}

onMounted(loadDetail)
</script>

<style scoped>
.description {
  line-height: 1.8;
  white-space: pre-wrap;
}
</style>
