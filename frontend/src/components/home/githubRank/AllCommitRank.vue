<template>
  <v-card height="1000px">
    <v-card-title style="color: #1d1d1f">
      Top 50
    </v-card-title>
    <v-list-item-group>
      <v-list-item
        v-for="(user, rank) in users"
        :key="user.githubId"
        class="rank"
        :href="`https://github.com/${user.githubId}`"
        target="_blank"
      >
        <v-list-item-avatar
          size="62"
          class="badge"
          :class="setBadgeColor(rank + 1)"
        >
          <span>{{ rank + 1 }}위</span>
        </v-list-item-avatar>
        <v-list-item-content style="font-size: 1.1rem">
          <div class="github-id">
            {{ user.githubId }}
          </div>
          <div style="margin-top: 3px;">
            <span>{{ user.degree }}기 </span>
            <span>{{ user.nickname }}</span>
          </div>
        </v-list-item-content>
        <span style="font-size: 0.9rem">
          {{ numberWithCommas(user.point) }} Point
        </span>
      </v-list-item>
    </v-list-item-group>
  </v-card>
</template>

<script>
import axios from "axios";

export default {
  name: "AllCommitRank",
  data() {
    return {
      users: [],
      badgeColor: {
        1: "gold",
        2: "silver",
        3: "bronze"
      }
    };
  },
  methods: {
    setBadgeColor(rank) {
      let badgeColor = this.badgeColor[rank];
      return badgeColor ? badgeColor : "default";
    },
    numberWithCommas(point) {
      return point.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
    },
    async setTotalCommitRank() {
      const totalCommitRank = await this.fetchTotalCommitRank();
      if (totalCommitRank) {
        this.users = totalCommitRank;
      }
    },
    fetchTotalCommitRank() {
      return axios
        .get(`${this.$store.state.requestUrl}/api/github/commit/rank`)
        .then(res => {
          if (res.status === 200) {
            return res.data;
          }
        });
    }
  },
  created() {
    this.setTotalCommitRank();
  }
};
</script>

<style scoped>
.rank {
  height: 100px;
  font-weight: bold;
  transition: 0.3s;
}
.rank:hover {
  box-shadow: 2px 3px 8px 3px rgba(0, 0, 0, 0.2);
}
.badge {
  box-shadow: 1px 1px 4px gray inset;
}
.github-id {
  font-weight: normal;
  font-size: 0.8rem;
}
.default {
  background: rgb(250, 250, 250);
  color: #424242;
}
</style>
