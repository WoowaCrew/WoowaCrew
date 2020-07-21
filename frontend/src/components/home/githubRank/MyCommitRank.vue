<template>
    <v-card
            dark
            color="rgba(66,66,66)"
            height="100px"
            class="d-flex align-center mb-6 rank-hover"
            style="font-weight: bold"
            :href="'https://github.com/' + githubId"
            target="_blank"
    >
        <v-list-item>
            <v-list-item-avatar
                    size="62"
                    color="rgb(250,250,250)"
                    style="color: #424242; box-shadow: 1px 1px 4px gray inset"
            >
                {{ rank }}위
            </v-list-item-avatar>
            <v-list-item-content style="font-size: 1.3rem">
                <div style="font-weight: normal; font-size: 0.8rem">
                    {{ githubId }}
                </div>
                <div style="margin-top: 3px">
                    <span>{{ degree }}기 </span>
                    <span>{{ nickname }}</span>
                </div>
            </v-list-item-content>
            <span>
                {{ numberWithCommas(point) }} Point
            </span>
        </v-list-item>
    </v-card>
</template>

<script>
    export default {
        name: "MyCommitRank",
        data() {
            return {
                rank: 1,
                degree: 1,
                nickname: "효오",
                githubId: "hyojaekim",
                point: 31232,
            }
        },
        methods: {
            numberWithCommas(point) {
                return point.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
            }
        }
    }
</script>

<style scoped>
    .rank-hover {
        transition: 0.3s;
    }

    .rank-hover:hover {
        box-shadow: 1px 3px 7px 4px rgba(0, 0, 0, 0.2);
        background-color: rgba(66, 66, 66, 0.9) !important;
    }
</style>