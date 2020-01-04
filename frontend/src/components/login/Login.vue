<template>
  <div>
    <v-btn :href="githubLogin" small color="primary">로그인</v-btn>
  </div>
</template>

<script>
export default {
  data() {
    return {
      githubLogin: "http://localhost:8080/login/github"
    };
  }
};
</script>

<style></style>
