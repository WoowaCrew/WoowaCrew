<template>
  <v-container fill-height fluid>
    <v-row align="center" justify="center">
      <v-col>
        <p class="text-center my-auto py-12">로그인이 필요합니다.</p>
        <v-btn :href="link" text block class="red--text">
          로그인 하러가기
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      link: this.$store.state.requestUrl + "/login/github"
    };
  }
};
</script>

<style></style>
