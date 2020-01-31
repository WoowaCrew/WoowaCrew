import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";

Vue.use(Vuex);

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
    checkLogin({ commit, state }) {
      axios
        .get(state.requestUrl + "/api/login/info", {
          withCredentials: true
        })
        .then(res => {
          commit("setUserContext", res.data);
        })
        .catch(error => {
          const statusCode = error.response.status;
          if (statusCode === 401) {
            console.log("로그인 되어있지 않음");
            return;
          }
        });
    },
    logout({ commit, state }) {
      axios
        .get(state.requestUrl + "/logout", {
          withCredentials: true
        })
        .then(res => {
          if (res.status === 200) {
            commit("removeUserContext");
            window.location.href = "/";
          }
        });
    }
  },
  modules: {}
});
