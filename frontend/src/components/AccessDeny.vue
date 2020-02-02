<template>
  <v-container fill-height fluid>
    <v-row align="center" justify="center">
      <v-col>
        <p class="text-center my-auto py-12">{{ message }}</p>
        <v-btn :href="link" text block class="red--text">
          {{ linkMessage }}
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      message: "접근할 수 없습니다.",
      linkMessage: "홈으로 돌아가기",
      link: ""
    };
  },
  created() {
    const user = this.$store.state.userContext;
    if (user == null) {
      this.message = "로그인이 필요합니다.";
      this.linkMessage = "로그인 하러가기";
      this.link = this.$store.state.requestUrl + "/login/github";
      return;
    }
    if (user.nickname == null) {
      this.message = "닉네임 설정이 필요합니다.";
      this.linkMessage = "닉네임 설정 하러가기";
      this.link = "/user/edit";
      return;
    }
    if (user.role === "ROLE_PRECOURSE") {
      this.message = "관리자의 승인이 필요합니다.";
      this.linkMessage = "홈으로 돌아가기";
      this.link = "/";
      return;
    }
    this.message = "접근할 수 없는 페이지입니다.";
    this.linkMessage = "홈으로 돌아가기";
    this.link = "/";
  }
};
</script>

<style></style>
