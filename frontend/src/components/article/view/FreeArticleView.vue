<template> <div></div></template>
<script>
import axios from "axios";

export default {
  props: ["articleId"],
  created() {
    const articleId = this.articleId;
    const requestUrl = this.$store.state.requestUrl + "/api/articles/" + articleId;

    axios
      .get(requestUrl, {
        withCredentials: true
      })
      .then(res => {
        const data = {
          title: res.data.title,
          content: res.data.content,
          nickname: res.data.userResponseDto.nickname,
          createdDate: res.data.createdDate,
          authorId: res.data.userResponseDto.id
        };
        this.$emit("setupData", data);
        this.title = res.data.title;
        this.content = res.data.content;
        this.nickname = res.data.userResponseDto.nickname;
        this.createdDate = res.data.createdDate;
      });
  }
};
</script>

<style></style>
