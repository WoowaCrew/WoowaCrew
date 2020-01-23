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
            <th class="text-left">게시글 번호</th>
            <th class="text-left">제목</th>
            <th class="text-left">작성자</th>
            <th class="text-left">작성일</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in articles" :key="item.id" :id="item.id">
            <td width="100">{{ item.id }}</td>
            <td
              width="500"
              class="hover-cursor"
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
            <td width="200">{{ item.userResponseDto.nickname }}</td>
            <td width="100">{{ item.createdDate }}</td>
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
    console.log(this.apiPath);
    console.log(this.viewPath);
    console.log(this.editPath);
    let page = this.$route.query.page;
    if (page == null) {
      page = 1;
    }
    this.page = Number(page);
    axios
      .get("http://localhost:8080" + this.apiPath + "?page=" + this.page, {
        withCredentials: true
      })
      .then(res => {
        const object = res.data;
        console.log(object);
        this.totalPage = object.totalPages;
        this.articles = object.articles;
      });
  },
  watch: {
    page() {
      console.log(this.viewPath);
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
        .get("http://localhost:8080" + this.apiPath + "?page=" + this.page, {
          withCredentials: true
        })
        .then(res => {
          const object = res.data;
          console.log(object);
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
</style>
