<template>
  <v-app>
    <v-app-bar color="#212121" dark app style="left: 0; z-index: 5">
      <v-app-bar-nav-icon class="side-nav-toggle-btn" @click="toggle()" />
      <a style="padding-left: 8px; text-decoration: none" href="/">
        <span class="font-weight-black white--text">WOOWA CREW</span>
      </a>

      <v-spacer />

      <LoginHeader />
    </v-app-bar>

    <v-navigation-drawer
      v-model="drawer"
      width="280px"
      style="z-index: 4"
      app
      disable-resize-watcher
    >
      <SideNav />
    </v-navigation-drawer>

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
  data: () => ({
    drawer: true
  }),
  components: {
    LoginHeader,
    SideNav
  },
  methods: {
    toggle() {
      this.drawer = !this.drawer;
    }
  }
};
</script>
<style>
@media all and (min-width: 1025px) {
  .side-nav-toggle-btn {
    display: none;
  }
}
</style>
