<template>
  <div id="home">
    <Header/>
    <div class="wrap">
      <SideMenu/>
      <Content
        :posts="posts"
        @updatePosts="updatePosts"
        @updateCompanies="updateCompanies"
      />
    </div>
  </div>
</template>

<script>
import axios from 'axios';

import Header from '@/components/Header';
import Content from '@/components/Content';
import SideMenu from '@/components/SideMenu';

var getCompaniesQuery = (companies) => {
  var companiesQuery = '';
  companies.forEach(company => {
    companiesQuery += '&companies='+company
  });
  return companiesQuery;
}

var getTitleQuery = (title) => '&title=' + title;
var getOffsetQuery = (offset) => '&offset=' + offset;

export default {
  components: {
    Header,
    Content,
    SideMenu
  },
  data: () => {
    return {
      posts: [],
      companies: [],
      offset: 0
    }
  },
  created () { 
    axios.get('http://127.0.0.1:8080/api/v1/recruit-posts?'
              + getOffsetQuery(this.offset))
      .then(response => {
        this.posts = response.data.data.recruitPosts
        this.offset = response.data.data.offset
      })
      .catch(e => {
        console.log('error : ', e)
      })
  },
  methods: {
    updatePosts() {
      axios.get('http://127.0.0.1:8080/api/v1/recruit-posts?'
                + getCompaniesQuery(this.companies))
      .then(response => {
        console.log(response)
        this.posts = response.data.data.recruitPosts
        this.offset = response.data.data.offset
      })
      .catch(e => {
        console.log('error : ', e)
      })
    },
    updatePostsByTitle(title) {
      axios.get('http://127.0.0.1:8080/api/v1/recruit-posts?'
                + getOffsetQuery(this.offset)
                + getCompaniesQuery(this.companies)
                + getTitleQuery(title))
      .then(response => {
        this.posts = response.data.data.recruitPosts
        this.offset = response.data.data.offset
      })
      .catch(e => {
        console.log('error : ', e)
      })
    },
    updateCompanies(companies) {
      this.companies = companies
    }
  }
};
</script>

<style>
#home {
  height: 100%;
}
.wrap {
  display: flex;
}
</style>