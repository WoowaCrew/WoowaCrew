import {HttpClient} from "./http.client";
import {AxiosSupplier} from "./axios/axios.supplier";
import {method} from "./axios/axios.method";

jest.mock('axios');
jest.mock('./axios/axios.supplier');
jest.mock('./axios/axios.method');


const mockAxios = require('axios');
const mockAxiosSupplier = require('./axios/axios.supplier');
mockAxiosSupplier.get = jest.fn().mockReturnValue(mockAxios);
const container = require("typedi").Container;
container.set(AxiosSupplier, mockAxiosSupplier);


describe('HttpClient', () => {
  describe('dependency injection', () => {
    test('should be defined', () => {
      let httpClient = container.get(HttpClient);
      expect(httpClient).toBeDefined();
      expect(mockAxiosSupplier.get).toHaveBeenCalled();
      expect(httpClient.axiosInstance).toEqual(mockAxios);
    });
  });

  describe('#call()', () => {
    const axiosMethod = require('./axios/axios.method');
    const httpClient = new HttpClient(container);
    test('should return response data', async () => {
      const responseData =  "response data";
      axiosMethod.getMethod = jest.fn().mockReturnValue(() => new Promise((resolve) => resolve(responseData)));

      expect(await httpClient.call(method.GET, "testUrl")).toEqual(responseData);
      expect(axiosMethod.getMethod).toHaveBeenCalled();
    });

    test('should throw Error', () => {
      const expectedError = new Error();
      axiosMethod.getMethod = jest.fn().mockReturnValue(() => new Promise((resolve, reject) => reject(expectedError)));

      expect(httpClient.call(method.GET, "testUrl")).rejects.toEqual(expectedError);
      expect(axiosMethod.getMethod).toHaveBeenCalled();
    })
  })
});
