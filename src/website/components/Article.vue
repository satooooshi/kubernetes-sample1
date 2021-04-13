<template>
  <div>
    <h3>{{ article.title }}</h3>
    <p class="text-right">Author: {{ article.author }}</p>
    <div class="text-left" v-html="article.body"></div>
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
    if (this.$nuxt.context.isDev) {
      console.log(`Article.fetch() called`)
      console.log(`    process.server: ${process.server}`)
      console.log(`    this.$nuxt.context.$config.articleBaseUrl: ${this.$nuxt.context.$config.articleBaseUrl}`)
      console.log(`    this.$nuxt.context.$config.rankBaseUrl: ${this.$nuxt.context.$config.rankBaseUrl}`)
    }
    const url = `${this.$nuxt.context.$config.articleBaseUrl}/api/articles/${this.$route.params.id}`
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
