<template>
  <v-card height="100%">
    <v-simple-table class="mx-auto user-table" fixed-header height="600px">
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-left">아이디</th>
            <th class="text-left">닉네임</th>
            <th class="text-left">기수</th>
            <th class="text-left">권한</th>
            <th class="text-left">수정 버튼</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in userData" :key="item.id" :id="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.nickname }}</td>
            <td>{{ item.degreeResponseDto.degreeNumber }}</td>
            <td>{{ item.userRole }}</td>
            <td>
              <CrewEditButton
                :user="{
                  id: item.id,
                  degreeNumber: item.degreeResponseDto.degreeNumber,
                  userRole: item.userRole
                }"
              />
            </td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
  </v-card>
</template>

<script>
import axios from "axios";
import CrewEditButton from "./crew/CrewEditButton";

export default {
  components: {
    CrewEditButton
  },
  data() {
    return {
      degreeId: this.$route.query.degreeId,
      userData: []
    };
  },
  created() {
    axios
      .get(this.$store.state.requestUrl + "/api/degrees/" + this.degreeId + "/users", {
        withCredentials: true
      })
      .then(res => {
        const object = res.data;
        console.log(object);
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
