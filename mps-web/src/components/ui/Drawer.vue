<script setup lang="ts">
  const props = withDefaults(defineProps<{ modelValue: boolean; position?: 'left' | 'right' }>(), { position: 'right' })

  const emit = defineEmits<{ (e: 'update:modelValue', v: boolean): void }>()
  const close = () => emit('update:modelValue', false)
</script>

<template>
  <div
    v-if="props.modelValue"
    class="fixed inset-0 bg-black/40 z-40"
    aria-hidden="true"
    @click="close"
  />
  <aside
    v-if="props.modelValue"
    :class="[
      'fixed top-0 h-full z-50 w-sm bg-white shadow-2xl',
      props.position === 'right' ? 'right-0 border-l-3' : 'left-0  border-r-3',
    ]"
  >
    <div class="app-pad flex-between border-b shadow-sm">
      <strong>Menu</strong>
      <button
        class="group p-2 bg-inherit transition-transform duration-150"
        aria-label="Close"
        @click="close"
      >
        <svg
          width="24"
          height="24"
          viewBox="0 0 24 24"
          aria-hidden="true"
          class="transition-transform duration-200 ease-out group-hover:rotate-90 motion-reduce:transition-none"
        >
          <path
            d="M6 6l12 12M18 6 6 18"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
          />
        </svg>
      </button>
    </div>
    <div class="p-3">
      <slot />
    </div>
  </aside>
</template>
