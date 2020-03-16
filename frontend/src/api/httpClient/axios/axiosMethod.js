export const method = {
  GET: "GET",
  POST: "POST",
  PUT: "PUT",
  DELETE: "DELETE",
};

const preparedMethods = new Map();
preparedMethods.set(method.GET, (axiosInstance) => axiosInstance.get);
preparedMethods.set(method.POST, (axiosInstance) => axiosInstance.post);
preparedMethods.set(method.PUT, (axiosInstance) => axiosInstance.put);
preparedMethods.set(method.DELETE, (axiosInstance) => axiosInstance.delete);


export function getMethod(method, axiosInstance) {
  return preparedMethods.get(method)(axiosInstance);
}
