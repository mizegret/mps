<script setup lang="ts">
  import Center from './Center.vue'
  import Left from './Left.vue'
  import Right from './Right.vue'
</script>

<template>
  <header
    uno-border="b gray-200"
    class="bg-white w-full"
  >
    <div class="container mx-auto flex-center-4 app-pad">
      <Left />
      <Center />
      <Right />
    </div>
  </header>
</template>
