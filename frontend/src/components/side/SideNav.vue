<template>
  <div>
    <v-list-item style="height: 63px; padding-left: 24px">
      <v-list-item-content>
        <v-list-item-title class="font-weight-black">
          WOOWA CREW
        </v-list-item-title>
      </v-list-item-content>
    </v-list-item>

    <v-divider />
    <AdminMenu v-if="isAdmin" />
    <ArticleMenu />
    <ServiceMenu />
  </div>
</template>

<script>
import AdminMenu from "../admin/Admin";
import ArticleMenu from "./menu/ArticleMenu";
import ServiceMenu from "./menu/ServiceMenu";

export default {
  name: "SideNav",
  components: {
    AdminMenu,
    ArticleMenu,
    ServiceMenu
  },
  computed: {
    isAdmin() {
      if (this.$store.state.userContext === null) {
        return false;
      }
      return this.$store.state.userContext.role === "ROLE_ADMIN";
    }
  }
};
</script>

<style>
.menu-header {
  padding: 40px 0 12px 24px;
}

.menu-header .v-list-item__content {
  padding: 0;
}

.menu-header .v-list-item__title {
  color: #212121;
  font-size: 18px;
}

.menu-buttons {
  padding: 0 0 40px;
}

.menu-buttons .v-list-item {
  padding: 0 0 0 24px;
}

.menu-buttons .v-list-item__title {
  color: #616161;
  font-size: 12px !important;
}
</style>
