import axios from "axios";
import jwtDecode from "jwt-decode";

export const signinUser = (credentials, cb) => {
	axios
		.post("http://localhost:8080/auth/authenticate", credentials)
		.then((res) => {
			const token = res.data.jwt;

			localStorage.setItem("token", token);
			console.log(token);

			cb();
		});
};

export const checkAuth = () => {
	const token = localStorage.getItem("token");
	console.log(token);

	try {
		const decoded = jwtDecode(token);

		console.log(decoded);

		if (decoded.exp > new Date().getTime() / 1000) {
			return {
				username: decoded.sub,
				role: decoded.authorities[0].split("_")[1],
			};
		}
	} catch (error) {}

	return false;
};
