<template><div></div></template>

<script>
import axios from "axios";

export default {
  props: ["articleId"],
  created() {
    const articleId = this.articleId;
    const requestUrl = "http://localhost:8080/api/slack/notice/" + articleId;

    axios.get(requestUrl).then(res => {
      const data = {
        channel: res.data.channel,
        author: res.data.author,
        content: res.data.content,
        downloadLink: res.data.downloadLink,
        downloadLinkFromSlack: res.data.downloadLinkFromSlack,
        createdDate: res.data.createdDate
      };
      this.$emit("setupData", data);
    });
  }
};
</script>
