import {
	Button,
	FormControlLabel,
	Grid,
	makeStyles,
	Switch,
	TextField,
} from "@material-ui/core";
import React, { useState } from "react";
import { useHistory } from "react-router";
import { registerProfile } from "../../api/profile-api";
import { useAuth } from "../../authentication/ProvideAuth";

const useStyles = makeStyles((theme) => ({
	inputContainer: {},
	forgotPasswordLink: {
		display: "flex",
		justifyContent: "flex-end",
		textDecoration: "none",
		marginTop: 10,
		fontSize: 15,
		color: "blueViolet",
	},
	input: {},
}));

const initialState = {
	firstname: "",
	lastname: "",
	email: "",
	mobile: "",
	password: "",
	role: "user",
};

function RegisterBody() {
	const classes = useStyles();

	const history = useHistory();
	const auth = useAuth();

	const [formInputState, setFormInputState] = useState(initialState);
	const [adminChecked, setAdminChecked] = useState(false);

	const handleSubmit = (event) => {
		event.preventDefault();

		let replaceURLTo = "/";
		if (adminChecked) {
			replaceURLTo = "/admin";
		}

		const callback = () => {
			auth.signin(
				{
					username: formInputState.email,
					password: formInputState.password,
				},
				() => {
					history.replace(replaceURLTo);
				}
			);
		};

		registerProfile(formInputState, callback).catch(() => {
			console.log("unable ");
		});
	};

	// change role change
	const handleRoleInputChange = () => {
		if (!adminChecked) {
			setFormInputState({
				...formInputState,
				role: "admin",
			});
		} else {
			setFormInputState({
				...formInputState,
				role: "user",
			});
		}
		setAdminChecked(!adminChecked);
	};

	// handle form state change
	const handleFormChange = (event) => {
		setFormInputState({
			...formInputState,
			[event.target.name]: event.target.value,
		});
	};

	return (
		<form onSubmit={handleSubmit}>
			<Grid>
				<Grid
					item
					style={{
						display: "flex",
						justifyContent: "space-between",
						marginTop: 5,
					}}
				>
					<TextField
						name="firstname"
						value={formInputState.firstname}
						onChange={handleFormChange}
						label="First Name"
						style={{ width: "48%" }}
						variant="outlined"
					/>
					<TextField
						name="lastname"
						value={formInputState.lastname}
						onChange={handleFormChange}
						label="Last Name"
						style={{ width: "48%" }}
						variant="outlined"
					/>
				</Grid>
				<Grid item style={{ marginTop: 30 }}>
					<TextField
						name="email"
						value={formInputState.email}
						onChange={handleFormChange}
						variant="outlined"
						className={classes.input}
						label="Email"
						fullWidth
					/>
				</Grid>

				<Grid item style={{ marginTop: 30 }}>
					<TextField
						name="mobile"
						value={formInputState.mobile}
						onChange={handleFormChange}
						variant="outlined"
						className={classes.input}
						label="Mobile NUmber"
						fullWidth
					/>
				</Grid>

				<Grid item style={{ marginTop: 30 }}>
					<TextField
						name="password"
						value={formInputState.password}
						onChange={handleFormChange}
						variant="outlined"
						className={classes.input}
						label="Password"
						fullWidth
					/>
				</Grid>

				<Grid item>
					<FormControlLabel
						control={
							<Switch
								checked={adminChecked}
								name="role"
								onChange={handleRoleInputChange}
							/>
						}
						label="Rent Parkings"
						style={{ marginTop: 10 }}
					/>
				</Grid>

				<Grid item style={{ marginTop: 30 }}>
					<Button
						// onClick={() => handleLogin()}
						variant="contained"
						color="primary"
						fullWidth
						className={classes.input}
						type="submit"
					>
						Register
					</Button>
				</Grid>
			</Grid>
		</form>
	);
}

export default RegisterBody;
