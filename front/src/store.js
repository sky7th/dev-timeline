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
var getSortOrderQuery = (sortOrder) => '&sortOrderType=' + sortOrder;

export default new Vuex.Store({
  plugins: [initUser],
  state: {
    authenticated: false,
    token: null,
    currentUser: null,
    selectedMenu: 'recruit-posts',
    posts: [],
    post: {},
    checkedCompanies: [],
    offset: 0,
    tags: [],
    sortOrder: 'DESC',
    postCounts: 0,
    chatRooms: [],
    selectedChatRooms: [],
    modalState: false,
    modalContent: '',
    postState: Constant.READ,
    isLoadingContent: false,
    isClickedClickMenu: false,
    clickMenuList: [],
    clickMenuLocation: [],
    isClickedMyLike: false
  },
  getters: {
    token: state => state.token,
    currentUser: state => state.currentUser,
    authenticated: state => state.authenticated,
    selectedMenu: state => state.selectedMenu,
    posts: state => state.posts,
    post: state => state.post,
    checkedCompanies: state => state.checkedCompanies,
    offset: state => state.offset,
    tags: state => state.tags,
    sortOrder: state => state.sortOrder,
    postCounts: state => state.postCounts,
    chatRooms: state => state.chatRooms,
    selectedChatRooms: state => state.selectedChatRooms,
    modalState: state => state.modalState,
    modalContent: state => state.modalContent,
    postState: state => state.postState,
    isLoadingContent: state => state.isLoadingContent,
    isClickedClickMenu: state => state.isClickedClickMenu,
    clickMenuList: state => state.clickMenuList,
    clickMenuLocation: state => state.clickMenuLocation,
    isClickedMyLike: state => state.isClickedMyLike
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
      let updatingMenu = state.selectedMenu;
      state.isLoadingContent = true;
      axios.get(`${process.env.VUE_APP_API}/api/v1/`+state.selectedMenu+'?'
                + getOffsetQuery(state.offset)
                + getLimitQuery(Constant.POST_LIMIT)
                + getCheckedCompaniesQuery(state.checkedCompanies)
                + getTagsQuery(state.tags)
                + '&liked='+ (state.isClickedMyLike ? 'true' : 'false')
                + getSortOrderQuery(state.sortOrder))
      .then(response => {
        if (state.selectedMenu !== updatingMenu)
          return;
        state.posts = response.data.posts
        state.offset = response.data.offset
        state.postCounts = response.data.searchCount
      })
      .catch(e => {
        console.log('error : ', e)
      })
      .finally(() => {
        state.isLoadingContent = false;
      })
    },
    insertPosts: (state, payload) => {
      let updatingMenu = state.selectedMenu;
      // state.isLoadingContent = true;
      axios.get(`${process.env.VUE_APP_API}/api/v1/`+state.selectedMenu+'?'
                + getOffsetQuery(state.offset)
                + getLimitQuery(Constant.POST_LIMIT)
                + getCheckedCompaniesQuery(state.checkedCompanies)
                + getTagsQuery(state.tags)
                + '&liked='+ (state.isClickedMyLike ? 'true' : 'false')
                + getSortOrderQuery(state.sortOrder))
      .then(({ data }) => {
        if (state.selectedMenu !== updatingMenu)
          return;
        if (data.posts.length) {
          state.offset = data.offset
          state.postCounts = data.searchCount
          state.posts.push(...data.posts)
          payload.infiniteState.loaded(); 
        } else {
          payload.infiniteState.complete();
        }
      }).catch(e => {
        console.log('error : ', e)
      })
      // .finally(() => {
      //   state.isLoadingContent = false;
      // })
    },
    removePost: (state, payload) => state.posts = state.posts.filter(post => post.id !== payload),
    updatePost: (state, payload) => state.post = payload,
    updateCheckedCompanies: (state, payload) => state.checkedCompanies = payload.checkedCompanies,
    resetOffset: state => state.offset = 0,
    updateOffset: (state, payload) => state.offset = payload.offset,
    insertTag: (state, payload) => 
      state.tags = [...state.tags, { id: new Date().getTime(), tagName: payload.tagName}],
    removeTag: (state, payload) =>  {
      state.tags = state.tags.filter(tag => tag.id !== payload.id);
    },
    removeAllTag: state => state.tags = [],
    updateSortOrder: (state, payload) => state.sortOrder = payload,
    updateSelectedMenu: (state, payload) => state.selectedMenu = payload.selectedMenu,
    resetAll: state => {
      state.selectedMenu = 'recruit-posts';
      state.posts = [];
      state.checkedCompanies = [];
      state.tags = [];
      state.offset = 0;
      state.postCounts = 0;
      state.modalState = false;
      state.sortOrder = 'DESC';
    },
    updatePostCounts: (state, payload) => state.postCounts = payload.postCounts,
    updateChatRooms: (state, payload) => state.chatRooms = payload,
    insertSelectedChatRooms: (state, payload) => state.selectedChatRooms.push(payload),
    removeSelectedChatRoom: (state, payload) => {
      state.selectedChatRooms = state.selectedChatRooms.filter(room => room.roomId !== payload);
    },
    onModalState: state => state.modalState = true,
    offModalState: state => state.modalState = false,
    updateModalContent: (state, payload) => state.modalContent = payload,
    updatePostState: (state, payload) => state.postState = payload,
    updateIsLoadingContent: (state, payload) => state.isLoadingContent = payload,
    updateIsClickedClickMenu: (state, payload) => state.isClickedClickMenu = payload,
    updateClickMenuList: (state, payload) => state.clickMenuList = payload,
    updateClickMenuLocation: (state, payload) => state.clickMenuLocation = payload,
    updateIsClickedMyLike: (state, payload) => state.isClickedMyLike = payload
  },
  actions: {
    setToken: (context, payload) => context.commit('setToken', payload),
    setUserDetail: (context, payload) => context.commit('setUserDetail', payload),
    updatePosts: context => context.commit('updatePosts'),
    insertPosts: (context, payload) => context.commit('insertPosts', { infiniteState: payload.infiniteState }),
    removePost: (context, payload) => context.commit('removePost', payload),
    updatePost: (context, payload) => context.commit('updatePost', payload),
    updateCheckedCompanies: (context, payload) => context.commit('updateCheckedCompanies', { checkedCompanies: payload.checkedCompanies }),
    resetOffset: context => context.commit('resetOffset'),
    updateOffset: (context, payload) => context.commit('updateOffset', { offset: payload.offset }),
    insertTag: (context, payload) => context.commit('insertTag', { tagName: payload.tagName }),
    removeTag: (context, payload) => context.commit('removeTag', { id: payload.id }),
    removeAllTag: context => context.commit('removeAllTag'),
    updateSortOrder: (context, payload) => context.commit('updateSortOrder', payload),
    updateSelectedMenu: (context, payload) => context.commit('updateSelectedMenu', { selectedMenu: payload.selectedMenu }),
    resetAll: context => context.commit('resetAll'),
    updatePostCounts: (context, payload) => context.commit('postCounts', { postCounts: payload.postCounts }),
    updateChatRooms: (context, payload) => context.commit('updateChatRooms', payload),
    insertSelectedChatRooms: (context, payload) => context.commit('insertSelectedChatRooms', payload),
    removeSelectedChatRoom: (context, payload) => context.commit('removeSelectedChatRoom', payload),
    onModalState: context => context.commit('onModalState'),
    offModalState: context => context.commit('offModalState'),
    updateModalContent: (context, payload) => context.commit('updateModalContent', payload),
    updatePostState: (context, payload) => context.commit('updatePostState', payload),
    updateIsLoadingContent: (context, payload) => context.commit('updateIsLoadingContent', payload),
    updateIsClickedClickMenu: (context, payload) => context.commit('updateIsClickedClickMenu', payload),
    updateClickMenuList: (context, payload) => context.commit('updateClickMenuList', payload),
    updateClickMenuLocation: (context, payload) => context.commit('updateClickMenuLocation', payload),
    updateIsClickedMyLike: (context, payload) => context.commit('updateIsClickedMyLike', payload)
  },
  strict: debug
});