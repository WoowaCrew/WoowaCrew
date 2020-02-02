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
        수정
      </v-btn>
    </template>

    <v-card>
      <v-card-title
        class="headline primary"
        style="color: whitesmoke"
        primary-title
      >
        피드 정보
      </v-card-title>

      <v-card-text style="margin-top: 15px">
        <v-form>
          <v-text-field v-model="sourceUrl" readonly="true" label="주소" />
          <v-text-field v-model="description" label="설명" />
        </v-form>
      </v-card-text>

      <v-divider></v-divider>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="red" text @click="dialog = false">
          취소
        </v-btn>
        <v-btn color="primary" text @click="editFeed">
          수정
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
      sourceUrl: this.feedSource.sourceUrl,
      description: this.feedSource.description,
      dialog: false
    };
  },
  methods: {
    editFeed() {
      const data = new FormData();
      data.append("description", this.description);

      axios(this.$store.state.requestUrl + "/api/feeds/" + this.feedSource.id, {
        method: "put",
        data: data,
        withCredentials: true
      })
        .then(res => {
          if (res.status != 200) {
            alert("오류가 발생했습니다. 다시한번 확인해주세요");
            return;
          }
          alert("정상적으로 수정되었습니다");
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
