import { Container } from "@material-ui/core";
import React, { useState } from "react";
import { useHistory, useLocation } from "react-router";
import { useAuth } from "../authentication/ProvideAuth";

function Login() {
	let auth = useAuth();
	let history = useHistory();

	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");

	let location = useLocation();

	let { from } = location.state || { from: { pathname: "/" } };

	const handleLogin = () => {
		auth.signin(() => {
			history.replace(from);
		});
	};

	return (
		<>
			<Container style={{ marginTop: "10vh" }}>
				<div>
					<input
						onChange={(e) => setUsername(e.target.value)}
						value={username}
						type="text"
					/>
				</div>
				<div>
					<input
						onChange={(e) => setPassword(e.target.value)}
						value={password}
						type="text"
					/>
				</div>
				<button onClick={() => handleLogin()} type="submit">
					login
				</button>
			</Container>
		</>
	);
}

export default Login;
