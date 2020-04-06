<template>
  <div class="recruit">
    <CompanyList/>
    <div class="post-list">
      <ul>
        <li>1. recruit</li>
        <li>2. recruit</li>
        <li>3. recruit</li>
      </ul>
    </div>
  </div>
</template>

<script>
import CompanyList from '@/components/company/CompanyList';

export default {
  components: {
    CompanyList
  }
}
</script>

<style scoped>
.post-list {
  padding: 20px 20px;
}
</style>