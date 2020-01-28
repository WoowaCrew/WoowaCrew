<template>
  <v-text-field
    shaped
    dark
    solo
    outlined
    autocomplete="off"
    style="max-height: 56px"
    background-color="#214b7d"
    append-icon="fa-search search-icon"
    placeholder="Google 검색 또는 URL 입력"
  />
</template>

<script>
export default {
  name: "SearchBox"
};
</script>

<style>
.search-icon {
  color: rgba(255, 255, 255, 0.4) !important;
}

.search-icon:hover {
  cursor: pointer;
}
</style>
