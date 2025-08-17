<script setup lang="ts">
  const props = withDefaults(defineProps<{ modelValue: boolean; position?: 'left' | 'right' }>(), { position: 'right' })

  const emit = defineEmits<{ (e: 'update:modelValue', v: boolean): void }>()
  const close = () => emit('update:modelValue', false)
</script>

<template>
  <Transition
    enter-active-class="trn-opacity-enter-500"
    enter-from-class="opacity-0"
    enter-to-class="opacity-100"
    leave-active-class="trn-opacity-leave-500"
    leave-from-class="opacity-100"
    leave-to-class="opacity-0"
  >
    <div
      v-if="props.modelValue"
      class="fixed inset-0 bg-black/40 z-40"
      aria-hidden="true"
      @click="close"
    />
  </Transition>
  <Transition
    appear
    :enter-active-class="'transition-transform duration-500 ease-out motion-reduce:transition-none'"
    :enter-from-class="props.position === 'right' ? 'translate-x-full' : '-translate-x-full'"
    enter-to-class="translate-x-0"
    leave-active-class="trn-transform-leave-500"
    leave-from-class="translate-x-0"
    :leave-to-class="props.position === 'right' ? 'translate-x-full' : '-translate-x-full'"
  >
    <aside
      v-if="props.modelValue"
      :class="[
        'fixed top-0 h-full z-50 w-[min(100vw,22rem)] bg-white transform-gpu will-change-transform translate-x-0',
        props.position === 'right' ? 'right-0 border-l-3' : 'left-0 border-r-3',
      ]"
      aria-label="Menu"
    >
      <div class="app-pad flex-between border-b">
        <strong class="leading-none">Menu</strong>
        <button
          class="group inline-flex items-center justify-center p-2 bg-white transition-transform duration-150"
          aria-label="Close"
          @click="close"
        >
          <i class="i-lucide:x w-5 h-5 text-gray-700 trn-transform-enter-500 group-hover:rotate-90"></i>
        </button>
      </div>
      <div class="p-3">
        <slot />
      </div>
    </aside>
  </Transition>
</template>
