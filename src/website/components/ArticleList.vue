<template>
  <div class="w-100">
    <div v-if="articles.length > 0" class="w-100 card-columns" style="display: inline-block;">
      <div
        v-for="article in articles"
        :key="article.id"
        class="card article-entry mb-2"
        >
        <div class="card-body">
          <h5 class="card-title text-left">{{ article.title }}</h5>
          <p class="card-text text-right">Author: {{ article.author }}</p>
          <a :href="`/article/${article.id}/`" class="card-link">Read</a>
        </div>
      </div>
    </div>
    <div v-else>
      No articles yet.
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.component('article-list', {
  data() {
    return {
      'articles': [],
    }
  },
  async fetch() {
    if (this.$nuxt.context.isDev) {
      console.log(`ArticleList.fetch() called`)
      console.log(`    process.server: ${process.server}`)
      console.log(`    this.$nuxt.context.$config.articleBaseUrl: ${this.$nuxt.context.$config.articleBaseUrl}`)
      console.log(`    this.$nuxt.context.$config.rankBaseUrl: ${this.$nuxt.context.$config.rankBaseUrl}`)
    }
    // const articleList = await this.$axios.$get(`${this.$nuxt.context.$config.articleBaseUrl}/api/articles/`)
    // for (const article of articleList) {
    //   this.$data.articles.push(article)
    // }
    this.$data.articles = await this.$axios.$get(`${this.$nuxt.context.$config.articleBaseUrl}/api/articles/`)
  },
})
</script>
