<script setup lang="ts">
  import Drawer from '@/components/ui/Drawer.vue'
  import { ref } from 'vue'
  import { RouterLink } from 'vue-router'

  const isOpen = ref(false)
</script>

<template>
  <button
    class="p-2 bg-white"
    aria-label="Open menu"
    @click="isOpen = !isOpen"
  >
    ☰
  </button>

  <Drawer
    v-model="isOpen"
    position="right"
  >
    <div class="p-2 space-y-2">
      <RouterLink
        to="/"
        class="block px-3 py-2"
        >Home</RouterLink
      >
      <RouterLink
        to="/products"
        class="block px-3 py-2"
        >Products</RouterLink
      >
      <RouterLink
        to="/products/new"
        class="block px-3 py-2"
        >Products New</RouterLink
      >
    </div>
  </Drawer>
</template>
