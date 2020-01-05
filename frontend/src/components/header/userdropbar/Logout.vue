<template>
  <v-list-item @click="logout">
    <p class="body-2 white--text">
      로그아웃
    </p>
  </v-list-item>
</template>

<script>
export default {
  methods: {
    logout() {
      this.$store.dispatch("logout");
    }
  }
};
</script>

<style></style>
