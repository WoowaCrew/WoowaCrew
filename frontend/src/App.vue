<template>
  <v-app>
    <v-app-bar app color="#78909C" dark>
      <div class="d-flex align-center">
        <span class="font-weight-black white--text">WoowaCrew</span>
      </div>

      <v-spacer></v-spacer>

      <LoginHeader></LoginHeader>
    </v-app-bar>

    <v-content>
      <router-view />
    </v-content>
  </v-app>
</template>

<script>
import LoginHeader from "./components/header/LoginHeader";

export default {
  name: "App",

  components: {
    LoginHeader
  },

  data: () => ({
    //
  })
};
</script>
