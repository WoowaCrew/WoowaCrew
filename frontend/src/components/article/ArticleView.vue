<template>
  <v-container fill-height>
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
      <v-layout row wrap class="mb-10">
        <v-flex fill-height>
          <v-card width="1000" class="overflow-hidden mx-auto my-2 fill-height">
            <v-card-title class="justify-center">
              <h1>
                {{ title }}
              </h1>
            </v-card-title>
            <v-card-title
              class="justify-right"
              style="border-bottom: 1px solid"
            >
              {{ nickname }}
              <v-spacer></v-spacer>
              {{ dateCut }}
            </v-card-title>
            <v-card-text>
              <viewer :value="content" height="500px" />
            </v-card-text>
          </v-card>
        </v-flex>
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
          <v-icon>fa-minus</v-icon>
        </v-btn>
      </v-layout>
    </v-container>
    <v-container v-if="isFreeArticleView">
      <v-row justify="center">
        <v-col sm="6">
          <v-row>
            <v-textarea
              v-model="comment"
              label="댓글을 작성해 주세요."
              auto-grow
              full-width
              outlined
              rows="1"
              row-height="15"
              class="mr-10"
            ></v-textarea>
            <v-btn height="50" color="primary" @click="saveComment">
              작성
            </v-btn>
          </v-row>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col sm="6">
          <v-row v-for="item in comments" :key="item.id" :id="item.id">
            <v-card max-width="100%" min-width="100%" class="mb-5">
              <v-card-title>{{ item.id }}</v-card-title>
              <v-card-subtitle>{{ item.userNickName }}</v-card-subtitle>
              <v-card-text>{{ item.content }}</v-card-text>
            </v-card>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
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
      editPath: "",
      comment: "",
      comments: [
        { id: 1, name: "van", content: "hihi1" },
        { id: 2, name: "van", content: "hihi2" },
        { id: 3, name: "van", content: "hihi3" }
      ]
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
    },
    saveComment() {
      const comment = this.comment;
      const articleId = this.$route.params.articleId;
      const formData = new FormData();
      formData.append("content", comment);
      //Todo: 자유게시판, 크루게시판 별로 댓글 목록 불러오게끔
      axios("http://localhost:8080/api/articles/" + articleId + "/comments", {
        method: "post",
        data: formData,
        withCredentials: true
      }).then(res => {
        if (res.status !== 200) {
          alert("댓글 저장에 실패하였습니다.");
          return;
        }
        alert("정상적으로 저장되었습니다");
        location.reload(true);
      });
    }
  },
  created() {
    const freeArticleViewPattern = new RegExp("/articles/free/[0-9]+");
    const crewArticleViewPattern = new RegExp("/articles/crew/[0-9]+");
    const articleId = this.$route.params.articleId;

    if (freeArticleViewPattern.test(this.path)) {
      this.editPath = "freeArticleModify";
    }
    if (crewArticleViewPattern.test(this.path)) {
      this.editPath = "crewArticleModify";
    }

    axios
      .get("http://localhost:8080/api/articles/" + articleId + "/comments", {
        withCredentials: true
      })
      .then(res => {
        console.log(res.data);
        this.comments = res.data;
      });
  }
};
</script>

<style>
.right-100 {
  right: 100px;
}
</style>
