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
    data() {
      return {
        drawer: true
      }
    },
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
  .gold {
    background-color: #fec84e;
    background-image: linear-gradient(315deg, #fec84e 0%, #ffdea8 74%);
  }

  .silver {
    background-color: #f1f2f6;
    background-image: linear-gradient(315deg, #f1f2f6 0%, #c9c6c6 74%);
  }

  .bronze {
    background-color: #f5d3c8;
    background-image: linear-gradient(315deg, #f5d3c8 0%, #c08552 74%);
  }

  @media all and (min-width: 1025px) {
    .side-nav-toggle-btn {
      display: none;
    }
  }
</style>
