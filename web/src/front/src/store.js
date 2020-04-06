import Vue from "vue";
import Vuex from "vuex";
import axios from 'axios';

Vue.use(Vuex);

var getCheckedCompaniesQuery = (companies) => {
  var companiesQuery = '';
  companies.forEach(company => {
    companiesQuery += '&companies='+company
  });
  return companiesQuery;
}
var getTagsQuery = (tags) => {
  var tagsQuery = '';
  tags.forEach(tag => {
    tagsQuery += '&tags='+tag.tagName
  });
  return tagsQuery;
}
var getOffsetQuery = (offset) => '&offset=' + offset;

export default new Vuex.Store({
  state: {
    selectedMenu: 'recruit-posts',
    posts: [],
    checkedCompanies: [],
    offset: 0,
    tags: []
  },
  getters: {
    selectedMenu: state => state.selectedMenu,
    posts: state => state.posts,
    checkedCompanies: state => state.checkedCompanies,
    offset: state => state.offset,
    tags: state => state.tags
  },
  mutations: {
    updatePosts: state => {
      axios.get('http://127.0.0.1:8080/api/v1/'+state.selectedMenu+'?'
                + getOffsetQuery(state.offset)
                + getCheckedCompaniesQuery(state.checkedCompanies)
                + getTagsQuery(state.tags))
      .then(response => {
        state.posts = response.data.data.posts
        state.offset = response.data.data.offset
      })
      .catch(e => {
        console.log('error : ', e)
      })
    },
    updateCheckedCompanies: (state, payload) => state.checkedCompanies = payload.checkedCompanies,
    resetOffset: state => state.offset = 0,
    insertTag: (state, payload) => 
      state.tags = [...state.tags, { id: new Date().getTime(), tagName: payload.tagName}],
    removeTag: (state, payload) =>  {
      state.tags = state.tags.filter(tag => tag.id !== payload.id);
    },
    removeAllTag: state => state.tags = [],
    updateSelectedMenu: (state, payload) => {
      state.selectedMenu = payload.selectedMenu,
      state.posts = []
      state.checkedCompanies = []
      state.tags = []
      state.offset = 0
    }
  },
  actions: {
    updatePosts: context => context.commit('updatePosts'),
    updateCheckedCompanies: (context, payload) => context.commit('updateCheckedCompanies', { checkedCompanies: payload.checkedCompanies }),
    resetOffset: context => context.commit('resetOffset'),
    insertTag: (context, payload) => context.commit('insertTag', { tagName: payload.tagName }),
    removeTag: (context, payload) => context.commit('removeTag', { id: payload.id }),
    removeAllTag: context => context.commit('removeAllTag'),
    updateSelectedMenu: (context, payload) => context.commit('updateSelectedMenu', { selectedMenu: payload.selectedMenu })
  }
});