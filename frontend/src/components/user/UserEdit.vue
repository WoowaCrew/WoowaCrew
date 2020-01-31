<template>
  <v-card width="500" class="mx-auto">
    <v-card-title>
      <h1 class="display-1">회원정보 수정</h1>
    </v-card-title>
    <v-card-text>
      <v-form>
        <v-text-field
          v-model="nickname"
          :rules="[rules.nicknameRequired]"
          label="닉네임"
        />
        <v-text-field
          v-model="birth"
          v-mask="mask"
          label="생년월일(예시 - 19931025)"
        >
        </v-text-field>
      </v-form>
    </v-card-text>
    <v-card-actions>
      <v-btn color="info" block @click="editUserInfo">
        저장
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import axios from "axios";
import { mask } from "vue-the-mask";

export default {
  directives: {
    mask
  },
  data: () => ({
    nickname: "",
    birth: "",
    mask: "####-##-##",
    rules: {
      nicknameRequired: value => {
        return !!value || "닉네임을 입력해주세요";
      }
    }
  }),

  methods: {
    editUserInfo() {
      if (this.nickname.trim().length === 0) {
        alert("닉네임을 입력해주세요");
        return;
      }
      const birthPattern = new RegExp("[0-9]{4}-[0-9]{2}-[0-9]{2}");

      if (!birthPattern.test(this.birth)) {
        alert("올바른 생년월일을 입력해 주세요");
        return;
      }
      const formData = new FormData();
      formData.append("nickname", this.nickname);
      formData.append("birthday", this.birth);
      axios(this.$store.state.requestUrl + "/users/update", {
        method: "post",
        data: formData,
        withCredentials: true
      }).then(() => {
        window.location.href = "/";
      });
    }
  }
};
</script>

<style></style>
