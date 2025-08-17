<script setup lang="ts">
  import { computed, onMounted, ref } from 'vue'
  import ProductList from '@/components/products/ProductList.vue'
  import { api } from '@/api/client'
  import type { Product } from '@/types/model'
  import { useRoute } from 'vue-router'

  const productList = ref<Product[]>([])
  const route = useRoute()

  const query = computed(() => {
    const q = route.query.q
    const v = Array.isArray(q) ? q[0] : q
    return v ?? ''
  })

  const filtered = computed(() => {
    const v = query.value.trim().toLowerCase()
    if (!v) return productList.value
    return productList.value.filter((p) => [p.name, p.description ?? ''].some((t) => t?.toLowerCase().includes(v)))
  })

  onMounted(async () => {
    try {
      productList.value = (await api.GET('/api/v1/products')).data ?? []
    } catch (e) {
      // eslint-disable-next-line no-undef
      console.error(e)
    }
  })
</script>

<template>
  <section class="max-w-screen-xl mx-auto p-2 space-y-4">
    <p
      v-if="query"
      uno-text="xs gray-500"
    >
      {{ filtered.length }} result{{ filtered.length === 1 ? '' : 's' }} for “{{ query }}”
    </p>

    <p
      v-if="filtered.length === 0"
      class="text-sm text-gray-600"
    >
      No products{{ query ? ' match your search.' : ' yet...' }}
    </p>

    <ProductList
      v-else
      :product-list="filtered"
    />
  </section>
</template>
