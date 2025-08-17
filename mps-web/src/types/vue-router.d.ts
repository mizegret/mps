import type { RouteLocationNormalized } from 'vue-router'

declare module 'vue-router' {
  interface RouteMeta {
    title?: string | ((r: RouteLocationNormalized) => string)
    showHeaderSearch?: boolean
  }
}
