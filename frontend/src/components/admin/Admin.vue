<template>
  <v-list>
    <v-list-item class="menu-header">
      <v-list-item-content>
        <v-list-item-title>관리자 기능</v-list-item-title>
      </v-list-item-content>
    </v-list-item>

    <v-list class="menu-buttons" dense nav>
      <v-list-item v-for="(item, i) in items" :key="i" @click="move(item.link)">
        <v-list-item-content>
          <v-list-item-title style="color: #212121">
            {{ item.title }}
          </v-list-item-title>
          <v-list-item-title>
            {{ item.description }}
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>

    <v-divider />
  </v-list>
</template>

<script>
export default {
  data() {
    return {
      item: 1,
      items: [
        {
          title: "승인 대기 회원 관리",
          description: "회원가입 후 승인을 기다리고 있습니다.",
          link: "/admin/user-approve"
        },
        {
          title: "rss 등록/관리",
          description: "rss피드를 등록하고 관리해보세요",
          link: "/admin/rss"
        },
        {
          title: "크루 관리",
          description: "크루들을 관리해보세요",
          link: "/admin/crew"
        }
      ]
    };
  },
  methods: {
    move(link) {
      window.location.href = link;
    }
  }
};
</script>

<style></style>
