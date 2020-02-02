<template>
  <div style="height: 100%">
    <v-card height="100%">
      <v-simple-table class="mx-auto user-table" fixed-header height="600px">
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-center">등록 번호</th>
              <th class="text-left">주소</th>
              <th class="text-left">설명</th>
              <th style="max-width: 100px" />
              <th class="text-right" style="max-width: 100px">
                <NewRssButton />
                <RssUpdateButton />
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, index) in feedData" :key="item.id" :id="item.id">
              <td class="text-center">{{ item.id }}</td>
              <td>{{ item.sourceUrl }}</td>
              <td>{{ item.description }}</td>
              <td>
                <RssEditButton
                  :feedSource="{
                    id: item.id,
                    sourceUrl: item.sourceUrl,
                    description: item.description
                  }"
                />
              </td>
              <td>
                <RssDeleteButton
                  :feedSource="{
                    id: item.id,
                    index: index
                  }"
                />
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </v-card>
  </div>
</template>

<script>
import axios from "axios";
import NewRssButton from "./rss/NewRssButton";
import RssEditButton from "./rss/RssEditButton";
import RssDeleteButton from "./rss/RssDeleteButton";
import RssUpdateButton from "./rss/RssUpdateButton";

export default {
  components: {
    NewRssButton,
    RssEditButton,
    RssDeleteButton,
    RssUpdateButton
  },
  data() {
    return {
      feedData: []
    };
  },
  created() {
    axios
      .get(this.$store.state.requestUrl + "/api/feeds", {
        withCredentials: true
      })
      .then(res => {
        const object = res.data;
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
