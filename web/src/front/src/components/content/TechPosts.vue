<template>
  <div class="tech-posts">
    <CompanyList/>
    <ul>
      <li
        v-for="({
          id, 
          companyTypeName, 
          companyUrlTypeName, 
          title,
          author,
          date,
          thumnailUrl }) in posts"
        :key="id"
      >
        <div>{{ companyTypeName }}</div>
        <div>{{ companyUrlTypeName }}</div>
        <div>{{ title }}</div>
        <div>{{ author }}</div>
        <div>{{ date }}</div>
        <div>{{ thumnailUrl }}</div>
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
.tech-posts ul {
  padding: 20px 20px;
}
</style>