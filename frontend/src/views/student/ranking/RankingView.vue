<template>
  <div class="page">
    <div class="page-header">
      <div>
        <h1 class="page-title">排行榜</h1>
        <p class="page-subtitle">按通过题数、总分和提交次数展示练习情况。</p>
      </div>
      <el-button @click="loadRankings">刷新</el-button>
    </div>

    <el-table :data="rows" border>
      <el-table-column prop="rank" label="排名" width="90" />
      <el-table-column prop="realName" label="姓名" />
      <el-table-column prop="passedCount" label="通过题数" />
      <el-table-column prop="totalScore" label="总分" />
      <el-table-column prop="submitCount" label="提交次数" />
    </el-table>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { getRankings } from '@/api/statistics'
import type { RankingItem } from '@/types/statistics'

const rows = ref<RankingItem[]>([])

async function loadRankings() {
  rows.value = await getRankings()
}

onMounted(loadRankings)
</script>
