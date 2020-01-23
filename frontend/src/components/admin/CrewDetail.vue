<template>
  <div>
    크루상세페이지
    {{ degreeNumber }}
  </div>
</template>

<script>
export default {
  data() {
    return {
      degreeNumber: this.$route.query.degreeNumber
    };
  }
};
</script>

<style></style>
