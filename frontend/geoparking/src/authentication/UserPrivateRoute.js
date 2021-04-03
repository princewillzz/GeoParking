import React from "react";
import { Redirect, Route } from "react-router";

import { checkUserAuth } from "../api/check-user-auth";

function UserPrivateRoute({ children }) {
	return (
		<Route
			render={({ location }) =>
				checkUserAuth() ? (
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
