<template>
  <v-card height="100%">
    <v-simple-table class="mx-auto user-table" fixed-header height="600px">
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-center mini-cell">아이디</th>
            <th class="text-center mini-cell">기수</th>
            <th class="text-center">닉네임</th>
            <th class="text-left">권한</th>
            <th />
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in userData" :key="item.id" :id="item.id">
            <td class="text-center mini-cell">{{ item.id }}</td>
            <td class="text-center mini-cell">
              {{ item.degreeResponseDto.degreeNumber }}
            </td>
            <td class="text-center">{{ item.nickname }}</td>
            <td class="text-left">{{ item.userRole }}</td>
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
        this.userData = object;
      });
  }
};
</script>

<style>
.v-data-table {
  width: 100%;
}
.mini-cell {
  width: 100px;
  max-width: 120px;
}
</style>
