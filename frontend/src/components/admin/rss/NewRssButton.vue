<template>
  <v-dialog v-model="dialog" width="500">
    <template v-slot:activator="{ on }">
      <v-btn v-on="on" text small color="primary">
        <v-icon>mdi-plus</v-icon>
        등록
      </v-btn>
    </template>

    <v-card>
      <v-card-title
        class="headline primary"
        primary-title
        style="color: whitesmoke"
      >
        신규 등록
      </v-card-title>

      <v-card-text>
        <v-form style="margin-top: 10px">
          <v-text-field v-model="sourceUrl" label="주소" />
          <v-text-field v-model="description" label="설명" />
        </v-form>
      </v-card-text>

      <v-divider></v-divider>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="red" text @click="dialog = false">
          취소
        </v-btn>
        <v-btn color="primary" text @click="addFeed">
          I accept
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      sourceUrl: "",
      description: "",
      dialog: false
    };
  },
  methods: {
    addFeed() {
      const data = new FormData();
      data.append("sourceUrl", this.sourceUrl);
      data.append("description", this.description);

      axios(this.$store.state.requestUrl + "/api/feeds", {
        method: "post",
        data: data,
        withCredentials: true
      })
        .then(res => {
          if (res.status != 200) {
            alert("오류가 발생했습니다. 다시한번 확인해주세요");
            return;
          }
          alert("정상적으로 등록되었습니다");
          this.sourceUrl = "";
          this.description = "";
          this.dialog = false;
          location.reload(true);
        })
        .catch(error => {
          error.data;
          alert("에러가 발생했습니다. 다시한번 확인해주세요");
          return;
        });
    }
  }
};
</script>

<style></style>
