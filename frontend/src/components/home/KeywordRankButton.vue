<template>
  <v-btn @click="findKeywordRank">
    <span>검색어 순위</span>
    <v-icon>mdi-chart-bar</v-icon>
  </v-btn>
</template>

<script>
import axios from "axios";
import IndexEventBus from "./IndexEventBus";
export default {
  name: "KeywordRankButton",
  methods: {
    findKeywordRank() {
      axios.get("http://localhost:8080/api/search/rank").then(response => {
        const keywords = response.data;
        IndexEventBus.$emit("keywordRank", keywords);
      });
    }
  }
};
</script>
