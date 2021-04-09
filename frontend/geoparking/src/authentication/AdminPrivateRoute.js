import React from "react";
import { Redirect, Route } from "react-router";
import { useAuth } from "./ProvideAuth";

function AdminPrivateRoute({ children }) {
	let auth = useAuth();

	return (
		<Route
			render={({ location }) =>
				auth.verifyAdminLogged() ? (
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

export default AdminPrivateRoute;
