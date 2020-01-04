<template>
  <div>
    <v-btn small color="primary">{{ setNickname }}</v-btn>
  </div>
</template>

<script>
export default {
  computed: {
    setNickname() {
      const nickname = this.$store.state.userContext.nickname;
      if (nickname === null) {
        return "닉네임 미설정";
      }
      return nickname;
    }
  }
};
</script>

<style></style>
