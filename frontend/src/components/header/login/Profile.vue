<template>
  <div class="text-center">
    <v-menu offset-y>
      <template v-slot:activator="{ on }">
        <v-btn text class="white--text" color="#424242" v-on="on">
          {{ setNickname }}
          <v-icon small>mdi-menu-down</v-icon>
        </v-btn>
      </template>
      <v-list dense small color="#424242">
        <UserEditPage></UserEditPage>
        <Logout></Logout>
      </v-list>
    </v-menu>
  </div>
</template>

<script>
import UserEditPage from "../userdropbar/UserEditPage";
import Logout from "../userdropbar/Logout";
export default {
  components: {
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
    }
  }
};
</script>

<style></style>
