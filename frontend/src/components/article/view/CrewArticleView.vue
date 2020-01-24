<template> <div></div></template>
<script>
import axios from "axios";

export default {
  props: ["articleId"],
  created() {
    const articleId = this.articleId;
    const requestUrl = "http://localhost:8080/api/articles/crew/" + articleId;

    axios
      .get(requestUrl, {
        withCredentials: true
      })
      .then(res => {
        console.log(res.data);
        const data = {
          title: res.data.title,
          content: res.data.content,
          nickname: res.data.userResponseDto.nickname,
          createdDate: res.data.createdDate,
          authorId: res.data.userResponseDto.id
        };
        console.log(data);
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
