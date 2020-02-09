<template>
  <v-card flat>
    <v-card-text v-if="isLogin && existMessage">
      <div v-if="downloadLinkFromSlack" style="padding: 16px">
        <a :href="downloadLinkFromSlack" class="title">
          #{{ channel }} - {{ author }}
        </a>
      </div>
      <div v-else style="padding: 16px; color: gray">
        <strong class="title">#{{ channel }} - {{ author }}</strong>
      </div>
      <div
        v-if="downloadLink"
        style="padding: 12px 12px 16px 16px; background-color: #f5f5f5"
      >
        <div style="margin-bottom: 10px">첨부 파일</div>
        <a :href="downloadLink">즉시 다운로드 받을 수 있는 링크 입니다.</a>
      </div>
      <div style="padding: 12px 12px 16px 16px">
        {{ content }}
      </div>
    </v-card-text>
    <v-card-text v-else-if="!isLogin">
      로그인이 필요합니다.
    </v-card-text>
    <v-card-text v-else-if="!existMessage">
      최근 슬랙 메세지가 존재하지 않습니다.
    </v-card-text>
  </v-card>
</template>

<script>
import axios from "axios";

export default {
  name: "Notice",
  data() {
    return {
      id: null,
      channel: null,
      author: null,
      content: null,
      downloadLink: null,
      downloadLinkFromSlack: null,
      createdDate: null
    };
  },
  created() {
    const requestUrl = this.$store.state.requestUrl + "/api/slack/notice";

    axios
      .get(requestUrl, {
        withCredentials: true
      })
      .then(res => res.data)
      .then(data => {
        this.id = data.id;
        this.channel = data.channel;
        this.author = data.author;
        this.content = data.content;
        this.downloadLink = data.downloadLink;
        this.downloadLinkFromSlack = data.downloadLinkFromSlack;
        this.createdDate = data.createdDate;
      });
  },
  computed: {
    isLogin() {
      return this.$store.state.userContext != null;
    },
    existMessage() {
      return this.id != null;
    }
  }
};
</script>
