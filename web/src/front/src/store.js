import Vue from "vue";
import Vuex from "vuex";
import axios from 'axios';

Vue.use(Vuex);

var getCheckedCompaniesQuery = (companies) => {
  var companiesQuery = '';
  console.log('ㅎㅇㅎㅇ', companies)
  companies.forEach(company => {
    companiesQuery += '&companies='+company
  });
  return companiesQuery;
}

var getTitleQuery = (title) => '&title=' + title;
var getOffsetQuery = (offset) => '&offset=' + offset;

export default new Vuex.Store({
  state: {
    posts: [],
    checkedCompanies: [],
    offset: 0
  },
  getters: {
    posts: state => state.posts,
    checkedCompanies: state => state.checkedCompanies,
    offset: state => state.offset
  },
  mutations: {
    updatePosts: state => {
      console.log(state)
      axios.get('http://127.0.0.1:8080/api/v1/recruit-posts?'
                + getCheckedCompaniesQuery(state.checkedCompanies))
      .then(response => {
        state.posts = response.data.data.recruitPosts
        state.offset = response.data.data.offset
      })
      .catch(e => {
        console.log('error : ', e)
      })
    },
    updatePostsByTitle: (state, payload) => {
      axios.get('http://127.0.0.1:8080/api/v1/recruit-posts?'
                + getOffsetQuery(state.offset)
                + getCheckedCompaniesQuery(state.checkedCompanies)
                + getTitleQuery(payload.title))
      .then(response => {
        state.posts = response.data.data.recruitPosts
        state.offset = response.data.data.offset
      })
      .catch(e => {
        console.log('error : ', e)
      })
    },
    updateCheckedCompanies: (state, payload) => state.checkedCompanies = payload.checkedCompanies
  },
  actions: {
    updatePosts: context => context.commit('updatePosts'),
    updatePostsByTitle: (context, payload) => context.commit('updatePostsByTitle', { title: payload.title }),
    updateCheckedCompanies: (context, payload) => context.commit('updateCheckedCompanies', { checkedCompanies: payload.checkedCompanies })
  }
});