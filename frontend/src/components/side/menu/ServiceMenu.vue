<template>
  <v-list>
    <div v-for="crewService in crewServices" :key="crewService.headerName">
      <v-list-item class="menu-header">
        <v-list-item-content>
          <v-list-item-title> {{ crewService.headerName }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list class="menu-buttons" dense nav>
        <v-list-item
          v-for="service in crewService.services"
          :key="service.title"
          link
          v-on:click="move(service)"
        >
          <v-list-item-content>
            <v-list-item-title style="color: #212121">
              {{ service.title }}
            </v-list-item-title>
            <v-list-item-title>
              {{ service.description }}
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>

      <v-divider />
    </div>
  </v-list>
</template>

<script>
export default {
  name: "ServiceMenu",
  data() {
    return {
      crewServices: [
        {
          headerName: "1기 크루들의 서비스",
          services: [
            {
              title: "테코브러리",
              description: "책을 빌려 볼까요?",
              url: "test"
            },
            {
              title: "우테코러닝",
              description: "스터디를 알아볼까요?",
              url: "test"
            },
            {
              title: "개장모",
              description: "개발자들의 장비가 궁금하신가요?",
              url: "https://gaejangmo.com/"
            }
          ]
        }
      ]
    };
  },
  methods: {
    move: function(service) {
      window.open(service.url);
    }
  }
};
</script>
