<template>
  <div class="recruit-posts">
    <CompanyList/>
    <ul>
      <li
        v-for="({
          id, 
          companyTypeName, 
          companyUrlTypeName, 
          title,
          closingDate }) in posts"
        :key="id"
      >
        <div>{{ companyTypeName }}</div>
        <div>{{ companyUrlTypeName }}</div>
        <div>{{ title }}</div>
        <div>{{ closingDate }}</div>
      </li>
    </ul>
    </div>
</template>

<script>
import CompanyList from '@/components/company/CompanyList';
import { mapGetters } from "vuex";

export default {
  components: {
    CompanyList
  },
  computed: {
    ...mapGetters(['posts'])
  }
}
</script>

<style scoped>
.recruit-posts ul {
  padding: 20px 20px;
}
</style>