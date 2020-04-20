import Vue from "vue";
import Vuex from "vuex";

import Constant from '@/constant/Constant';
import { setHeader } from './libs/axios.custom';
import axios from './libs/axios.custom'

Vue.use(Vuex);

const debug = process.env.VUE_APP_ENV === 'development';

const initUser = (store) => {
  const { ACCESS_TOKEN } = localStorage;
  if (ACCESS_TOKEN) {
    store.commit('setToken', ACCESS_TOKEN);
  }
};

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
var getLimitQuery = (limit) => '&limit=' + limit;

export default new Vuex.Store({
  plugins: [initUser],
  state: {
    authenticated: false,
    token: null,
    currentUser: null,
    selectedMenu: 'recruit-posts',
    posts: [],
    checkedCompanies: [],
    offset: 0,
    tags: [],
    postCounts: 0,
    chatRooms: [],
    selectedChatRooms: [],
    modalState: false,
    postState: Constant.READ
  },
  getters: {
    token: state => state.token,
    currentUser: state => state.currentUser,
    authenticated: state => state.authenticated,
    selectedMenu: state => state.selectedMenu,
    posts: state => state.posts,
    checkedCompanies: state => state.checkedCompanies,
    offset: state => state.offset,
    tags: state => state.tags,
    postCounts: state => state.postCounts,
    chatRooms: state => state.chatRooms,
    selectedChatRooms: state => state.selectedChatRooms,
    modalState: state => state.modalState,
    postState: state => state.postState
  },
  mutations: {
    setToken(state, accessToken) {
      state.token = accessToken;
      localStorage.ACCESS_TOKEN = accessToken;
      setHeader(accessToken);
    },
    setUserDetail(state, payload) {
      state.currentUser = payload;
      state.authenticated = payload !== null;
    },
    updatePosts: state => {
      axios.get('http://127.0.0.1:8080/api/v1/'+state.selectedMenu+'?'
                + getOffsetQuery(state.offset)
                + getLimitQuery(Constant.POST_LIMIT)
                + getCheckedCompaniesQuery(state.checkedCompanies)
                + getTagsQuery(state.tags))
      .then(response => {
        state.posts = response.data.data.posts
        state.offset = response.data.data.offset
        state.postCounts = response.data.data.postCounts
      })
      .catch(e => {
        console.log('error : ', e)
      })
    },
    insertPosts: (state, payload) => {
      axios.get('http://127.0.0.1:8080/api/v1/'+state.selectedMenu+'?'
                + getOffsetQuery(state.offset)
                + getLimitQuery(Constant.POST_LIMIT)
                + getCheckedCompaniesQuery(state.checkedCompanies)
                + getTagsQuery(state.tags))
      .then(({ data }) => {
        if (data.data.posts.length) {
          state.offset = data.data.offset
          state.postCounts = data.data.postCounts
          state.posts.push(...data.data.posts)
          payload.infiniteState.loaded(); 
        } else {
          payload.infiniteState.complete();
        }
      }).catch(e => {
        console.log('error : ', e)
      })
    },
    updateCheckedCompanies: (state, payload) => state.checkedCompanies = payload.checkedCompanies,
    resetOffset: state => state.offset = 0,
    updateOffset: (state, payload) => state.offset = payload.offset,
    insertTag: (state, payload) => 
      state.tags = [...state.tags, { id: new Date().getTime(), tagName: payload.tagName}],
    removeTag: (state, payload) =>  {
      state.tags = state.tags.filter(tag => tag.id !== payload.id);
    },
    removeAllTag: state => state.tags = [],
    updateSelectedMenu: (state, payload) => state.selectedMenu = payload.selectedMenu,
    resetAll: state => {
      state.selectedMenu = 'recruit-posts';
      state.posts = [];
      state.checkedCompanies = [];
      state.tags = [];
      state.offset = 0;
      state.postCounts = 0;
      state.modalState = false;
    },
    updatePostCounts: (state, payload) => state.postCounts = payload.postCounts,
    updateChatRooms: (state, payload) => state.chatRooms = payload,
    insertSelectedChatRooms: (state, payload) => state.selectedChatRooms.push(payload),
    removeSelectedChatRoom: (state, payload) => {
      state.selectedChatRooms = state.selectedChatRooms.filter(room => room.roomId !== payload);
    },
    onModalState: state => state.modalState = true,
    offModalState: state => state.modalState = false,
    updatePostState: (state, payload) => state.postState = payload
  },
  actions: {
    setToken: (context, payload) => context.commit('setToken', payload),
    setUserDetail: (context, payload) => context.commit('setUserDetail', payload),
    updatePosts: context => context.commit('updatePosts'),
    insertPosts: (context, payload) => context.commit('insertPosts', { infiniteState: payload.infiniteState }),
    updateCheckedCompanies: (context, payload) => context.commit('updateCheckedCompanies', { checkedCompanies: payload.checkedCompanies }),
    resetOffset: context => context.commit('resetOffset'),
    updateOffset: (context, payload) => context.commit('updateOffset', { offset: payload.offset }),
    insertTag: (context, payload) => context.commit('insertTag', { tagName: payload.tagName }),
    removeTag: (context, payload) => context.commit('removeTag', { id: payload.id }),
    removeAllTag: context => context.commit('removeAllTag'),
    updateSelectedMenu: (context, payload) => context.commit('updateSelectedMenu', { selectedMenu: payload.selectedMenu }),
    resetAll: context => context.commit('resetAll'),
    updatePostCounts: (context, payload) => context.commit('postCounts', { postCounts: payload.postCounts }),
    updateChatRooms: (context, payload) => context.commit('updateChatRooms', payload),
    insertSelectedChatRooms: (context, payload) => context.commit('insertSelectedChatRooms', payload),
    removeSelectedChatRoom: (context, payload) => context.commit('removeSelectedChatRoom', payload),
    onModalState: context => context.commit('onModalState'),
    offModalState: context => context.commit('offModalState'),
    updatePostState: (context, payload) => context.commit('updatePostState', payload)
  },
  strict: debug
});