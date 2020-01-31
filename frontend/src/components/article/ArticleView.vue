<template>
  <v-container>
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
            <v-card-title style="border-bottom: 1px solid rgba(0, 0, 0, 0.1)">
              <h1>{{ title }}</h1>
              <v-spacer />
              <div class="text-right">
                <p
                  style="font-size: 12px; color: #666666; margin: 0; line-height: 1.2rem"
                >
                  {{ dateCut }}
                </p>
                <p style="margin: 0">{{ nickname }}</p>
              </div>
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
      <v-icon>fa-edit</v-icon>
    </v-btn>
    <v-btn
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
    <v-container v-if="isFreeArticleView" style="max-width: 1000px">
      <h3 style="color: #333; margin-top: 25px">댓글 {{ comments.length }}</h3>
      <v-row justify="center">
        <v-col style="padding-right: 25px; padding-left: 25px">
          <v-row v-for="item in comments" :key="item.id" :id="item.id">
            <v-card max-width="100%" min-width="100%" class="mb-5">
              <v-card-subtitle style="padding-bottom: 0">
                {{ item.userResponseDto.nickname }} &ndash;
                {{ convert(item.createDateTime) }}
              </v-card-subtitle>
              <v-card-title>
                <div style="font-size: 18px">{{ item.content }}</div>
                <v-spacer />
                <div v-if="isCommentAuthor(item.userResponseDto.id)">
                  <v-dialog v-model="form.dialog[item.id]" width="500">
                    <template v-slot:activator="{ on }">
                      <v-btn icon v-on="on">
                        <v-icon size="1.2rem">fa-edit</v-icon>
                      </v-btn>
                    </template>

                    <v-card>
                      <v-card-title
                        class="headline primary"
                        style="color: white"
                      >
                        Comment 💬
                      </v-card-title>

                      <v-card-text style="padding: 25px 15px 15px;">
                        <v-form>
                          <v-textarea
                            v-model="form.comment[item.id]"
                            label="댓글을 작성해 주세요."
                            auto-grow
                            outlined
                          />
                        </v-form>
                      </v-card-text>

                      <v-divider></v-divider>

                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn
                          color="red"
                          text
                          @click="form.dialog[item.id] = false"
                        >
                          취소
                        </v-btn>
                        <v-btn
                          color="primary"
                          text
                          @click="editComment(item.id)"
                        >
                          수정
                        </v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                  <v-btn icon @click="deleteComment(item.id)">
                    <v-icon size="1.2rem">fa-trash</v-icon>
                  </v-btn>
                </div>
              </v-card-title>
            </v-card>
          </v-row>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col style="padding-right: 25px; padding-left: 25px">
          <v-row>
            <v-textarea
              v-model="comment"
              label="댓글을 작성해 주세요. 💬"
              auto-grow
              outlined
              row-height="9"
              class="mr-3"
            />
            <v-btn height="57" color="primary" @click="saveComment">작성</v-btn>
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
import dateConverter from "../../store/dateConverter";
import FreeArticleView from "./view/FreeArticleView";
import CrewArticleView from "./view/CrewArticleView";

export default {
  data() {
    return {
      form: {
        dialog: [],
        comment: []
      },
      title: "",
      content: "",
      nickname: "",
      createdDate: "",
      authorId: "",
      path: this.$route.path,
      editPath: "",
      comment: "",
      dialog: false,
      comments: []
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
      return crewArticleViewPattern.test(this.path);
    }
  },
  methods: {
    setData(data) {
      this.title = data.title;
      this.content = data.content;
      this.nickname = data.nickname;
      this.createdDate = dateConverter(data.createdDate);
      this.authorId = data.authorId;
    },
    isCommentAuthor(commentAuthorId) {
      const user = this.$store.state.userContext;
      if (user == null) {
        return false;
      }
      return commentAuthorId === user.id;
    },
    deleteArticle() {
      const articleId = this.$route.params.articleId;
      let apiPath = "";
      let hrefPath = "";
      if (this.isFreeArticleView) {
        apiPath = "/api/articles/";
        hrefPath = "/articles/free";
      }
      if (this.isCrewArticleView) {
        apiPath = "/api/articles/crew/";
        hrefPath = "/articles/crew";
      }

      axios(this.$store.state.requestUrl + apiPath + articleId, {
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
      axios(this.$store.state.requestUrl + "/api/articles/" + articleId + "/comments", {
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
    },
    editComment(commentId) {
      const comment = this.form.comment[commentId];
      const articleId = this.$route.params.articleId;
      const formData = new FormData();
      formData.append("updateContent", comment);
      axios(
        this.$store.state.requestUrl + "/api/articles/" +
          articleId +
          "/comments/" +
          commentId,
        {
          method: "put",
          data: formData,
          withCredentials: true
        }
      ).then(res => {
        if (res.status !== 200) {
          alert("댓글 수정에 실패하였습니다.");
          return;
        }
        alert("정상적으로 수정되었습니다");
        location.reload(true);
      });
    },
    deleteComment(commentId) {
      const articleId = this.$route.params.articleId;
      axios(
        this.$store.state.requestUrl + "/api/articles/" +
          articleId +
          "/comments/" +
          commentId,
        {
          method: "delete",
          withCredentials: true
        }
      ).then(res => {
        if (res.status !== 200) {
          alert("댓글 삭제에 실패하였습니다.");
          return;
        }
        alert("정상적으로 삭제되었습니다");
        location.reload(true);
      });
    },
    convert(date) {
      return dateConverter(date);
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
      .get(this.$store.state.requestUrl + "/api/articles/" + articleId + "/comments", {
        withCredentials: true
      })
      .then(res => {
        res.data.forEach(element => {
          this.form.comment[element.id] = element.content;
        });
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
