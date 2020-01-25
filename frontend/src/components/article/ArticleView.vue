<template>
  <v-container fill-height>
    <FreeArticleView
      v-if="isFreeArticleView"
      :articleId="this.$route.params.articleId"
      @setupData="setData"
    ></FreeArticleView>
    <CrewArticleView
      v-if="isCrewArticleView"
      :articleId="this.$route.params.articleId"
      @setupData="setData"
    ></CrewArticleView>
    <v-layout row wrap>
      <v-flex fill-height>
        <v-card width="1000" class="overflow-hidden mx-auto my-2 fill-height">
          <div style="padding: 30px">
            <v-card-title class="justify-center">
              <h1>
                {{ title }}
              </h1>
            </v-card-title>
            <v-card-title class="justify-right" style="border-bottom: 1px solid #333; color: gray; font-size: 18px">
              {{ nickname }}
              <v-spacer></v-spacer>
              {{ dateCut }}
            </v-card-title>
          </div>
          <v-card-text style="padding: 0 60px 60px;">
            <viewer :value="content" height="500px" />
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
    <v-btn
      v-if="isAuthor"
      style="right: 100px"
      class="right-100"
      fab
      dark
      large
      color="primary"
      fixed
      right
      bottom
      @click="
        $router
          .push({
            name: editPath,
            params: {
              articleId: $route.params.articleId
            }
          })
          .catch(err => {})
      "
    >
      <v-icon>fa-edit</v-icon> </v-btn
    ><v-btn
      v-if="isAuthor"
      fab
      dark
      large
      color="red"
      fixed
      right
      bottom
      @click="deleteArticle"
    >
      <v-icon>fa-trash</v-icon>
    </v-btn>
  </v-container>
</template>

<script>
import "tui-editor/dist/tui-editor-contents.css";
import "highlight.js/styles/github.css";
import { Viewer } from "@toast-ui/vue-editor";
import axios from "axios";
import FreeArticleView from "./view/FreeArticleView";
import CrewArticleView from "./view/CrewArticleView";

export default {
  data() {
    return {
      title: "",
      content: "",
      nickname: "",
      createdDate: "",
      authorId: "",
      path: this.$route.path,
      editPath: ""
    };
  },
  components: {
    viewer: Viewer,
    FreeArticleView,
    CrewArticleView
  },
  computed: {
    isAuthor() {
      const user = this.$store.state.userContext;
      console.log(user);
      if (user == null) {
        return false;
      }
      return this.authorId === user.id;
    },
    dateCut() {
      return this.createdDate.split("T")[0];
    },
    isFreeArticleView() {
      const freeArticleViewPattern = new RegExp("/articles/free/[0-9]+");

      return freeArticleViewPattern.test(this.path);
    },
    isCrewArticleView() {
      const crewArticleViewPattern = new RegExp("/articles/crew/[0-9]+");
      console.log(crewArticleViewPattern.test(this.path));
      return crewArticleViewPattern.test(this.path);
    }
  },
  methods: {
    setData(data) {
      this.title = data.title;
      this.content = data.content;
      this.nickname = data.nickname;
      this.createdDate = data.createdDate;
      this.authorId = data.authorId;
    },
    deleteArticle() {
      const articleId = this.$route.params.articleId;
      let apiPath = "";
      let hrefPath = "";
      console.log("articleId:" + articleId);
      if (this.isFreeArticleView) {
        apiPath = "/api/articles/";
        hrefPath = "/articles/free";
      }
      if (this.isCrewArticleView) {
        apiPath = "/api/articles/crew/";
        hrefPath = "/articles/crew";
      }

      axios("http://localhost:8080" + apiPath + articleId, {
        method: "delete",
        withCredentials: true
      }).then(res => {
        if (res.status !== 200) {
          alert("게시글 삭제에 실패하였습니다.");
          return;
        }
        alert("정상적으로 삭제되었습니다");
        window.location.href = hrefPath;
      });
    }
  },
  created() {
    const freeArticleViewPattern = new RegExp("/articles/free/[0-9]+");
    const crewArticleViewPattern = new RegExp("/articles/crew/[0-9]+");

    if (freeArticleViewPattern.test(this.path)) {
      this.editPath = "freeArticleModify";
    }
    if (crewArticleViewPattern.test(this.path)) {
      this.editPath = "crewArticleModify";
    }
  }
};
</script>

<style>
.right-100 {
  right: 100px;
}
</style>
