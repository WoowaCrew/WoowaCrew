<template>
  <v-col sm="4">
    <v-card>
      <v-list two-line>
        <v-subheader style="color: #214b7d">
          "Happy Birthday to You! 🥳"
        </v-subheader>
        <template v-for="user in users">
          <v-list-item :key="user.id">
            <v-list-item-content>
              <v-list-item-title>{{ user.nickname }}</v-list-item-title>
              <v-list-item-subtitle>
                {{ user.degree }}기 크루 &mdash; 생일까지 {{ user.birthday }}일
                남았습니다.
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
export default {
  name: "BirthdayList",
  data: () => ({
    users: [
      { id: 1, nickname: "테스트 유저 1", degree: 1, birthday: 5 },
      { id: 2, nickname: "테스트 유저 2", degree: 1, birthday: 5 },
      { id: 3, nickname: "테스트 유저 3", degree: 1, birthday: 5 }
    ]
  })
};
</script>
