<template>
  <div class="text-left">
    <h5>Access Ranking</h5>
    <ol>
      <li
        v-for="rank of dailyRanking.total"
        :key="rank.articleId">
        <a
          :href="`/article/${rank.articleId}/`"
          >
          [id={{rank.articleId}}](TODO) How to get title here
        </a>
      </li>
    </ol>
    <h5>Access User Ranking</h5>
    <ol>
      <li
        v-for="rank of dailyRanking.unique"
        :key="rank.articleId">
        <a
          :href="`/article/${rank.articleId}/`"
          >
          [id={{rank.articleId}}](TODO) How to get title here
        </a>
      </li>
    </ol>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.component('sidebar-outer', {
  data() {
    return {
      'dailyRanking': {},
    }
  },
  async fetch() {
    if (this.$nuxt.context.isDev) {
      console.log(`Sidebar.fetch() called`)
      console.log(`    process.server: ${process.server}`)
      console.log(`    this.$nuxt.context.$config.articleBaseUrl: ${this.$nuxt.context.$config.articleBaseUrl}`)
      console.log(`    this.$nuxt.context.$config.rankBaseUrl: ${this.$nuxt.context.$config.rankBaseUrl}`)
    }
    const yesterday = new Date()
    yesterday.setDate(yesterday.getDate() - 1)
    const year = yesterday.getFullYear().toString().padStart(4, '0')
    const month = (yesterday.getMonth() + 1).toString().padStart(2, '0')
    const dayOfMonth = yesterday.getDate().toString().padStart(2, '0')
    const yesterdayISO8601 = `${year}-${month}-${dayOfMonth}`

    const url = `${this.$nuxt.context.$config.rankBaseUrl}/api/ranks/daily/?date=${yesterdayISO8601}`
    this.$data.dailyRanking = await this.$axios.$get(url)

  },

})

</script>

<style scoped>
li > a {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
</style>
