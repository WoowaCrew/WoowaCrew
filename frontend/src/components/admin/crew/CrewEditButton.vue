<template>
  <v-dialog v-model="dialog" width="500">
    <template v-slot:activator="{ on }">
      <v-btn width="100" color="primary" block v-on="on">수정</v-btn>
    </template>

    <v-card>
      <v-card-title
        class="headline primary"
        style="color: whitesmoke"
        primary-title
      >
        크루 정보
      </v-card-title>

      <v-card-text>
        <v-form style="margin-top: 10px">
          <div>기수</div>
          <v-overflow-btn
            v-model="degreeNumber"
            :items="degreeList"
            label="기수를 선택해 주세요"
          ></v-overflow-btn>
          <div>권한</div>
          <v-overflow-btn
            v-model="userRole"
            :items="userRoleList"
            label="권한을 선택해 주세요"
          ></v-overflow-btn>
        </v-form>
      </v-card-text>

      <v-divider></v-divider>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="red" text @click="dialog = false">
          취소
        </v-btn>
        <v-btn color="primary" text @click="editUser">
          수정
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from "axios";

export default {
  props: ["user"],
  data() {
    return {
      degreeNumber: String(this.user.degreeNumber),
      userRole: this.user.userRole,
      degreeList: ["0", "1", "2"],
      userRoleList: ["ROLE_PRECOURSE", "ROLE_CREW", "ROLE_COACH", "ROLE_ADMIN"],
      dialog: false
    };
  },
  methods: {
    editUser() {
      const id = this.user.id;
      const degree = this.degreeNumber;
      const userRole = this.userRole;
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
        this.dialog = false;
        location.reload(true);
      });
    }
  }
};
</script>

<style></style>
