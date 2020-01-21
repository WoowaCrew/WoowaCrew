import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "index",
    component: () =>
      import(/* webpackChunkName: "index" */ "../components/Index.vue")
  },
  {
    path: "/about",
    name: "about",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue")
  },
  {
    path: "/user/edit",
    name: "userEdit",
    component: () =>
      import(/* webpackChunkName: "userEdit" */ "../components/user/UserEdit")
  },
  {
    path: "/article/free/new",
    name: "freeArticleEdit",
    component: () =>
      import(
        /* webpackChunkName: "articleEdit" */ "../components/article/ArticleEdit"
      )
  },
  {
    path: "/article/crew/new",
    name: "crewArticleEdit",
    component: () =>
      import(
        /* webpackChunkName: "articleEdit" */ "../components/article/ArticleEdit"
      )
  },
  {
    path: "/articles/free/:articleId",
    name: "freeArticleView",
    component: () =>
      import(
        /* webpackChunkName: "articleView" */ "../components/article/ArticleView"
      )
  },
  {
    path: "/articles/crew/:articleId",
    name: "crewArticleView",
    component: () =>
      import(
        /* webpackChunkName: "articleView" */ "../components/article/ArticleView"
      )
  },
  {
    path: "/admin/user-approve",
    name: "user-approve",
    component: () =>
      import(
        /* webpackChunkName: "UserApprove" */ "../components/admin/UserApprove"
      )
  },
  {
    path: "/admin/rss",
    name: "admin-rss",
    component: () =>
      import(/* webpackChunkName: "AdminRss" */ "../components/admin/Rss")
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
