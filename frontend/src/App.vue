<template>
  <v-app>
    <v-app-bar color="#78909C" dark app style="left: 0; z-index: 5">
      <div class="d-flex align-center">
        <span class="font-weight-black white--text">WoowaCrew</span>
      </div>

      <v-spacer />

      <LoginHeader />
    </v-app-bar>

    <SideNav />

    <v-content>
      <router-view />
    </v-content>
  </v-app>
</template>

<script>
import LoginHeader from "./components/header/LoginHeader";
import SideNav from "./components/side/SideNav";

export default {
  name: "App",

  components: {
    LoginHeader,
    SideNav
  },

  data: () => ({
    //
  })
};
</script>
