<template>
  <v-container>
    <v-layout row wrap>
      <v-flex fill-height>
        <v-card width="1000" class="overflow-hidden mx-auto my-2">
          <v-bottom-navigation
            height="20"
            scroll-target="#scroll-area-1"
            absolute
            horizontal
          >
            <FreeArticleEditButton
              v-if="idFreeArticleEdit"
              :articleForm="{
                title: this.title,
                content: this.editorText
              }"
            ></FreeArticleEditButton>

            <CrewArticleEditButton
              v-if="idCrewArticleEdit"
              :articleForm="{
                title: this.title,
                content: this.editorText
              }"
            ></CrewArticleEditButton>
          </v-bottom-navigation>
          <v-card-title>
            <v-text-field
              v-model="title"
              label="제목을 입력해주세요"
              single-line
            ></v-text-field>
          </v-card-title>
          <v-card-text>
            <editor
              v-model="editorText"
              :options="editorOptions"
              :html="editorHtml"
              :visible="editorVisible"
              previewStyle="vertical"
              height="600px"
              mode="markdown"
            />
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import "tui-editor/dist/tui-editor.css";
import "tui-editor/dist/tui-editor-contents.css";
import "codemirror/lib/codemirror.css";
import { Editor } from "@toast-ui/vue-editor";
import axios from "axios";
import FreeArticleEditButton from "./edit/FreeArticleEditButton";
import CrewArticleEditButton from "./edit/CrewArticleEditButton";

export default {
  components: {
    Editor,
    FreeArticleEditButton,
    CrewArticleEditButton
  },
  data() {
    return {
      title: "",
      editorText: "",
      editorOptions: {
        hideModeSwitch: true
      },
      editorHtml: "",
      editorVisible: true,
      isRight: true,
      path: this.$route.path
    };
  },
  computed: {
    idFreeArticleEdit() {
      console.log(this.path);
      return this.path === "/article/free/new";
    },
    idCrewArticleEdit() {
      console.log(this.path);
      return this.path === "/article/crew/new";
    }
  },
  methods: {
    save() {
      if (this.title.trim().length === 0) {
        alert("제목을 입력해주세요");
        return;
      }
      if (this.editorText.trim().length === 0) {
        alert("내용을 입력해주세요");
        return;
      }
      const formData = new FormData();
      formData.append("title", this.title);
      formData.append("content", this.editorText);
      axios("http://localhost:8080/api/articles", {
        method: "post",
        data: formData,
        withCredentials: true
      }).then(res => {
        const articleId = res.data.id;
        alert("정상적으로 저장되었습니다");
        window.location.href = "/articles/" + articleId;
      });
    }
  }
};
</script>

<style></style>
