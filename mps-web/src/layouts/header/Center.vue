<script setup lang="ts">
  import { useRoute, useRouter, type LocationQueryValue } from 'vue-router'
  import { computed } from 'vue'

  const route = useRoute()
  const router = useRouter()
  const showSearch = computed(() => route.matched.some((r) => r.meta.showHeaderSearch))

  const norm = (v: LocationQueryValue | LocationQueryValue[]) =>
    (Array.isArray(v) ? (v[0] ?? '') : (v ?? '')).toString().trim()

  const query = computed<string>({
    get() {
      return norm(route.query.q)
    },
    set(val: string) {
      const next = val.trim()
      const curr = norm(route.query.q)
      if (next === curr) return
      router.replace({ name: 'products', query: { ...route.query, q: next || undefined } })
    },
  })
</script>

<template>
  <div class="flex-1 flex justify-center items-center">
    <div class="w-full max-w-lg h-10">
      <form
        v-if="showSearch"
        role="search"
        class="w-full max-w-lg hidden lg:block"
        @submit.prevent
      >
        <input
          v-model="query"
          type="search"
          placeholder="Search products..."
          aria-label="Search products"
          aria-controls="product-list"
          class="w-full input-text-base"
        />
      </form>
    </div>
  </div>
</template>
