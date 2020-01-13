<template>
  <v-container fill-height>
    <FreeArticleView
      :articleId="this.$route.params.articleId"
      @setupData="setData"
    ></FreeArticleView>
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
import FreeArticleView from "./view/FreeArticleView";

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
    viewer: Viewer,
    FreeArticleView
  },
  computed: {
    dateCut() {
      return this.createdDate.split("T")[0];
    }
  },
  methods: {
    setData(data) {
      this.title = data.title;
      this.content = data.content;
      this.nickname = data.nickname;
      this.createdDate = data.createdDate;
    }
  }
};
</script>

<style></style>
