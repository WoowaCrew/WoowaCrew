<template>
  <v-col sm="4">
    <v-card>
      <v-list two-line>
        <v-subheader style="color: #214b7d">
          이번 달 생일자 🥳
        </v-subheader>
        <template v-for="user in users">
          <v-list-item :key="user.id">
            <v-list-item-content>
              <v-list-item-title>{{ user.nickname }}</v-list-item-title>
              <v-list-item-subtitle>
                {{ user.degree }}기 크루 &mdash;
                {{ convertBirthday(user.birthday) }}
              </v-list-item-subtitle>
              <v-divider style="margin-top: 10px" :inset="true" />
            </v-list-item-content>
          </v-list-item>
        </template>
      </v-list>
    </v-card>
  </v-col>
</template>

<script>
import axios from "axios";

export default {
  name: "BirthdayList",
  data: () => ({
    users: []
  }),
  created() {
    axios
      .get(this.$store.state.requestUrl + "/api/user/birthday", {
        withCredentials: true
      })
      .then(response => (this.users = response.data));
  },
  methods: {
    convertBirthday(birthday) {
      const data = birthday.split("-");
      if (new Date().getDate() == data[2]) {
        return "🎉오늘의 생일 축하드립니다!! 🎉";
      }
      return `${data[1]}월 ${data[2]}일 생일 입니다.`;
    }
  }
};
</script>
