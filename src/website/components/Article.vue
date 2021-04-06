<template>
  <div>
    <h3>{{ article.title }}</h3>
    <p class="text-right">Author: {{ article.author }}</p>
    <div class="text-left">{{ article.body }}</div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  name: 'article-view',
  data() {
    return {
      'article': {},
    }
  },
  async fetch() {
    const articleBaseUrl = process.server ? this.$nuxt.context.env.articleBaseUrlFromServer : this.$nuxt.context.env.articleBaseUrlFromClient
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
