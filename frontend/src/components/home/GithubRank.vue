<template>
    <v-row>
        <v-col sm="7">
            <MyCommitRank></MyCommitRank>
            <AllCommitRank></AllCommitRank>
        </v-col>
    </v-row>
</template>

<script>
    import AllCommitRank from "./githubRank/AllCommitRank";
    import MyCommitRank from "./githubRank/MyCommitRank";

    export default {
        name: "GithubRank",
        components: {MyCommitRank, AllCommitRank}
    }
</script>

<style scoped>

</style>