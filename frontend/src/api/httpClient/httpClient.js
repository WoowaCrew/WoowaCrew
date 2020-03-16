import {getMethod} from "./axios/axiosMethod";
import {AxiosSupplier} from "./axios/axiosSupplier";


export class HttpClient {
  axiosInstance;

  constructor(container) {
    let axiosSupplier = container.get(AxiosSupplier);
    this.axiosInstance = axiosSupplier.get();
  }

  async call(method, url, config) {
    const axiosMethod = getMethod(method, this.axiosInstance);
    return await axiosMethod(url, config);
  }
}
