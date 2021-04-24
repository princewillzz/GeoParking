import { useEffect, useState } from "react";
import { checkAuth, signinUser } from "../api/auth-api";

function useProvideAuth() {
	const [username, setUsername] = useState(null);

	const [isUserLoggedIn, setIsUserLoggedIn] = useState(false);
	const [isAdminLoggedIn, setIsAdminLoggedIn] = useState(false);

	const _initializeAuthState = () => {
		const { username, role } = checkAuth();

		if (role === "USER" || role === "ADMIN") {
			setUsername(username);

			if (role === "USER") setIsUserLoggedIn(true);
			else if (role === "ADMIN") setIsAdminLoggedIn(true);
		}
	};

	useEffect(() => {
		try {
			_initializeAuthState();
		} catch (error) {}
	}, []);

	const signin = async (credentials, cb) => {
		return signinUser(credentials, () => {
			_initializeAuthState();

			cb();
		});
	};

	const signout = (cb) => {
		localStorage.removeItem("token");
		setTimeout(() => {
			setUsername(null);

			setIsAdminLoggedIn(false);
			setIsUserLoggedIn(false);
			cb();
		}, 0);
	};

	const verifyUserLogged = () => {
		try {
			const { role } = checkAuth();
			if (role === "USER") return true;
		} catch (e) {}

		return false;
	};

	const verifyAdminLogged = () => {
		try {
			const { role } = checkAuth();
			if (role === "ADMIN") return true;
		} catch (error) {}

		return false;
	};

	return {
		username,
		isAdminLoggedIn,
		verifyUserLogged,
		isUserLoggedIn,
		verifyAdminLogged,
		signin,
		signout,
	};
}

export default useProvideAuth;
