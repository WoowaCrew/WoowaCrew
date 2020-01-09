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
            <v-btn width="400" color="red" block @click="save">저장</v-btn>
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

export default {
  components: {
    Editor
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
      isRight: true
    };
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
