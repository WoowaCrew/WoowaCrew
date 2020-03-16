import Vue from "vue";
import Vuex from "vuex";

import {HttpClient} from "../api/httpClient/httpClient";
import {method} from "../api/httpClient/axios/axiosMethod";

Vue.use(Vuex);

const container = require("typedi").Container;
const httpClient = container.get(HttpClient);

export default new Vuex.Store({
  state: {
    userContext: null,
    requestUrl: window.location.origin
  },
  mutations: {
    setUserContext(state, userContext) {
      state.userContext = userContext;
    },
    removeUserContext(state) {
      state.userContext = null;
    }
  },
  actions: {
    async checkLogin({ commit}) {
      try {
        let response = await httpClient.call(method.GET, "/login/info", {
          withCredentials: true
        });
        commit("setUserContext", response.data);
      } catch (error) {
        const statusCode = error.response.status;
        if (statusCode === 401) {
          console.log("로그인 되어있지 않음");
        }
      }
    },

    async logout({ commit }) {
      let response = await httpClient.call(method.GET, "/logout", {
        withCredentials: true
      });
      if (response.status === 200) {
        commit("removeUserContext");
        window.location.href = "/";
      }
    }
  },
  modules: {}
});
