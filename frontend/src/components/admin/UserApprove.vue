<template>
  <v-simple-table class="mx-auto user-table" fixed-header height="600px">
    <template v-slot:default>
      <thead>
        <tr>
          <th class="text-left">아이디</th>
          <th class="text-left">닉네임</th>
          <th class="text-left">기수</th>
          <th class="text-left">권한</th>
          <th class="text-left">승인 버튼</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in userData" :key="item.id" :id="item.id">
          <td>{{ item.id }}</td>
          <td>{{ item.calories }}</td>
          <td>
            <v-overflow-btn
              v-model="form.degree[item.id]"
              :items="item.degree"
              label="기수를 선택해 주세요"
            ></v-overflow-btn>
          </td>
          <td>
            <v-overflow-btn
              v-model="form.userRole[item.id]"
              :items="item.userRole"
              label="권한을 선택해 주세요"
            ></v-overflow-btn>
          </td>
          <td>
            <v-btn @click="approve(item.id)">
              승인
            </v-btn>
          </td>
        </tr>
      </tbody>
    </template>
  </v-simple-table>
</template>

<script>
import axios from "axios";

export default {
  methods: {
    approve(id) {
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
        console.log(res);
        alert("정상적으로 저장되었습니다");
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
      userRole: ["ROLE_PRECOURSE", "ROLE_CREW", "ROLE_COACH", "ROLE_ADMIN"],
      userData: [
        {
          id: "Frozen Yogurt",
          calories: 159,
          degree: ["0", "1", "2"],
          userRole: ["ROLE_PRECOURSE", "ROLE_CREW", "ROLE_COACH", "ROLE_ADMIN"]
        },
        {
          name: "Ice cream sandwich",
          calories: 237,
          degree: ["0", "1", "2"],
          userRole: ["ROLE_PRECOURSE", "ROLE_CREW", "ROLE_COACH", "ROLE_ADMIN"]
        },
        {
          name: "Eclair",
          calories: 262,
          degree: ["0", "1", "2"],
          userRole: ["ROLE_PRECOURSE", "ROLE_CREW", "ROLE_COACH", "ROLE_ADMIN"]
        },
        {
          name: "Cupcake",
          calories: 305,
          degree: ["0", "1", "2"],
          userRole: ["ROLE_PRECOURSE", "ROLE_CREW", "ROLE_COACH", "ROLE_ADMIN"]
        },
        {
          name: "Gingerbread",
          calories: 356,
          degree: ["0", "1", "2"],
          userRole: ["ROLE_PRECOURSE", "ROLE_CREW", "ROLE_COACH", "ROLE_ADMIN"]
        },
        {
          name: "Jelly bean",
          calories: 375,
          degree: ["0", "1", "2"],
          userRole: ["ROLE_PRECOURSE", "ROLE_CREW", "ROLE_COACH", "ROLE_ADMIN"]
        },
        {
          name: "Lollipop",
          calories: 392,
          degree: ["0", "1", "2"],
          userRole: ["ROLE_PRECOURSE", "ROLE_CREW", "ROLE_COACH", "ROLE_ADMIN"]
        },
        {
          name: "Honeycomb",
          calories: 408,
          degree: ["0", "1", "2"],
          userRole: ["ROLE_PRECOURSE", "ROLE_CREW", "ROLE_COACH", "ROLE_ADMIN"]
        },
        {
          name: "Donut",
          calories: 452,
          degree: ["0", "1", "2"],
          userRole: ["ROLE_PRECOURSE", "ROLE_CREW", "ROLE_COACH", "ROLE_ADMIN"]
        }
      ]
    };
  }
};
</script>

<style>
.user-table {
  width: 800px;
}
</style>
