<template>
  <div>
    <h3>{{ article.title }}</h3>
    <p class="text-right">Author: {{ article.author }}</p>
    <div class="text-left">{{ article.body }}</div>
  </div>
</template>

<script>
import Vue from 'vue'

export default Vue.component('article-view', {
  data() {
    return {
      'article': {},
    }
  },
  async fetch() {
    const articleBaseUrl = process.client ? process.env.articleBaseUrlFromClient : process.env.articleBaseUrlFromServer
    const url = `${articleBaseUrl}/api/articles/${this.$route.params.id}`
    const options = {
      'headers': {
        'X-UID': 'hoge',
      },
    }
    this.$data.article = await this.$axios.$get(url, options)
  }
})
</script>

<style scoped>

</style>
