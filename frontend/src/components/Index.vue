<template>
  <v-row style="margin-left: 40px; margin-top: 60px">
    <v-col sm="7">
      <v-window
        v-model="window"
        class="elevation-1"
        vertical
        style="height: 500px; max-height: 500px; overflow: scroll; background-color: white"
      >
        <v-window-item v-for="button in buttons" :key="button.name">
          <div v-bind:is="button.component"></div>
        </v-window-item>
      </v-window>

      <v-bottom-navigation
        dark
        shift
        background-color="#214b7d"
        v-model="window"
      >
        <v-item
          v-for="button in buttons"
          :key="button"
          v-slot:default="{ active, toggle }"
        >
          <v-btn :input-value="active" @click="toggle">
            <span>{{ button.name }}</span>
            <v-icon>{{ button.icon }}</v-icon>
          </v-btn>
        </v-item>
      </v-bottom-navigation>
    </v-col>

    <BirthdayList />
  </v-row>
</template>

<script>
import BirthdayList from "./home/BirthdayList";
import Notice from "./home/Notice";
import KeywordRank from "./home/KeywordRank";
export default {
  name: "Index",
  components: {
    BirthdayList
  },
  data: () => ({
    buttons: [
      { name: "공지사항", icon: "mdi-home-variant", component: Notice },
      { name: "검색어 순위", icon: "mdi-chart-bar", component: KeywordRank }
    ],
    window: 0
  })
};
</script>
