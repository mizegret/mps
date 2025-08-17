<script setup lang="ts">
  import TheHeader from './layouts/header/TheHeader.vue'
  import TheFooter from './layouts/TheFooter.vue'
</script>

<template>
  <div
    uno-flex="~ col"
    class="min-h-screen bg-gray-50 text-gray-900"
  >
    <TheHeader />
    <main class="container mx-auto flex-1 app-pad">
      <RouterView />
    </main>
    <TheFooter />
  </div>
</template>
