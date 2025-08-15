<script setup lang="ts">
  import ProductItem from '@/components/products/ProductItem.vue'
  import type { Product } from '@/types/model'

  defineProps<{
    productList: Product[]
  }>()
</script>

<template>
  <ul
    id="product-list"
    uno-grid="~ cols-1 sm:cols-2 md:cols-3 lg:cols-4 xl:cols-5"
    uno-gap="4 sm:6"
  >
    <li
      v-for="p in productList"
      :key="p.id"
      uno-border="~ gray-200 hover:gray-300"
      uno-shadow="md hover:lg"
      class="group overflow-hidden rounded-2xl bg-white transition motion-safe:hover:-translate-y-0.5"
    >
      <ProductItem :product="p" />
    </li>
  </ul>
</template>
