<script setup lang="ts">
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  import { api } from '@/api/client'

  const name = ref()
  const description = ref()
  const router = useRouter()
  const formEl = ref<HTMLFormElement | null>(null)

  const submit = async () => {
    if (!formEl.value?.checkValidity()) {
      formEl.value?.reportValidity()
      return
    }
    await api.POST('/api/v1/products', {
      body: {
        name: name.value,
        description: description.value,
      },
    })
    router.push({ name: 'products' })
  }
</script>

<template>
  <section class="max-w-2xl mx-auto p-2">
    <form
      ref="formEl"
      class="space-y-6"
      novalidate
      @submit.prevent="submit"
    >
      <div>
        <label
          for="name"
          class="block mb-2 font-medium"
        >
          <span class="mr-1">Name</span>
          <span
            aria-hidden="true"
            class="text-red-500"
          >
            *
          </span>
          <span class="sr-only">required</span>
        </label>
        <input
          id="name"
          v-model="name"
          type="text"
          class="input-text-base w-full"
          required
        />
      </div>
      <div>
        <label
          for="description"
          class="block mb-2 font-medium"
          >Description</label
        >
        <textarea
          id="description"
          v-model="description"
          class="input-text-base w-full"
          rows="4"
        ></textarea>
      </div>
      <div>
        <button
          type="submit"
          class="button-base"
        >
          Submit
        </button>
      </div>
    </form>
  </section>
</template>
