import {getMethod, method} from "./axiosMethod";

jest.mock('axios');
let mockAxios = require('axios');

describe('#getMethod()', () =>  {
  test('should return axiosMethod', () => {
    expect(getMethod(method.GET, mockAxios)).toEqual(mockAxios.get);
    expect(getMethod(method.POST, mockAxios)).toEqual(mockAxios.post);
    expect(getMethod(method.PUT, mockAxios)).toEqual(mockAxios.put);
    expect(getMethod(method.DELETE, mockAxios)).toEqual(mockAxios.delete);
  });

  test('should throw exception', () => {
    expect(() => getMethod("WrongMethod", mockAxios)).toThrowError(TypeError);
  })
});
