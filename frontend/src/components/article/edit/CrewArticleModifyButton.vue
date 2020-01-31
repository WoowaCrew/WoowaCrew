<template>
  <v-btn width="400" block @click="save" style="color: ghostwhite !important;">
    수정
  </v-btn>
</template>

<script>
import axios from "axios";

export default {
  props: ["articleForm", "articleId"],
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
      formData.append("articleId", this.articleId);
      formData.append("title", title);
      formData.append("content", content);
      axios(this.$store.state.requestUrl + "/api/articles/crew/" + this.articleId, {
        method: "put",
        data: formData,
        withCredentials: true
      }).then(res => {
        if (res.status !== 200) {
          alert("게시글 수정에 실패하였습니다.");
          return;
        }
        alert("정상적으로 수정되었습니다");
        window.location.href = "/articles/crew/" + this.articleId;
      });
    }
  },
  created() {
    const articleId = this.articleId;
    const requestUrl = this.$store.state.requestUrl + "/api/articles/crew/" + articleId;
    axios
      .get(requestUrl, {
        withCredentials: true
      })
      .then(res => {
        const data = {
          title: res.data.title,
          content: res.data.content,
          nickname: res.data.userResponseDto.nickname,
          createdDate: res.data.createdDate
        };
        this.$emit("setupData", data);
        this.title = res.data.title;
        this.content = res.data.content;
      });
  }
};
</script>

<style></style>
