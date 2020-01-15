<template>
  <v-card style="height: 100%">
    <v-layout row wrap fill-height>
      <v-navigation-drawer permanent>
        <v-list nav dense>
          <v-list-item
            v-for="(item, i) in items"
            :key="i"
            @click="move(item.link)"
          >
            <v-list-item-icon>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-item-icon>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>
      <router-view></router-view>
    </v-layout>
  </v-card>
</template>

<script>
export default {
  data() {
    return {
      item: 1,
      items: [
        { icon: "mdi-folder", title: "My Files", link: "a" },
        {
          icon: "mdi-account-multiple",
          title: "승인 대기 회원 관리",
          link: "/admin/user-approve"
        },
        { icon: "mdi-star", title: "Starred", link: "c" }
      ]
    };
  },
  methods: {
    move(link) {
      console.log(link);
      window.location.href = link;
    }
  }
};
</script>

<style></style>
