<template>
  <v-card flat>
    <v-card-text>
      <div class="row" style="padding: 16px">
        <strong class="title">검색어 순위</strong>
        <v-icon @click="updateKeywordRank" style="margin-left: 5px">
          mdi-refresh
        </v-icon>
      </div>
      <v-list>
        <v-list-item
          v-for="(keyword, index) in keywords"
          :key="keyword.id"
          @click="search(keyword)"
        >
          <v-list-item-icon>
            <v-icon color="black">
              mdi-numeric-{{ index + 1 }}-box-outline
            </v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title v-text="keyword.content" />
          </v-list-item-content>

          <v-list-item-icon>
            <span style="margin-right: 5px">{{ keyword.views }}</span>
            <v-icon color="pink">
              mdi-arrow-up-bold-hexagon-outline
            </v-icon>
          </v-list-item-icon>
        </v-list-item>
      </v-list>
    </v-card-text>
  </v-card>
</template>

<script>
import axios from "axios";
import IndexEventBus from "./IndexEventBus";
export default {
  name: "KeywordRank",
  data: () => ({
    keywords: null
  }),
  methods: {
    updateKeywordRank() {
      axios
        .get(this.$store.state.requestUrl + "/api/search/rank", {
          withCredentials: true
        })
        .then(response => {
          if (response.request.responseURL.includes("/api/search/rank")) {
            this.keywords = response.data;
          }
        });
    },
    search(keyword) {
      axios.get(this.$store.state.requestUrl + "/api/search/" + keyword.id, {
        withCredentials: true
      }).finally(() => {
        window.open("https://www.google.com/search?q=" + keyword.content);
      });
    }
  },
  created() {
    const keywordRank = this;
    IndexEventBus.$on("keywordRank", function(keywords) {
      keywordRank.keywords = keywords;
    });
  }
};
</script>
