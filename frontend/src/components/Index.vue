<template>
  <v-container style="margin-left: 40px; margin-top: 30px">
    <v-row>
      <v-col sm="7">
        <SearchBox />
        <v-window
          v-model="window"
          class="elevation-1 main-card-content"
          vertical
        >
          <v-window-item
            v-for="indexContent in indexContents"
            :key="indexContent.id"
          >
            <v-content :is="indexContent.content" />
          </v-window-item>
        </v-window>

        <v-bottom-navigation
          dark
          shift
          background-color="#214b7d"
          v-model="window"
        >
          <v-item
            v-for="indexContent in indexContents"
            :key="indexContent.id"
            v-slot:default="{ active, toggle }"
          >
            <v-content
              :is="indexContent.button"
              :input-value="active"
              @click="toggle"
            />
          </v-item>
        </v-bottom-navigation>
      </v-col>

      <BirthdayList style="padding-top: 70px" />
    </v-row>
    <GithubRank></GithubRank>
  </v-container>
</template>

<script>
import BirthdayList from "./home/BirthdayList";
import Notice from "./home/Notice";
import KeywordRank from "./home/KeywordRank";
import KeywordRankButton from "./home/KeywordRankButton";
import NoticeButton from "./home/NoticeButton";
import SearchBox from "./home/SearchBox";
import GithubRank from "./home/GithubRank";

export default {
  name: "Index",
  components: {
    GithubRank,
    BirthdayList,
    SearchBox
  },
  data: () => ({
    indexContents: [
      { id: 1, button: NoticeButton, content: Notice },
      { id: 2, button: KeywordRankButton, content: KeywordRank }
    ],
    window: 0
  })
};
</script>

<style scoped>
.main-card-content {
  height: 500px;
  max-height: 500px;
  overflow: scroll;
  background-color: white;
}
</style>
