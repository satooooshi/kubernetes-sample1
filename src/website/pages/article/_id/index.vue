<template>
  <div class="container">
    <Header />
    <div class="content-row row w-100">
      <div class="col-lg-9 col-md-10">
        <Article />
      </div>
      <div class="col-lg-3 col-md-2">
        <Sidebar />
      </div>
    </div>
  </div>
</template>

<script lang="ts">

import Vue from 'vue'

export default Vue.extend({
  data() {
    return {
      'article': {},
    }
  },
  async fetch() {
    const url = `http://localhost:8081/articles/${this.$route.params.id}/`
    const options = {
      'headers': {
        'X-UID': 'hoge',
      },
    }
    const article = await this.$axios.$get(url, options)
    this.$data.article = article
  },
})

</script>

<style scoped>
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
