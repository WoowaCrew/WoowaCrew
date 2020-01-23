<template>
  <v-container fill-height>
    <SlackNoticeView
      v-if="isSlackNotice"
      :articleId="this.$route.params.articleId"
      @setupData="setData"
    ></SlackNoticeView>
    <v-layout row wrap>
      <v-flex fill-height>
        <v-card width="1000" class="overflow-auto mx-auto my-2 fill-height">
          <v-card-title>
            # {{ channel }} ({{ author }})
            <v-spacer></v-spacer>
            {{ createdDate }}
          </v-card-title>

          <v-card-text style="min-height: 100px">
            {{ content }}
          </v-card-text>

          <v-card-text
            v-if="downloadLink != null"
            style="background-color: #f5f5f5; padding: 26px"
          >
            <div style="margin-bottom: 10px">첨부 파일</div>
            <a :href="downloadLink">
              (1) 즉시 다운로드 받을 수 있는 링크 입니다.
            </a>
            <br />
            <a :href="downloadLinkFromSlack">
              (2) 슬랙으로 이동하여 다운로드 받을 수 있는 링크 입니다.
            </a>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import SlackNoticeView from "./view/SlackNoticeView";
export default {
  name: "SlackNotice",
  components: {
    SlackNoticeView
  },
  data() {
    return {
      channel: "",
      author: "",
      content: "",
      downloadLink: "",
      downloadLinkFromSlack: "",
      createdDate: "",
      path: this.$route.path
    };
  },
  computed: {
    dateCut() {
      return this.createdDate.split("T")[0];
    },
    isSlackNotice() {
      const slackNoticeViewPattern = new RegExp("/slack/notice/[0-9]+");

      return slackNoticeViewPattern.test(this.path);
    }
  },
  methods: {
    setData(data) {
      this.channel = data.channel;
      this.author = data.author;
      this.content = data.content;
      this.downloadLink = data.downloadLink;
      this.downloadLinkFromSlack = data.downloadLinkFromSlack;
      this.createdDate = data.createdDate;
    }
  }
};
</script>
