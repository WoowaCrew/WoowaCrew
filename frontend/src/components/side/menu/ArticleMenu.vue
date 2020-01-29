<template>
  <v-list>
    <v-list-item class="menu-header">
      <v-list-item-content>
        <v-list-item-title>커뮤니케이션</v-list-item-title>
      </v-list-item-content>
    </v-list-item>

    <v-list class="menu-buttons" dense nav>
      <v-list-item v-for="article in articles" :key="article.title" link>
        <v-list-item-content @click="movePath(article.link)">
          <v-list-item-title style="color: #212121">
            {{ article.title }}
          </v-list-item-title>
          <v-list-item-title>
            {{ article.description }}
          </v-list-item-title>
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
        {
          title: "자유 게시판",
          description: "자유롭게 소통해요.",
          link: "/articles/free"
        },
        {
          title: "크루 게시판",
          description: "같은 기수와 소통해요.",
          link: "/articles/crew"
        },
        {
          title: "익명 게시판",
          description: "비밀은 보장할게요. 단, 나쁜 말은 안 돼요!"
        },
        {
          title: "피드 게시판",
          description: "어떤 블로그 게시물을 볼까요?",
          link: "/articles/feed"
        }
      ]
    };
  },
  methods: {
    movePath(link) {
      window.location.href = link + "?page=1";
    }
  }
};
</script>
