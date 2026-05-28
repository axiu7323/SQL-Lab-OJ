<template>
  <div class="page">
    <div class="page-header">
      <div>
        <h1 class="page-title">{{ isEdit ? '编辑题目' : '新增题目' }}</h1>
        <p class="page-subtitle">维护题干、初始化 SQL、标准答案和判题配置。</p>
      </div>
      <el-button type="primary" :loading="saving" @click="handleSave">保存</el-button>
    </div>

    <el-form :model="form" label-width="110px" class="section">
      <el-form-item label="题目标题">
        <el-input v-model="form.title" />
      </el-form-item>
      <el-form-item label="题目描述">
        <el-input v-model="form.description" type="textarea" :rows="5" />
      </el-form-item>
      <el-form-item label="分类">
        <el-input v-model="form.categoryName" />
      </el-form-item>
      <el-form-item label="难度/状态">
        <el-select v-model="form.difficulty" style="width: 140px">
          <el-option label="简单" value="EASY" />
          <el-option label="中等" value="MEDIUM" />
          <el-option label="困难" value="HARD" />
        </el-select>
        <el-select v-model="form.status" style="width: 140px; margin-left: 12px">
          <el-option label="草稿" value="DRAFT" />
          <el-option label="启用" value="ENABLED" />
          <el-option label="禁用" value="DISABLED" />
        </el-select>
      </el-form-item>
      <el-form-item label="分值/限制">
        <el-input-number v-model="form.score" :min="0" :max="100" />
        <el-input-number v-model="form.timeLimitMs" :min="500" :step="500" style="margin-left: 12px" />
        <el-input-number v-model="form.maxRows" :min="1" :step="100" style="margin-left: 12px" />
      </el-form-item>
      <el-form-item label="比较配置">
        <el-checkbox v-model="form.orderSensitive">顺序敏感</el-checkbox>
        <el-checkbox v-model="form.checkColumnName">检查列名</el-checkbox>
      </el-form-item>
    </el-form>

    <div class="stack">
      <section class="section">
        <h2 class="section-title">建表 SQL</h2>
        <SqlEditor v-model="form.initSchemaSql" height="220px" />
      </section>
      <section class="section">
        <h2 class="section-title">初始化数据 SQL</h2>
        <SqlEditor v-model="form.initDataSql" height="220px" />
      </section>
      <section class="section">
        <h2 class="section-title">标准答案 SQL</h2>
        <SqlEditor v-model="form.standardSql" height="220px" />
      </section>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { createProblem, getTeacherProblemDetail, updateProblem } from '@/api/problem'
import SqlEditor from '@/components/SqlEditor.vue'
import type { ProblemSaveRequest } from '@/types/problem'

const route = useRoute()
const router = useRouter()
const saving = ref(false)
const problemId = computed(() => Number(route.params.id))
const isEdit = computed(() => Boolean(route.params.id))
const form = reactive<ProblemSaveRequest>({
  title: '',
  description: '',
  categoryId: 1,
  categoryName: 'basic',
  difficulty: 'EASY',
  score: 100,
  initSchemaSql: 'CREATE TABLE student(id BIGINT, name VARCHAR(64));',
  initDataSql: "INSERT INTO student(id, name) VALUES (1, 'Tom');",
  standardSql: 'SELECT id, name FROM student;',
  orderSensitive: false,
  checkColumnName: true,
  timeLimitMs: 3000,
  maxRows: 1000,
  status: 'DRAFT'
})

async function loadProblem() {
  if (!isEdit.value) {
    return
  }
  const detail = await getTeacherProblemDetail(problemId.value)
  const firstCase = detail.cases[0]
  Object.assign(form, {
    title: detail.title,
    description: detail.description,
    categoryId: detail.categoryId || 1,
    categoryName: detail.categoryName,
    difficulty: detail.difficulty,
    score: detail.score,
    initSchemaSql: firstCase?.schemaSql || '',
    initDataSql: firstCase?.initDataSql || '',
    standardSql: firstCase?.expectedSql || '',
    orderSensitive: detail.orderSensitive,
    checkColumnName: detail.checkColumnName,
    timeLimitMs: detail.timeLimitMs,
    maxRows: detail.maxRows,
    status: detail.status || 'DRAFT'
  })
}

async function handleSave() {
  saving.value = true
  try {
    if (isEdit.value) {
      await updateProblem(problemId.value, form)
    } else {
      await createProblem(form)
    }
    await router.push('/teacher/problems')
  } finally {
    saving.value = false
  }
}

onMounted(loadProblem)
</script>
