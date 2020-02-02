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
    path: "/login",
    name: "login",
    component: () =>
      import(/* webpackChunkName: "login" */ "../components/Login.vue")
  },
  {
    path: "/accessdeny",
    name: "accessdeny",
    component: () =>
      import(
        /* webpackChunkName: "accessDeny" */ "../components/AccessDeny.vue"
      )
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
    path: "/articles/free/:articleId/modify",
    name: "freeArticleModify",
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
    path: "/articles/crew/:articleId/modify",
    name: "crewArticleModify",
    component: () =>
      import(
        /* webpackChunkName: "articleEdit" */ "../components/article/ArticleEdit"
      )
  },
  {
    path: "/articles/free",
    name: "freeArticlesList",
    component: () =>
      import(
        /* webpackChunkName: "ArticleList" */ "../components/article/ArticleList"
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
    path: "/articles/crew",
    name: "crewArticlesList",
    component: () =>
      import(
        /* webpackChunkName: "ArticleList" */ "../components/article/ArticleList"
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
    path: "/articles/feed",
    name: "FeedArticleList",
    component: () =>
      import(
        /* webpackChunkName: "FeedArticleList" */ "../components/article/FeedArticleList"
      )
  },
  {
    path: "/slack/notice/:articleId",
    name: "slackNoticeView",
    component: () =>
      import(
        /* webpackChunkName: "slackArticleView" */ "../components/slack/SlackArticleView"
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
  },
  {
    path: "/admin/crew",
    name: "admin-crew",
    component: () =>
      import(/* webpackChunkName: "AdminCrew" */ "../components/admin/Crew")
  },
  {
    path: "/admin/crew/detail",
    name: "admin-crew-detail",
    component: () =>
      import(
        /* webpackChunkName: "AdminCrewDetail" */ "../components/admin/CrewDetail"
      )
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
