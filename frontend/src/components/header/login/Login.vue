<template>
  <div>
    <v-btn :href="githubLogin" text class="white--text">로그인</v-btn>
  </div>
</template>

<script>
export default {
  data() {
    return {
      githubLogin: this.$store.state.requestUrl + "/login/github",
      nickname: "닉네임"
    };
  },
  computed: {
    isNotLogin() {
      if (this.$store.state.userContext === null) {
        return true;
      }
      return false;
    }
  }
};
</script>

<style></style>
