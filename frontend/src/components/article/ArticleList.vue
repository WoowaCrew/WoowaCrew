<template>
  <v-card height="100%">
    <v-bottom-navigation absolute style="box-shadow: none;">
      <v-pagination
        v-model="page"
        class="my-4"
        :length="totalPage"
      ></v-pagination>
    </v-bottom-navigation>

    <v-simple-table class="mx-auto user-table" fixed-header height="800px">
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-center article-mini-cell">번호</th>
            <th class="text-center article-title">제목</th>
            <th class="text-left article-mini-cell">작성자</th>
            <th class="text-center article-mini-cell">작성일</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in articles" :key="item.id" :id="item.id">
            <td class="text-center article-mini-cell text-truncate">
              {{ item.id }}
            </td>
            <td
              width="500"
              class="hover-cursor text-truncate article-title"
              @click="
                $router
                  .push({
                    name: viewPath,
                    params: {
                      articleId: item.id
                    }
                  })
                  .catch(err => {})
              "
            >
              {{ item.title }}
            </td>
            <td class="article-mini-cell text-truncate">
              {{ item.userResponseDto.nickname }}
            </td>
            <td class="article-mini-cell text-center">
              {{ convert(item.createdDate) }}
            </td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    <v-btn
      fab
      dark
      large
      color="primary"
      fixed
      right
      bottom
      @click="
        $router
          .push({
            name: editPath
          })
          .catch(err => {})
      "
    >
      <v-icon>mdi-plus</v-icon>
    </v-btn>
  </v-card>
</template>

<script>
import dateConverter from "../../store/dateConverter";
import axios from "axios";

export default {
  components: {},
  data() {
    return {
      page: "",
      totalPage: 0,
      apiPath: "",
      viewPath: "",
      editPath: "",
      articles: []
    };
  },
  created() {
    const path = this.$route.path;
    if (path.startsWith("/articles/free")) {
      this.apiPath = "/api/articles";
      this.viewPath = "freeArticleView";
      this.editPath = "freeArticleEdit";
    }
    if (path.startsWith("/articles/crew")) {
      this.apiPath = "/api/articles/crew";
      this.viewPath = "crewArticleView";
      this.editPath = "crewArticleEdit";
    }
    let page = this.$route.query.page;
    if (page == null) {
      page = 1;
    }
    this.page = Number(page);
    axios
      .get(this.$store.state.requestUrl + this.apiPath + "?page=" + this.page, {
        withCredentials: true
      })
      .then(res => {
        const object = res.data;
        this.totalPage = object.totalPages;
        this.articles = object.articles;
      });
  },
  methods: {
    convert(date) {
      return dateConverter(date).split(" ")[0];
    }
  },
  watch: {
    page() {
      this.$router
        .push({
          path: this.$route.path,
          query: {
            page: this.page
          }
        })
        .catch(err => {
          err;
        });
      axios
        .get(this.$store.state.requestUrl + this.apiPath + "?page=" + this.page, {
          withCredentials: true
        })
        .then(res => {
          const object = res.data;
          this.totalPage = object.totalPages;
          this.articles = object.articles;
        });
    }
  }
};
</script>

<style>
.v-data-table {
  width: 100%;
}
.hover-cursor:hover {
  cursor: pointer;
}
.article-mini-cell {
  width: 100px;
  max-width: 110px;
}
.article-title {
  width: 600px;
  max-width: 750px;
}
</style>
