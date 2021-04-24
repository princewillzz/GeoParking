import jwtDecode from "jwt-decode";
import { axiosInstance } from "./axios-config";

export const signinUser = async (credentials, cb) => {
	// const csrfToken = cookies.get("XSRF-TOKEN");

	return axiosInstance.post("/auth/authenticate", credentials).then((res) => {
		const token = res.data.jwt;

		localStorage.setItem("token", token);

		cb();
	});
};

export const checkAuth = () => {
	// console.log(localStorage.getItem("token"));

	try {
		const decoded = jwtDecode(localStorage.getItem("token"));

		if (decoded.exp > new Date().getTime() / 1000) {
			return {
				username: decoded.username,
				role: decoded.authorities[0].split("_")[1],
			};
		}
	} catch (error) {}

	return false;
};
