<template>
  <v-dialog v-model="dialog" width="500">
    <template v-slot:activator="{ on }">
      <v-btn
        color="gray"
        style="color: #333333"
        block
        v-on="on"
        class="mini-button"
      >
        삭제
      </v-btn>
    </template>

    <v-card>
      <v-card-title class="headline">
        정말로 삭제하시겠습니까?
      </v-card-title>

      <v-divider></v-divider>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="red" text @click="dialog = false">
          취소
        </v-btn>
        <v-btn color="primary" text @click="deleteFeed">
          삭제
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from "axios";

export default {
  props: ["feedSource"],
  data() {
    return {
      dialog: false
    };
  },
  methods: {
    deleteFeed() {
      axios(this.$store.state.requestUrl + "/api/feeds/" + this.feedSource.id, {
        method: "delete",
        withCredentials: true
      })
        .then(res => {
          if (res.status != 200) {
            alert("오류가 발생했습니다. 다시한번 확인해주세요");
            return;
          }
          alert("삭제 완료!");
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

<style>
.mini-button {
  width: 100px;
  max-width: 100px;
}
</style>
