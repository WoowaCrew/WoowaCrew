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
      axios
        .get(this.$store.state.requestUrl + "/api/search/rank", {
          withCredentials: true
        })
        .then(response => {
          if (response.request.responseURL.includes("/api/search/rank")) {
            const keywords = response.data;
            IndexEventBus.$emit("keywordRank", keywords);
          }
        });
    }
  }
};
</script>
