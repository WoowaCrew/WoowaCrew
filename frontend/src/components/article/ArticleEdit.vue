<template>
  <v-container>
    <v-layout row wrap>
      <v-flex fill-height>
        <v-card width="1000" class="overflow-hidden mx-auto my-2">
          <v-bottom-navigation
            height="30"
            scroll-target="#scroll-area-1"
            absolute
            horizontal
            style="background-color: #1976d2"
          >
            <FreeArticleEditButton
              v-if="isFreeArticleEdit"
              :articleForm="{
                title: this.title,
                content: this.editorText
              }"
            ></FreeArticleEditButton>
            <FreeArticleModifyButton
              v-if="isFreeArticleModify"
              :articleId="this.$route.params.articleId"
              :articleForm="{
                id: this.articleId,
                title: this.title,
                content: this.editorText
              }"
              @setupData="setData"
            />
            <CrewArticleEditButton
              v-if="isCrewArticleEdit"
              :articleForm="{
                title: this.title,
                content: this.editorText
              }"
            ></CrewArticleEditButton>
            <CrewArticleModifyButton
              v-if="isCrewArticleModify"
              :articleId="this.$route.params.articleId"
              :articleForm="{
                id: this.articleId,
                title: this.title,
                content: this.editorText
              }"
              @setupData="setData"
            />
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
import FreeArticleEditButton from "./edit/FreeArticleEditButton";
import FreeArticleModifyButton from "./edit/FreeArticleModifyButton";
import CrewArticleEditButton from "./edit/CrewArticleEditButton";
import CrewArticleModifyButton from "./edit/CrewArticleModifyButton";

export default {
  components: {
    Editor,
    FreeArticleEditButton,
    FreeArticleModifyButton,
    CrewArticleEditButton,
    CrewArticleModifyButton
  },
  data() {
    return {
      articleId: this.$route.params.articleId,
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
    isFreeArticleEdit() {
      return this.path === "/article/free/new";
    },
    isFreeArticleModify() {
      const freeArticleModifyPattern = new RegExp(
        "/articles/free/[0-9]+/modify"
      );

      return freeArticleModifyPattern.test(this.path);
    },
    isCrewArticleEdit() {
      return this.path === "/article/crew/new";
    },
    isCrewArticleModify() {
      const crewArticleModifyPattern = new RegExp(
        "/articles/crew/[0-9]+/modify"
      );
      return crewArticleModifyPattern.test(this.path);
    }
  },
  methods: {
    setData(data) {
      this.title = data.title;
      this.editorText = data.content;
    }
  }
};
</script>

<style></style>
