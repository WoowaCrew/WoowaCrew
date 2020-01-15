<template>
  <v-app>
    <v-app-bar color="#212121" dark app style="left: 0; z-index: 5">
      <a style="padding-left: 8px; text-decoration: none" href="/">
        <span class="font-weight-black white--text">WOOWA CREW</span>
      </a>

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
