<template>
  <div class="text-center">
    <v-menu offset-y>
      <template v-slot:activator="{ on }">
        <v-btn color="primary" v-on="on">{{ setNickname }}</v-btn>
      </template>
      <v-list dense small color="accent">
        <AdminPage v-if="isAdmin"></AdminPage>
        <UserEditPage></UserEditPage>
        <Logout></Logout>
      </v-list>
    </v-menu>
  </div>
</template>

<script>
import AdminPage from "../userdropbar/AdminPage";
import UserEditPage from "../userdropbar/UserEditPage";
import Logout from "../userdropbar/Logout";
export default {
  components: {
    AdminPage,
    UserEditPage,
    Logout
  },
  computed: {
    setNickname() {
      const nickname = this.$store.state.userContext.nickname;
      if (nickname === null) {
        return "닉네임 미설정";
      }
      return nickname;
    },
    isAdmin() {
      if (this.$store.state.userContext.role === "ROLE_ADMIN") {
        return true;
      }
      return false;
    }
  }
};
</script>

<style></style>
