<template>
  <v-card height="100%">
    <v-bottom-navigation
      height="50"
      scroll-target="#scroll-area-1"
      absolute
      horizontal
    >
      <NewRssButton />
    </v-bottom-navigation>
    <v-simple-table class="mx-auto user-table" fixed-header height="600px">
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-left">등록 번호</th>
            <th class="text-left">주소</th>
            <th class="text-left">설명</th>
            <th class="text-left">수정 버튼</th>
            <th class="text-left">삭제 버튼</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in feedData" :key="item.id" :id="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.sourceUrl }}</td>
            <td>{{ item.description }}</td>
            <td>
              <NewRssButton />
            </td>
            <td>
              <RssDeleteButton
                :feedSource="{
                  id: item.id,
                  index: index
                }"
                :deleteFeed="deleteFeedRow"
              />
            </td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
  </v-card>
</template>

<script>
import axios from "axios";
import NewRssButton from "./rss/NewRssButton";
import RssDeleteButton from "./rss/RssDeleteButton";

export default {
  components: {
    NewRssButton,
    RssDeleteButton
  },
  methods: {
    deleteFeedRow(index) {
      console.log(index);
      this.$delete(this.feedData, index);
    }
  },
  data() {
    return {
      feedData: []
    };
  },
  created() {
    axios
      .get("http://localhost:8080/api/feeds", {
        withCredentials: true
      })
      .then(res => {
        const object = res.data;
        console.log(object);
        this.feedData = object;
      });
  }
};
</script>

<style>
.v-data-table {
  width: 100%;
}
</style>
