<template>
  <v-container fill-height>
    <v-layout row wrap>
      <v-flex fill-height>
        <v-card width="1000" class="overflow-hidden mx-auto my-2 fill-height">
          <v-card-title class="justify-center">
            <h1>
              {{ title }}
            </h1>
          </v-card-title>
          <v-card-title class="justify-right" style="border-bottom: 1px solid">
            {{ nickname }}
            <v-spacer></v-spacer>
            {{ dateCut }}
          </v-card-title>
          <v-card-text>
            <viewer :value="content" height="500px" />
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import "tui-editor/dist/tui-editor-contents.css";
import "highlight.js/styles/github.css";
import { Viewer } from "@toast-ui/vue-editor";
import axios from "axios";

export default {
  data() {
    return {
      title: "",
      content: "",
      nickname: "",
      createdDate: ""
    };
  },
  components: {
    viewer: Viewer
  },
  computed: {
    dateCut() {
      return this.createdDate.split("T")[0];
    }
  },
  created() {
    const articleId = this.$route.params.articleId;
    console.log(articleId);
    const requestUrl = "http://localhost:8080/api/articles/" + articleId;
    console.log(requestUrl);
    axios
      .get(requestUrl, {
        withCredentials: true
      })
      .then(res => {
        this.title = res.data.title;
        this.content = res.data.content;
        this.nickname = res.data.userResponseDto.nickname;
        this.createdDate = res.data.createdDate;
      });
  }
};
</script>

<style></style>
