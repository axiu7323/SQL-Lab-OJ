<template>
  <pre class="sample-viewer">{{ content || '暂无样例数据' }}</pre>
</template>

<script setup lang="ts">
defineProps<{
  content?: string
}>()
</script>

<style scoped>
.sample-viewer {
  min-height: 100px;
  margin: 0;
  padding: 12px;
  overflow: auto;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  background: #f8fafc;
  color: #334155;
  line-height: 1.6;
}
</style>
