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
  <Transition
    appear
    :enter-active-class="'transition-transform duration-200 ease-out motion-reduce:transition-none'"
    :enter-from-class="props.position === 'right' ? 'translate-x-full' : '-translate-x-full'"
    enter-to-class="translate-x-0"
    :leave-active-class="'transition-transform duration-150 ease-in motion-reduce:transition-none'"
    leave-from-class="translate-x-0"
    :leave-to-class="props.position === 'right' ? 'translate-x-full' : '-translate-x-full'"
  >
    <aside
      v-if="props.modelValue"
      :class="[
        'fixed top-0 h-full z-50 w-sm bg-white transform-gpu will-change-transform translate-x-0',
        props.position === 'right' ? 'right-0 border-l-3' : 'left-0 border-r-3',
      ]"
      aria-label="Menu"
    >
      <div class="app-pad flex-between border-b">
        <strong>Menu</strong>
        <button
          class="group p-2 bg-white transition-transform duration-150"
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
  </Transition>
</template>
