import axios from "axios";

export class AxiosSupplier {
  axiosInstance;
  constructor() {
    this.axiosInstance = axios.create({
      baseURL: window.location.origin
    });
  }

  get() {
    return this.axiosInstance;
  }
}
