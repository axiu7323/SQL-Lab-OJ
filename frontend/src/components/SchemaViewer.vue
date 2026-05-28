<template>
  <pre class="schema-viewer">{{ content || '暂无表结构说明' }}</pre>
</template>

<script setup lang="ts">
defineProps<{
  content?: string
}>()
</script>

<style scoped>
.schema-viewer {
  min-height: 120px;
  margin: 0;
  padding: 12px;
  overflow: auto;
  border-radius: 8px;
  background: #0f172a;
  color: #d1fae5;
  line-height: 1.6;
}
</style>
