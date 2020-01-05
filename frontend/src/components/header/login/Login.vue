<template>
  <div>
    <v-btn :href="githubLogin" color="primary">로그인</v-btn>
  </div>
</template>

<script>
export default {
  data() {
    return {
      githubLogin: "http://localhost:8080/login/github",
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
