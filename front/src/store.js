import Vue from "vue";
import Vuex from "vuex";

import Constant from '@/constant/Constant';
import { setHeader } from './libs/axios.custom';
import axios from './libs/axios.custom';

Vue.use(Vuex);

const debug = process.env.VUE_APP_ENV === 'development';

const initUser = (store) => {
  const { ACCESS_TOKEN } = localStorage;
  if (ACCESS_TOKEN) {
    store.commit('setToken', ACCESS_TOKEN);
  }
};

var getCheckedCompanyQuery = (company) => {
  return '&company='+company;
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
var getSortOrderQuery = (sortOrder) => '&orderType=' + sortOrder;
var getFilterTypeQuery = (filterType) => '&filterType=' + filterType;

export default new Vuex.Store({
  plugins: [initUser],
  state: {
    authenticated: false,
    token: null,
    currentUser: null,
    selectedMenu: 'recruit-posts',
    posts: [],
    post: {},
    checkedCompany: 'ALL',
    offset: 0,
    tags: [],
    sortOrder: 'DESC',
    filterType: 'ALL',
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
    isClickedMyLike: false,
    isOnChatRooms: false,
    chatConnect: {
      connected: false,
      sock: null,
      ws: null,
      reconnect: 0,
      subscribeObject: null
    },
    isLogined: false,
  },
  getters: {
    token: state => state.token,
    currentUser: state => state.currentUser,
    authenticated: state => state.authenticated,
    selectedMenu: state => state.selectedMenu,
    posts: state => state.posts,
    post: state => state.post,
    checkedCompany: state => state.checkedCompany,
    offset: state => state.offset,
    tags: state => state.tags,
    sortOrder: state => state.sortOrder,
    filterType: state => state.filterType,
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
    isClickedMyLike: state => state.isClickedMyLike,
    isOnChatRooms: state => state.isOnChatRooms,
    chatConnect: state => state.chatConnect,
    isLogined: state => state.token !== null && state.token !== 'null' && state.token !== ''
  },
  mutations: {
    setToken(state, accessToken) {
      state.token = accessToken;
      localStorage.ACCESS_TOKEN = accessToken;
      setHeader(accessToken);
    },
    setCurrentUser(state, payload) {
      state.currentUser = payload;
      state.authenticated = payload !== null;
    },
    updatePosts: state => {
      if (state.offset === 0) {
        state.isLoadingContent = true;
      }

      let updatingMenu = state.selectedMenu;

      axios.get(`${process.env.VUE_APP_API}/api/v1/`+state.selectedMenu+'?'
                + getOffsetQuery(state.offset)
                + getLimitQuery(Constant.POST_LIMIT)
                + getCheckedCompanyQuery(state.checkedCompany)
                + getTagsQuery(state.tags)
                + '&liked='+ (state.isClickedMyLike ? 'true' : 'false')
                + getSortOrderQuery(state.sortOrder)
                + getFilterTypeQuery(state.filterType)
                + '&onlyMyPost='+ (state.isClickedMyPost ? 'true' : 'false'))
      .then(response => {
        if (state.selectedMenu !== updatingMenu) {
          return;
        }
        state.posts = response.data.posts
        state.offset = response.data.offset
        if (response.data.searchCount != null) {
          state.postCounts = response.data.searchCount
        }
      })
      .catch(e => {
        console.log('error : ', e)
      })
      .finally(() => {
        state.isLoadingContent = false;
      })
    },
    insertPosts: (state, payload) => {
      if (state.offset === 0) {
        state.isLoadingContent = true;
      }
      
      let updatingMenu = state.selectedMenu;
      const updatingcheckedCompany = state.checkedCompany;

      axios.get(`${process.env.VUE_APP_API}/api/v1/`+state.selectedMenu+'?'
                + getOffsetQuery(state.offset)
                + getLimitQuery(Constant.POST_LIMIT)
                + getCheckedCompanyQuery(state.checkedCompany)
                + getTagsQuery(state.tags)
                + '&liked='+ (state.isClickedMyLike ? 'true' : 'false')
                + getSortOrderQuery(state.sortOrder)
                + getFilterTypeQuery(state.filterType)
                + '&onlyMyPost='+ (state.isClickedMyPost ? 'true' : 'false'))
      .then(({ data }) => {
        if (state.selectedMenu !== updatingMenu || state.checkedCompany != updatingcheckedCompany)
          return;
        if (data.posts.length) {
          state.offset = data.offset
          if (data.searchCount) {
            state.postCounts = data.searchCount
          }
          state.posts.push(...data.posts)
          payload.infiniteState.loaded(); 
        } else {
          payload.infiniteState.complete();
        }
      }).catch(e => {
        console.log('error : ', e)
      })
      .finally(() => {
        state.isLoadingContent = false;
      })
    },
    removePost: (state, payload) => state.posts = state.posts.filter(post => post.id !== payload),
    updatePost: (state, payload) => state.post = payload,
    updateCheckedCompany: (state, payload) => state.checkedCompany = payload.checkedCompany,
    resetOffset: state => state.offset = 0,
    updateOffset: (state, payload) => state.offset = payload.offset,
    insertTag: (state, payload) => 
      state.tags = [...state.tags, { id: new Date().getTime(), tagName: payload.tagName}],
    removeTag: (state, payload) =>  {
      state.tags = state.tags.filter(tag => tag.id !== payload.id);
    },
    removeAllTag: state => state.tags = [],
    updateSortOrder: (state, payload) => state.sortOrder = payload,
    updateFilterType: (state, payload) => state.filterType = payload,
    updateSelectedMenu: (state, payload) => state.selectedMenu = payload.selectedMenu,
    resetAll: state => {
      state.selectedMenu = 'recruit-posts';
      state.posts = [];
      state.checkedCompany = [];
      state.tags = [];
      state.offset = 0;
      state.postCounts = 0;
      state.modalState = false;
      state.sortOrder = 'DESC';
      state.filterType = 'ALL';
    },
    updatePostCounts: (state, payload) => state.postCounts = payload.postCounts,
    updateChatRooms: (state, payload) => state.chatRooms = payload,
    insertSelectedChatRooms: (state, payload) => state.selectedChatRooms.push(payload),
    removeSelectedChatRoom: (state, payload) => {
      state.selectedChatRooms = state.selectedChatRooms.filter(room => room.id !== payload);
    },
    onModalState: state => state.modalState = true,
    offModalState: state => state.modalState = false,
    updateModalContent: (state, payload) => state.modalContent = payload,
    updatePostState: (state, payload) => state.postState = payload,
    updateIsLoadingContent: (state, payload) => state.isLoadingContent = payload,
    updateIsClickedClickMenu: (state, payload) => state.isClickedClickMenu = payload,
    updateClickMenuList: (state, payload) => state.clickMenuList = payload,
    updateClickMenuLocation: (state, payload) => state.clickMenuLocation = payload,
    updateIsClickedMyLike: (state, payload) => state.isClickedMyLike = payload,
    updateIsOnChatRooms: (state, payload) => state.isOnChatRooms = payload,
    updateChatConnect: (state, payload) => state.chatConnect = payload,
  },
  actions: {
    setToken: (context, payload) => context.commit('setToken', payload),
    setCurrentUser: (context, payload) => context.commit('setCurrentUser', payload),
    updatePosts: context => context.commit('updatePosts'),
    insertPosts: (context, payload) => context.commit('insertPosts', { infiniteState: payload.infiniteState }),
    removePost: (context, payload) => context.commit('removePost', payload),
    updatePost: (context, payload) => context.commit('updatePost', payload),
    updateCheckedCompany: (context, payload) => context.commit('updateCheckedCompany', { checkedCompany: payload.checkedCompany }),
    resetOffset: context => context.commit('resetOffset'),
    updateOffset: (context, payload) => context.commit('updateOffset', { offset: payload.offset }),
    insertTag: (context, payload) => context.commit('insertTag', { tagName: payload.tagName }),
    removeTag: (context, payload) => context.commit('removeTag', { id: payload.id }),
    removeAllTag: context => context.commit('removeAllTag'),
    updateSortOrder: (context, payload) => context.commit('updateSortOrder', payload),
    updateFilterType: (context, payload) => context.commit('updateFilterType', payload),
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
    updateIsClickedMyLike: (context, payload) => context.commit('updateIsClickedMyLike', payload),
    updateIsOnChatRooms: (context, payload) => context.commit('updateIsOnChatRooms', payload),
    updateChatConnect: (context, payload) => context.commit('updateChatConnect', payload),
  },
  strict: debug
});