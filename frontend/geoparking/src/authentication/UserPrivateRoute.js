import React from "react";
import { Redirect, Route } from "react-router";
import { useAuth } from "./ProvideAuth";

function UserPrivateRoute({ children }) {
	const auth = useAuth();

	return (
		<Route
			render={({ location }) =>
				auth.verifyUserLogged() ? (
					children
				) : (
					<Redirect
						to={{
							pathname: "/login",
							state: { from: location },
						}}
					/>
				)
			}
		/>
	);
}

export default UserPrivateRoute;
