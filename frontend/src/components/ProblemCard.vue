<template>
  <div class="problem-card">
    <div>
      <h3>{{ problem.title }}</h3>
      <p>{{ problem.categoryName }} · {{ problem.score }} 分</p>
    </div>
    <DifficultyTag :difficulty="problem.difficulty" />
  </div>
</template>

<script setup lang="ts">
import DifficultyTag from './DifficultyTag.vue'
import type { ProblemItem } from '@/types/problem'

defineProps<{
  problem: ProblemItem
}>()
</script>

<style scoped>
.problem-card {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  padding: 14px;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  background: #fff;
}

.problem-card h3 {
  margin: 0 0 6px;
  font-size: 16px;
}

.problem-card p {
  margin: 0;
  color: #64748b;
}
</style>
