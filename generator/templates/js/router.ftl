import { createRouter, createWebHashHistory } from 'vue-router'
import inner from './pages/inner.vue'
import outer from './pages/outer.vue'
import forget from './pages/outer/forget.vue'
import login from './pages/outer/login.vue'
import register from './pages/outer/register.vue'

const routes = [
  {
    path: '/o',
    component: outer,
    children: [
      {
        path: 'login',
        component: login,
      },
      {
        path: 'forget',
        component: forget,
      },
      {
        path: 'register',
        component: register,
      },
    ],
  },
  {
    path: '/i',
    component: inner,
    children: [
<#list models?keys as key>
      {
        path: '${u(key)}',
        component: () => import('./pages/auto/${u(key)}/table.vue'),
      },
</#list>
    ],
  },
]

export default createRouter({
  history: createWebHashHistory(),
  routes: routes,
})
