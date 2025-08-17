<script setup lang="ts">
  import { RouterLink } from 'vue-router'
</script>

<template>
  <div class="flex-center-4">
    <RouterLink
      to="/"
      class="iflex-2"
    >
      <img
        src="/icon.svg"
        alt="logo"
        class="h-6 w-6"
      />
      <h1 class="text-lg font-semibold hidden sm:block">MPS Product Survey</h1>
    </RouterLink>
  </div>
</template>
