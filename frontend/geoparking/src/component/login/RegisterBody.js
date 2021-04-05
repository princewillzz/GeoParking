import { Button, Grid, makeStyles, TextField } from "@material-ui/core";
import React, { useState } from "react";

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
};

function RegisterBody() {
	const classes = useStyles();

	const [formInputState, setFormInputState] = useState(initialState);

	const handleSubmit = (event) => {
		event.preventDefault();
		console.log(formInputState);
	};

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
