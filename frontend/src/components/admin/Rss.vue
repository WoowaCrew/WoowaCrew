<template>
  <v-card height="100%">
    <v-bottom-navigation
      height="50"
      scroll-target="#scroll-area-1"
      absolute
      horizontal
    >
      <NewRssButton />
    </v-bottom-navigation>
    <v-simple-table class="mx-auto user-table" fixed-header height="600px">
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-left">등록 번호</th>
            <th class="text-left">주소</th>
            <th class="text-left">설명</th>
            <th class="text-left">수정 버튼</th>
            <th class="text-left">삭제 버튼</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in userData" :key="item.id" :id="item.id">
            <td>{{ item.id }}</td>
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
    <v-dialog v-model="dialog" max-width="290">
      <v-card>
        <v-card-title class="headline"
          >Use Google's location service?</v-card-title
        >

        <v-card-text>
          Let Google help apps determine location. This means sending anonymous
          location data to Google, even when no apps are running.
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn color="green darken-1" text @click="dialog = false">
            Disagree
          </v-btn>

          <v-btn color="green darken-1" text @click="dialog = false">
            Agree
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-card>
</template>

<script>
import axios from "axios";
import NewRssButton from "./rss/NewRssButton";

export default {
  components: {
    NewRssButton
  },
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

      axios("http://localhost:8080/api/users/2/approve", {
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
      .get("http://localhost:8080/api/users/disapprove", {
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
.v-data-table {
  width: 100%;
}
</style>
