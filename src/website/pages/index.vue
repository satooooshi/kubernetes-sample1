<template>
  <div class="container">
    <Header />
    <div class="content-row row w-100">
      <div class="col-lg-9 col-md-10">
        <ArticleList />
      </div>
      <div class="col-lg-3 col-md-2">
        <Sidebar />
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({})
</script>

<style>
.container {
  margin: 0 auto;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
}
.content-row {
  margin-top: 80px;
}
</style>
