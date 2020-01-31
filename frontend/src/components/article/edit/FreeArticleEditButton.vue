<template>
  <v-btn width="400" block @click="save" style="color: ghostwhite !important;">
    저장
  </v-btn>
</template>

<script>
import axios from "axios";

export default {
  props: ["articleForm"],
  methods: {
    save() {
      const title = this.articleForm.title;
      const content = this.articleForm.content;
      if (title.trim().length === 0) {
        alert("제목을 입력해주세요");
        return;
      }
      if (content.trim().length === 0) {
        alert("내용을 입력해주세요");
        return;
      }
      const formData = new FormData();
      formData.append("title", title);
      formData.append("content", content);
      axios(this.$store.state.requestUrl + "/api/articles", {
        method: "post",
        data: formData,
        withCredentials: true
      }).then(res => {
        if (res.status !== 201) {
          alert("게시글 저장에 실패하였습니다.");
          return;
        }
        const articleId = res.data.id;
        alert("정상적으로 저장되었습니다");
        window.location.href = "/articles/free/" + articleId;
      });
    }
  }
};
</script>

<style></style>
