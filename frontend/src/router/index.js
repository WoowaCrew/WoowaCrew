import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: Home
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
    path: "/articles/:articleId",
    name: "articleView",
    component: () =>
      import(
        /* webpackChunkName: "articleView" */ "../components/article/ArticleView"
      )
  },
  {
    path: "/admin",
    name: "admin",
    component: () =>
      import(/* webpackChunkName: "admin" */ "../components/admin/Admin"),
    children: [
      {
        path: ":id",
        name: "users-detail",
        component: () =>
          import(
            /* webpackChunkName: "UserApprove" */ "../components/admin/UserApprove"
          )
      }
    ]
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
