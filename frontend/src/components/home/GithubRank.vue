<template>
  <v-row>
    <v-col sm="7">
      <MyCommitRank v-if="isCrew"></MyCommitRank>
      <AllCommitRank></AllCommitRank>
    </v-col>
  </v-row>
</template>

<script>
import AllCommitRank from "./githubRank/AllCommitRank";
import MyCommitRank from "./githubRank/MyCommitRank";

export default {
  name: "GithubRank",
  components: { MyCommitRank, AllCommitRank },
  computed: {
    isCrew() {
      if (this.$store.state.userContext === null) {
        return false;
      }
      return this.$store.state.userContext.role !== "ROLE_PRECOURSE";
    }
  }
};
</script>

<style scoped></style>
