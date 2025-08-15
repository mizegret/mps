<script setup lang="ts">
  import { defineProps } from 'vue'
  import type { Product } from '@/types/model'

  defineProps<{
    product: Product
  }>()
</script>

<template>
  <div
    uno-flex="~ col"
    class="p-4 h-full min-h-40"
  >
    <h3 class="text-lg font-semibold truncate tracking-tight">{{ product.name }}</h3>
    <p
      v-if="product?.description"
      class="mt-2 text-sm text-gray-600 break-words clamp-2"
    >
      {{ product.description }}
    </p>

    <div
      uno-text="xs gray-500"
      class="mt-auto pt-3 iflex-2"
    >
      <time v-if="product.createdAt">{{ new Date(product.createdAt).toLocaleDateString() }}</time>
      <span class="ml-auto opacity-0 transition group-hover:opacity-100">›</span>
    </div>
  </div>
</template>

<style scoped>
  .clamp-2 {
    display: -webkit-box;
    line-clamp: 3;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    overflow: hidden;
  }
</style>
