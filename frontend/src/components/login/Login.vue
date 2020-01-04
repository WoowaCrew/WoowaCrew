<template>
  <div>
    <v-btn :href="githubLogin" v-if="isNotLogin" small color="primary"
      >로그인</v-btn
    >
  </div>
</template>

<script>
export default {
  data() {
    return {
      githubLogin: "http://localhost:8080/login/github",
      isNotLogin: this.$store.state.userContext === null ? true : false
    };
  },
  methods: {
    checkLogin() {
      alert("체크 로그인");
    }
  }
};
</script>

<style></style>
