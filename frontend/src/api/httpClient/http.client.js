import {AxiosSupplier} from "./axios/axios.supplier";
import {getMethod} from "./axios/axios.method";


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
