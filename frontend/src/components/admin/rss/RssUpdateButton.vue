<template>
  <v-btn width="50" color="orange" style="color: white" @click="updateFeed">
    <v-icon>mdi-refresh</v-icon>
  </v-btn>
</template>

<script>
import axios from "axios";

export default {
  methods: {
    updateFeed() {
      axios(this.$store.state.requestUrl + "/api/feeds/new", {
        method: "post",
        withCredentials: true
      })
        .then(res => {
          if (res.status != 200) {
            alert("오류가 발생했습니다. 다시한번 확인해주세요");
            return;
          }
          alert("업데이트 완료!");
        })
        .catch(error => {
          error.data;
          alert("에러가 발생했습니다. 다시한번 확인해주세요");
          return;
        });
    }
  }
};
</script>

<style></style>
