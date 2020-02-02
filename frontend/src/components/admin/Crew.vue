<template>
  <v-card height="100%">
    <v-simple-table class="mx-auto user-table" fixed-header height="600px">
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-center">기수</th>
            <th class="text-center">인원</th>
            <th class="text-center" />
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in degreeData" :key="item.id" :id="item.id">
            <td class="text-center">{{ item.degreeNumber }}</td>
            <td class="text-center">{{ item.userCount }}</td>
            <td>
              <v-btn
                width="10"
                color="primary"
                block
                @click="
                  $router.push({
                    name: 'admin-crew-detail',
                    query: { degreeId: item.id }
                  })
                "
              >
                상세보기
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
  data() {
    return {
      degreeData: []
    };
  },
  created() {
    axios
      .get(this.$store.state.requestUrl + "/api/degrees", {
        withCredentials: true
      })
      .then(res => {
        const object = res.data;
        this.degreeData = object;
      });
  }
};
</script>

<style>
.v-data-table {
  width: 100%;
}
</style>
