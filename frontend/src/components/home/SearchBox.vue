<template>
  <v-text-field
    shaped
    dark
    solo
    outlined
    autocomplete="off"
    style="max-height: 56px"
    background-color="#214b7d"
    append-icon="fa-search search-icon"
    placeholder="Google 검색하기.."
    @input="setKeyword"
    @click:append="search"
    @keydown="search"
  />
</template>

<script>
import axios from "axios";

export default {
  name: "SearchBox",
  data() {
    return {
      keyword: ""
    };
  },
  methods: {
    setKeyword(keyword) {
      this.keyword = keyword;
    },
    search(key) {
      if (key.code === "Enter" || key.type === "click") {
        if (this.keyword == null || this.keyword === "") {
          return;
        }
        const formData = new FormData();
        formData.append("content", this.keyword);
        axios(this.$store.state.requestUrl + "/api/search", {
          method: "post",
          data: formData,
          withCredentials: true
        }).finally(() => {
          window.open("https://www.google.com/search?q=" + this.keyword);
        });
      }
    }
  }
};
</script>

<style>
.search-icon {
  color: rgba(255, 255, 255, 0.4) !important;
}

.search-icon:hover {
  cursor: pointer;
}
</style>
