<template>
    <v-card height="1000px">
        <v-card-title style="color: #1d1d1f">
            Top 50
        </v-card-title>
        <v-list-item-group>
            <v-list-item
                    v-for="(user, rank) in users"
                    :key="user.githubId"
                    class="rank-hover"
                    style="height: 100px; font-weight: bold"
                    :href="'https://github.com/' + user.githubId"
                    target="_blank"
            >
                <v-list-item-avatar
                        size="62"
                        :class="setBadgeColor(rank + 1)"
                        style="box-shadow: 1px 1px 4px gray inset"
                >
                    {{ rank + 1 }}위
                </v-list-item-avatar>
                <v-list-item-content style="font-size: 1.1rem">
                    <div style="font-weight: normal; font-size: 0.8rem">
                        {{ user.githubId }}
                    </div>
                    <div style="margin-top: 3px;">
                        <span>{{ user.degree }}기 </span>
                        <span>{{ user.nickname}}</span>
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
    export default {
        name: "AllCommitRank",
        data() {
            return {
                users: [
                    {
                        degree: 1,
                        githubId: "hyojaekim",
                        nickname: "효오",
                        point: 3000,
                    },
                    {
                        degree: 1,
                        githubId: "hyojaekim",
                        nickname: "효오",
                        point: 3000,
                    },
                    {
                        degree: 1,
                        githubId: "hyojaekim",
                        nickname: "효오",
                        point: 3000,
                    },
                    {
                        degree: 1,
                        githubId: "hyojaekim",
                        nickname: "효오",
                        point: 3000,
                    },
                ],
                badgeColor: {
                    1: "gold",
                    2: "silver",
                    3: "bronze",
                },
            }
        },
        methods: {
            setBadgeColor(rank) {
                let badgeColor = this.badgeColor[rank];
                return badgeColor ? badgeColor : "default";
            },
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
        box-shadow: 2px 3px 8px 3px rgba(0, 0, 0, 0.2);
    }

    .default {
        background: rgb(250, 250, 250);
        color: #424242;
    }
</style>