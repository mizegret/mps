<script setup lang="ts">
  import { defineProps, onBeforeUnmount, onMounted, ref } from 'vue'
  import type { Product } from '@/types/model'

  const props = defineProps<{ product: Product }>()
  const isOpen = ref(false)
  const root = ref<HTMLDivElement | null>(null)

  const onDocClick = (e: MouseEvent) => {
    if (!root.value) return
    if (!root.value.contains(e.target as Node)) isOpen.value = false
  }
  onMounted(() => document.addEventListener('click', onDocClick))
  onBeforeUnmount(() => document.removeEventListener('click', onDocClick))
</script>

<template>
  <div
    uno-flex="~ col"
    class="p-4 h-full min-h-40 group"
  >
    <h3 class="text-lg font-semibold truncate tracking-tight">{{ product.name }}</h3>
    <p
      v-if="product?.description"
      class="mt-2 text-sm text-gray-600 break-words clamp-3"
    >
      {{ product.description }}
    </p>

    <div class="mt-auto pt-3 flex items-center text-xs text-gray-500">
      <time
        v-if="product.createdAt"
        class="flex-1 truncate"
      >
        {{ new Date(product.createdAt).toLocaleDateString() }}
      </time>
      <div class="relative">
        <button
          type="button"
          @click.stop="isOpen = !isOpen"
          uno-border="~ gray-300"
          uno-bg="white hover:gray-100"
          uno-opacity="0 group-hover:100"
          uno-ring="active:(2 gray-400)"
          class="shrink-0 ml-2 rounded-md px-2 py-1 text-gray-700 leading-none transition"
          :class="{ 'opacity-100': isOpen }"
          aria-label="More actions"
        >
          <span class="i-lucide-more-horizontal inline-block w-4 h-4"></span>
        </button>
        <div
          v-if="isOpen"
          ref="root"
          class="absolute left-0 top-hull mt-1 z-20 min-w-28 rounded-md border border-gray-200 bg-white p-2 shadow-lg"
        >
          <button
            type="button"
            class="w-full text-left px-3 py-2 rounded-md text-sm text-red-600 hover:bg-red-50 bg-white"
            @click="isOpen = false"
          >
            delete
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
  .clamp-3 {
    display: -webkit-box;
    line-clamp: 3;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    overflow: hidden;
  }
</style>
