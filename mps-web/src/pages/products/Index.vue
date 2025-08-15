<script setup lang="ts">
  import { computed, onMounted, ref } from 'vue'
  import ProductList from '@/components/products/ProductList.vue'
  import { api } from '@/api/client'
  import type { Product } from '@/types/model'

  const productList = ref<Product[]>([])
  const query = ref('')

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
  <section class="max-w-screen-xl mx-auto p-2">
    <header class="mb-8">
      <h2 class="mb-4 text-2xl font-bold">Product List</h2>

      <form
        role="search"
        class="ml-auto w-full lg:w-[30rem]"
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
    </header>

    <p
      v-if="query"
      uno-text="xs gray-500"
      class="mb-3"
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
