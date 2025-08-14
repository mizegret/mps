<template>
  <header
    uno-border="b gray-200"
    class="bg-white w-full"
  >
    <div class="container mx-auto flex-between app-pad">
      <div>
        <router-link
          to="/"
          class="iflex-2"
        >
          <img
            src="/icon.svg"
            alt="logo"
            class="h-6 w-6"
          />
          <h1 class="text-lg font-semibold hidden sm:block">MPS Product Survey</h1>
        </router-link>
      </div>
      <nav
        uno-text="sm gray-600 [&>a:hover]:gray-900 [&_.router-link-exact-active]:gray-900"
        class="iflex-6 [&_.router-link-exact-active]:font-bold"
      >
        <router-link to="/">Home</router-link>
        <router-link to="/products/new">New Product</router-link>
      </nav>
    </div>
  </header>
</template>
