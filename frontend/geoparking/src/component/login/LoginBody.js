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

const testUserCred = {
	username: "harsh",
	password: "harsh",
};

const testAdminCred = {
	username: "testadmin",
	password: "testadmin",
};

function LoginBody() {
	const classes = useStyles();

	let auth = useAuth();
	let history = useHistory();

	let location = useLocation();

	let { from } = location.state || { from: { pathname: "/" } };

	const [credentials, setCredentials] = useState({
		username: "harsh",
		password: "harsh",
	});

	const handleCredentialsChange = (event) => {
		setCredentials({
			...credentials,
			[event.target.name]: event.target.value,
		});
	};

	const handleLogin = async (event) => {
		event.preventDefault();

		try {
			await auth.signin(credentials, () => {
				history.replace(from);
			});
		} catch (error) {
			alert("login failed...!");
		}
	};

	const [isTestCredUser, setIsTestCredUser] = useState(true);
	const handleTestCredChange = () => {
		if (isTestCredUser) {
			setCredentials(testAdminCred);
		} else {
			setCredentials(testUserCred);
		}
		setIsTestCredUser(!isTestCredUser);

		console.log(credentials);
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
				<Grid
					// To be removed later
					onClick={handleTestCredChange}
					// to be removed
					className={classes.forgotPasswordLink}
				>
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
