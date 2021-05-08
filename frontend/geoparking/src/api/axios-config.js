import axios from "axios";

export const axiosInstance = axios.create({
	// baseURL: "http://localhost:8080",
	baseURL: "https://geoparking-gateway.herokuapp.com",
	timeout: 30000,
});

axiosInstance.interceptors.request.use(
	function (config) {
		let jwtToken = localStorage.getItem("token");
		if (jwtToken) {
			config.headers["Authorization"] = "Bearer " + jwtToken;
		}
		return config;
	},
	function (err) {
		return Promise.reject(err);
	}
);
