<template>
  <v-card
    dark
    color="rgba(66,66,66)"
    height="100px"
    class="d-flex align-center mb-6 rank"
    v-if="user"
    :href="`https://github.com/${user.githubId}`"
    target="_blank"
  >
    <v-list-item>
      <v-list-item-avatar
        size="62"
        class="badge"
        :class="setBadgeColor(user.rank)"
      >
        {{ user.rank }}위
      </v-list-item-avatar>
      <v-list-item-content style="font-size: 1.3rem">
        <div class="github-id">
          {{ user.githubId }}
        </div>
        <div style="margin-top: 3px">
          <span>{{ user.degree }}기 </span>
          <span>{{ user.nickname }}</span>
        </div>
      </v-list-item-content>
      <span>{{ numberWithCommas(user.point) }} Point</span>
    </v-list-item>
  </v-card>
</template>

<script>
import axios from "axios";

export default {
  name: "MyCommitRank",
  data() {
    return {
      user: null,
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
    async setMyCommitRank() {
      const user = await this.fetchMyCommitRank();
      if (user) {
        this.user = user;
      }
    },
    fetchMyCommitRank() {
      return axios
        .get(`${this.$store.state.requestUrl}/api/github/commit/rank/me`, {
          withCredentials: true
        })
        .then(res => {
          if (res.status === 200) {
            return res.data;
          }
        })
        .catch(() => {});
    }
  },
  created() {
    this.setMyCommitRank();
  }
};
</script>

<style scoped>
.rank {
  font-weight: bold;
  transition: 0.3s;
}
.rank:hover {
  box-shadow: 1px 3px 7px 4px rgba(0, 0, 0, 0.2);
  background-color: rgba(66, 66, 66, 0.9) !important;
}
.badge {
  color: #424242;
  box-shadow: 1px 1px 4px gray inset;
}
.github-id {
  font-weight: normal;
  font-size: 0.8rem;
}
.default {
  background: rgb(250, 250, 250);
}
</style>
