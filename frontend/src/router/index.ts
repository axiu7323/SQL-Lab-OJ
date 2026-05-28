import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import AppLayout from '@/layouts/AppLayout.vue'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    redirect: '/student/problems'
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/login/LoginView.vue'),
    meta: { title: '登录', requiresAuth: false, roles: [] }
  },
  {
    path: '/student',
    component: AppLayout,
    redirect: '/student/problems',
    meta: { requiresAuth: true, roles: ['STUDENT'] },
    children: [
      {
        path: 'problems',
        name: 'student-problems',
        component: () => import('@/views/student/problem-list/ProblemListView.vue'),
        meta: { title: '题目列表', requiresAuth: true, roles: ['STUDENT'] }
      },
      {
        path: 'problems/:id',
        name: 'student-problem-detail',
        component: () => import('@/views/student/problem-detail/ProblemDetailView.vue'),
        meta: { title: '题目详情', requiresAuth: true, roles: ['STUDENT'] }
      },
      {
        path: 'problems/:id/practice',
        name: 'student-sql-practice',
        component: () => import('@/views/student/sql-practice/SqlPracticeView.vue'),
        meta: { title: 'SQL 练习', requiresAuth: true, roles: ['STUDENT'] }
      },
      {
        path: 'submissions',
        name: 'student-submissions',
        component: () => import('@/views/student/submission-history/SubmissionHistoryView.vue'),
        meta: { title: '提交历史', requiresAuth: true, roles: ['STUDENT'] }
      },
      {
        path: 'ranking',
        name: 'student-ranking',
        component: () => import('@/views/student/ranking/RankingView.vue'),
        meta: { title: '排行榜', requiresAuth: true, roles: ['STUDENT'] }
      }
    ]
  },
  {
    path: '/teacher',
    component: AppLayout,
    redirect: '/teacher/problems',
    meta: { requiresAuth: true, roles: ['TEACHER'] },
    children: [
      {
        path: 'problems',
        name: 'teacher-problems',
        component: () => import('@/views/teacher/problem-manage/ProblemManageView.vue'),
        meta: { title: '题库管理', requiresAuth: true, roles: ['TEACHER'] }
      },
      {
        path: 'problems/create',
        name: 'teacher-problem-create',
        component: () => import('@/views/teacher/problem-edit/ProblemEditView.vue'),
        meta: { title: '新增题目', requiresAuth: true, roles: ['TEACHER'] }
      },
      {
        path: 'problems/:id/edit',
        name: 'teacher-problem-edit',
        component: () => import('@/views/teacher/problem-edit/ProblemEditView.vue'),
        meta: { title: '编辑题目', requiresAuth: true, roles: ['TEACHER'] }
      },
      {
        path: 'submissions',
        name: 'teacher-submissions',
        component: () => import('@/views/teacher/submission-manage/SubmissionManageView.vue'),
        meta: { title: '提交记录', requiresAuth: true, roles: ['TEACHER'] }
      },
      {
        path: 'statistics',
        name: 'teacher-statistics',
        component: () => import('@/views/teacher/statistics/StatisticsView.vue'),
        meta: { title: '练习统计', requiresAuth: true, roles: ['TEACHER'] }
      }
    ]
  },
  {
    path: '/admin',
    component: AppLayout,
    redirect: '/admin/users',
    meta: { requiresAuth: true, roles: ['ADMIN'] },
    children: [
      {
        path: 'users',
        name: 'admin-users',
        component: () => import('@/views/admin/user-manage/UserManageView.vue'),
        meta: { title: '用户管理', requiresAuth: true, roles: ['ADMIN'] }
      },
      {
        path: 'classes',
        name: 'admin-classes',
        component: () => import('@/views/admin/class-manage/ClassManageView.vue'),
        meta: { title: '班级管理', requiresAuth: true, roles: ['ADMIN'] }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
