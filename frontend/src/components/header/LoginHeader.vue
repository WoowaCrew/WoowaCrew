<template>
  <div>
    <Login v-if="isNotLogin"></Login>
    <Profile v-else></Profile>
  </div>
</template>

<script>
import Login from "./login/Login";
import Profile from "./login/Profile";
export default {
  components: {
    Login,
    Profile
  },
  created() {
    this.$store.dispatch("checkLogin");
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
