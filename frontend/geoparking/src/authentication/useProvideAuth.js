import React, { useEffect, useState } from "react";
import { checkUserAuth } from "../api/check-user-auth";

const fakeAuth = {
	// isAuthenticated: false,
	signin(cb) {
		// fakeAuth.isAuthenticated = true;
		localStorage.setItem("token", "tokeeke");
		setTimeout(cb, 0); // fake async
	},
	signout(cb) {
		// fakeAuth.isAuthenticated = false;
		localStorage.removeItem("token");
		setTimeout(cb, 0);
	},
};

function useProvideAuth() {
	const [username, setUsername] = useState(null);

	const [isUserLoggedIn, setIsUserLoggedIn] = useState(false);
	const [isAdminLoggedIn, setIsAdminLoggedIn] = useState(false);

	useEffect(() => {
		const { username, role } = checkUserAuth();

		if (role) setUsername(username);

		if (role === "USER") setIsUserLoggedIn(true);
		else if (role === "ADMIN") setIsAdminLoggedIn(true);
	}, [username]);

	const signin = (cb) => {
		return fakeAuth.signin(() => {
			setUsername("harsh");
			cb();
		});
	};

	const signout = (cb) => {
		return fakeAuth.signout(() => {
			setUsername(null);
			setIsAdminLoggedIn(false);
			setIsUserLoggedIn(false);
			cb();
		});
	};

	return {
		username,
		isAdminLoggedIn,
		isUserLoggedIn,
		signin,
		signout,
	};
}

export default useProvideAuth;
