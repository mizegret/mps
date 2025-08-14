<script setup lang="ts">
  import { computed, onMounted, ref } from 'vue'
  import type { Product } from '@/types/product'
  import ProductList from '@/components/products/ProductList.vue'

  const productList = ref<Product[]>([])
  const query = ref('')

  const fetchProductList = () => {
    return [
      {
        id: '1',
        name: 'Product 1',
        description: 'Description for Product 1',
        created_at: '2023-01-01T00:00:00Z',
        updated_at: '2023-01-01T00:00:00Z',
      },
      {
        id: '2',
        name: 'Product 2',
        description: undefined,
        created_at: '2023-01-01T00:00:00Z',
        updated_at: '2023-01-01T00:00:00Z',
      },
      {
        id: '3',
        name: 'Product 3',
        description: 'Description for Product 3',
        created_at: '2023-01-01T00:00:00Z',
        updated_at: '2023-01-01T00:00:00Z',
      },
      {
        id: '4',
        name: 'Product 4',
        description: 'Description for Product 4',
        created_at: '2023-01-01T00:00:00Z',
        updated_at: '2023-01-01T00:00:00Z',
      },
      {
        id: '5',
        name: 'Product 5',
        description: 'Description for Product 5',
        created_at: '2023-01-01T00:00:00Z',
        updated_at: '2023-01-01T00:00:00Z',
      },
      {
        id: '6',
        name: 'Product 6',
        description: 'Description for Product 6',
        created_at: '2023-01-01T00:00:00Z',
        updated_at: '2023-01-01T00:00:00Z',
      },
      {
        id: '7',
        name: 'Product 7',
        description: 'Description for Product 7',
        created_at: '2023-01-01T00:00:00Z',
        updated_at: '2023-01-01T00:00:00Z',
      },
      {
        id: '8',
        name: 'Product 8',
        description: 'Description for Product 8',
        created_at: '2023-01-01T00:00:00Z',
        updated_at: '2023-01-01T00:00:00Z',
      },
      {
        id: '9',
        name: 'Product 9',
        description:
          'Description\n for Product 9 with a long text that should be clamped to two lines. here is some more text to make it long enough.',
        created_at: '2023-01-01T00:00:00Z',
        updated_at: '2023-01-01T00:00:00Z',
      },
    ]
  }

  const filtered = computed(() => {
    const v = query.value.trim().toLowerCase()
    if (!v) return productList.value
    return productList.value.filter((p) => [p.name, p.description ?? ''].some((t) => t.toLowerCase().includes(v)))
  })

  onMounted(() => {
    productList.value = fetchProductList()
  })
</script>

<template>
  <section class="max-w-screen-xl mx-auto px-4 py-8">
    <header class="mb-8">
      <h2 class="mb-4 text-2xl font-bold">Product List</h2>

      <form
        role="search"
        class="mr-auto w-full lg:w-[30rem]"
        @submit.prevent
      >
        <input
          v-model="query"
          type="search"
          placeholder="Search products..."
          aria-label="Search products"
          aria-controls="product-list"
          class="w-full input-text-base"
        />
      </form>
    </header>

    <p
      v-if="query"
      uno-text="xs gray-500"
      class="mb-3"
    >
      {{ filtered.length }} result{{ filtered.length === 1 ? '' : 's' }} for “{{ query }}”
    </p>

    <p
      v-if="filtered.length === 0"
      class="text-sm text-gray-600"
    >
      No products{{ query ? ' match your search.' : ' yet...' }}
    </p>

    <ProductList
      v-else
      :product-list="filtered"
    />
  </section>
</template>
