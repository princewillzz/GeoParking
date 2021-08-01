import axios from "axios";
import { ENV, ENVIRONMENTS } from "../config";

let baseURL;

if (ENV === ENVIRONMENTS.PRODUCTION) {
	baseURL = "https://geoparking-gateway.herokuapp.com";
	// baseURL = "https://parking-gateway-server.herokuapp.com";
} else if (ENV === ENVIRONMENTS.DEVELOPMENT) {
	baseURL = "http://localhost:8080";
}

// const baseURL = "https://geoparking-gateway.herokuapp.com"

export const axiosInstance = axios.create({
	// baseURL: "http://localhost:8080",
	baseURL: baseURL,
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
