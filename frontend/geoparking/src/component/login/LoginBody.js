import { Button, Grid, makeStyles, TextField } from "@material-ui/core";
import React, { useState } from "react";
import { useHistory, useLocation } from "react-router";
import { useAuth } from "../../authentication/ProvideAuth";

const useStyles = makeStyles((theme) => ({
	root: {
		width: "min(600px, 80vw)",
	},
	inputContainer: {},
	forgotPasswordLink: {
		display: "flex",
		justifyContent: "flex-end",
		marginTop: 10,
		fontSize: 15,
		"&>*": {
			textDecoration: "none",
			textTransform: "none",
			color: "blueViolet",
		},
	},
	input: {},
}));

function LoginBody() {
	const classes = useStyles();

	let auth = useAuth();
	let history = useHistory();

	let location = useLocation();

	let { from } = location.state || { from: { pathname: "/" } };

	const [credentials, setCredentials] = useState({
		username: "",
		password: "",
	});

	const handleCredentialsChange = (event) => {
		setCredentials({
			...credentials,
			[event.target.name]: event.target.value,
		});
	};

	const handleLogin = (event) => {
		event.preventDefault();

		try {
			auth.signin(credentials, () => {
				history.replace(from);
			});
		} catch (error) {
			alert("login failed...!");
		}
	};

	return (
		<form onSubmit={handleLogin}>
			<Grid>
				<Grid
					item
					className={classes.inputContainer}
					style={{ marginTop: 5 }}
				>
					<TextField
						variant="outlined"
						name="username"
						onChange={handleCredentialsChange}
						value={credentials.username}
						className={classes.input}
						label="Email"
						fullWidth
					/>
				</Grid>

				<Grid
					item
					className={classes.inputContainer}
					style={{ marginTop: 30 }}
				>
					<TextField
						variant="outlined"
						name="password"
						onChange={handleCredentialsChange}
						value={credentials.password}
						className={classes.input}
						label="Password"
						fullWidth
					/>
				</Grid>
				<Grid className={classes.forgotPasswordLink}>
					<Button
						onClick={(e) => {
							e.preventDefault();
							console.log("hi");
						}}
					>
						Forgot Password?
					</Button>
				</Grid>

				<Grid
					item
					className={classes.inputContainer}
					style={{ marginTop: 30 }}
				>
					<Button
						type="submit"
						variant="contained"
						color="primary"
						fullWidth
						className={classes.input}
					>
						login
					</Button>
				</Grid>
			</Grid>
		</form>
	);
}

export default LoginBody;
