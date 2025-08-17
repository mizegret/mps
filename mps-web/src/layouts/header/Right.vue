<script setup lang="ts">
    import { RouterLink } from 'vue-router'
</script>

<template>
  <nav
    uno-text="sm gray-600 [&>a:hover]:gray-900 [&_.router-link-exact-active]:gray-900"
    class="flex-center-6 [&_.router-link-exact-active]:font-bold"
  >
    <RouterLink to="/">Home</RouterLink>
    <RouterLink to="/products">Products</RouterLink>
  </nav>
</template>
