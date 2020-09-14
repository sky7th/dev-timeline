import Vue from "vue";
import VueRouter from "vue-router";
import ErrorPage from "./views/ErrorPage";
import Home from "./views/Home";
import Login from './components/user/Login';
import Signup from './components/user/SignUp';
import auth from './libs/authentication';
import Oauth2redirect from './components/user/Oauth2redirect';

Vue.use(VueRouter); 

const router = new VueRouter({
  mode: "history", 
  routes: [
    {
      path: '/',
      component: Home,
    },
    {
      path: '/login',
      component: Login,
    },
    {
      path: '/signup',
      component: Signup,
    },
    {
      path: '/oauth2/redirect',
      component: Oauth2redirect,
      beforeEnter: auth.isAuthenticated,
    },
    {
      path: '*',
      component: ErrorPage
    }
  ],
});

export default router;