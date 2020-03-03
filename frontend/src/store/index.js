import Vue from "vue";
import Vuex from "vuex";
import {AxiosSupplier} from "../api/httpClient/axios/axios.supplier";

Vue.use(Vuex);

var container = require("typedi").Container;
let axiosSupplier = container.get(AxiosSupplier);
let axiosInstance = axiosSupplier.get();

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
      axiosInstance
        .get(state.requestUrl + "/login/info", {
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
      axiosInstance
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
