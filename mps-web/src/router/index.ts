import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'home',
    component: () => import('@/pages/Home.vue'),
  },
  {
    path: '/products',
    alias: '/me/products',
    children: [
      {
        path: '',
        name: 'products',
        component: () => import('@/pages/products/List.vue'),
        meta: { title: 'Products List', showHeaderSearch: true },
      },
      {
        path: 'new',
        name: 'products-new',
        component: () => import('@/pages/products/New.vue'),
        meta: { title: 'New Product' },
      },
    ],
  },
  { path: '/:pathMatch(.*)*', name: 'not-found', component: () => import('@/pages/NotFound.vue') },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router
