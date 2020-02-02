<template>
  <v-card height="100%">
    <v-simple-table class="mx-auto user-table" fixed-header height="600px">
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-center">아이디</th>
            <th class="text-left">닉네임</th>
            <th class="text-left">기수</th>
            <th class="text-left">권한</th>
            <th class="text-left">승인 버튼</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in userData" :key="item.id" :id="item.id">
            <td class="text-center">{{ item.id }}</td>
            <td>{{ item.nickname }}</td>
            <td>
              <v-overflow-btn
                v-model="form.degree[item.id]"
                :items="degreeList"
                label="기수를 선택해 주세요"
              ></v-overflow-btn>
            </td>
            <td>
              <v-overflow-btn
                v-model="form.userRole[item.id]"
                :items="userRoleList"
                label="권한을 선택해 주세요"
              ></v-overflow-btn>
            </td>
            <td>
              <v-btn @click="approve(item.id, index)">
                승인
              </v-btn>
            </td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
  </v-card>
</template>

<script>
import axios from "axios";

export default {
  methods: {
    approve(id, index) {
      const degree = this.form.degree[id];
      const userRole = this.form.userRole[id];
      if (degree == null) {
        alert("기수를 입력해주세요.");
        return;
      }
      if (userRole == null) {
        alert("권한을 입력해주세요.");
        return;
      }

      const data = {
        role: userRole,
        degreeNumber: degree
      };

      axios(this.$store.state.requestUrl + "/api/users/" + id + "/approve", {
        method: "put",
        data: JSON.stringify(data),
        headers: { "Content-Type": "application/json; charset=utf-8" },
        withCredentials: true
      }).then(res => {
        res.data;
        alert("정상적으로 저장되었습니다");
        this.$delete(this.userData, index);
      });
    }
  },
  data() {
    return {
      form: {
        degree: [],
        userRole: []
      },
      degreeList: ["0", "1", "2"],
      userRoleList: ["ROLE_PRECOURSE", "ROLE_CREW", "ROLE_COACH", "ROLE_ADMIN"],
      userData: []
    };
  },
  created() {
    axios
      .get(this.$store.state.requestUrl + "/api/users/disapprove", {
        withCredentials: true
      })
      .then(res => {
        const object = res.data;
        this.userData = object;
      });
  }
};
</script>

<style>
.user-table {
  width: 100%;
}
</style>
