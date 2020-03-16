import store from "../../../store/index.js";
import {AxiosSupplier} from "./axiosSupplier";

var Container = require("typedi").Container;

describe('AxiosSupplier', () => {
  describe('dependency injection', () => {
    test("should be defined", () => {
      expect(Container.get(AxiosSupplier)).toBeDefined();
    });
  });

  describe('#get()', () => {
    test("should return AxiosInstance", () => {
      let axiosSupplier = Container.get(AxiosSupplier);
      let axiosInstance = axiosSupplier.get();
      expect(typeof axiosInstance).toEqual(typeof Function);
      expect(axiosInstance.get()).toBeDefined();
      expect(axiosInstance.post()).toBeDefined();
      expect(axiosInstance.delete()).toBeDefined();
      expect(axiosInstance.post()).toBeDefined();
      expect(axiosInstance.defaults.baseURL).toEqual(store.state.requestUrl);
    });
  })
});



