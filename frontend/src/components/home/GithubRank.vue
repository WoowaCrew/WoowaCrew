<template>
    <v-row>
        <v-col sm="7">
            <GithubRankDetail></GithubRankDetail>
            <AllRank></AllRank>
        </v-col>
    </v-row>
</template>

<script>
    import AllRank from "./githubRank/AllRank";
    import GithubRankDetail from "./githubRank/GithubRankDetail";

    export default {
        name: "GithubRank",
        components: {GithubRankDetail, AllRank}
    }
</script>

<style scoped>

</style>