<template>
  <v-list>
    <v-list-item class="menu-header">
      <v-list-item-content>
        <v-list-item-title>Communication</v-list-item-title>
      </v-list-item-content>
    </v-list-item>

    <v-list class="menu-buttons" dense nav>
      <v-list-item v-for="article in articles" :key="article.title" link>
        <v-list-item-content>
          <v-list-item-title>{{ article.title }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>

    <v-divider />
  </v-list>
</template>

<script>
export default {
  name: "ArticleMenu",
  data() {
    return {
      articles: [
        { title: "자유롭게 소통해요." },
        { title: "같은 기수와 소통해요." },
        { title: "비밀은 보장할게요. 단, 나쁜 말은 안 돼요!" },
        { title: "어떤 블로그 게시을 볼까요?" }
      ]
    };
  }
};
</script>
