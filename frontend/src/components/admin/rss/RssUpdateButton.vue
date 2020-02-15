<template>
  <v-btn text small color="orange" :disabled="!isUpdatable" @click="updateFeed">
    <v-icon>mdi-refresh</v-icon>새로고침
  </v-btn>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      isUpdatable: true
    };
  },
  methods: {
    async updateFeed() {
      if (!this.isUpdatable) {
        alert("이미 업데이트 실행 중입니다.");
        return;
      }
      this.isUpdatable = false;
      const { status: status } = await axios(
        this.$store.state.requestUrl + "/api/feeds/new",
        {
          method: "post",
          withCredentials: true
        }
      ).finally(() => {
        this.isUpdatable = true;
      });
      if (status === 200) {
        alert("업데이트 완료!");
        return;
      }
      alert("오류가 발생했습니다. 다시한번 확인해주세요");
    }
  }
};
</script>

<style>
</style>
