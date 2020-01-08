<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      <div class="d-flex align-center">
        <v-img
          alt="Vuetify Logo"
          class="shrink mr-2"
          contain
          src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png"
          transition="scale-transition"
          width="40"
        />

        <v-img
          alt="Vuetify Name"
          class="shrink mt-1 hidden-sm-and-down"
          contain
          min-width="100"
          src="https://cdn.vuetifyjs.com/images/logos/vuetify-name-dark.png"
          width="100"
        />
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
