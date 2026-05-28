<template>
  <div class="page">
    <div class="page-header">
      <div>
        <h1 class="page-title">{{ detail?.title || '题目详情' }}</h1>
        <p class="page-subtitle">{{ detail?.categoryName }} · {{ detail?.score || 0 }} 分</p>
      </div>
      <el-button type="primary" :disabled="!detail" @click="startPractice">开始练习</el-button>
    </div>

    <div v-if="detail" class="two-column">
      <section class="section">
        <div class="meta-row">
          <DifficultyTag :difficulty="detail.difficulty" />
          <span>限时 {{ detail.timeLimitMs }} ms</span>
          <span>最大 {{ detail.maxRows }} 行</span>
        </div>
        <h2 class="section-title">题目描述</h2>
        <p class="description">{{ detail.description }}</p>
      </section>

      <div class="stack">
        <section class="section">
          <h2 class="section-title">表结构</h2>
          <SchemaViewer :content="firstCase?.schemaSql" />
        </section>
        <section class="section">
          <h2 class="section-title">样例数据</h2>
          <SampleDataViewer :content="firstCase?.initDataSql" />
        </section>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getStudentProblemDetail } from '@/api/problem'
import DifficultyTag from '@/components/DifficultyTag.vue'
import SampleDataViewer from '@/components/SampleDataViewer.vue'
import SchemaViewer from '@/components/SchemaViewer.vue'
import type { ProblemDetail } from '@/types/problem'

const route = useRoute()
const router = useRouter()
const detail = ref<ProblemDetail>()
const problemId = computed(() => Number(route.params.id))
const firstCase = computed(() => detail.value?.cases[0])

async function loadDetail() {
  detail.value = await getStudentProblemDetail(problemId.value)
}

function startPractice() {
  router.push(`/student/problems/${problemId.value}/practice`)
}

onMounted(loadDetail)
</script>

<style scoped>
.meta-row {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-bottom: 16px;
  color: #64748b;
}

.description {
  line-height: 1.8;
  white-space: pre-wrap;
}
</style>
