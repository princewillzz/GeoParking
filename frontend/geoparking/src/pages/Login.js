import {
	Button,
	Card,
	CardActions,
	CardContent,
	CardHeader,
	CircularProgress,
	Container,
	makeStyles,
	Snackbar,
} from "@material-ui/core";
import { Alert } from "@material-ui/lab";
import React, { useState } from "react";
import LoginBody from "../component/login/LoginBody";
import RegisterBody from "../component/login/RegisterBody";

const useStyles = makeStyles((theme) => ({
	root: {
		width: "min(600px, 90vw)",
	},
	loginRegisterSwitchBtn: {
		textTransform: "none",
		color: "blueviolet",
	},
	loginContainer: {
		marginTop: "10vh",
	},
}));

function Login() {
	const classes = useStyles();

	const [openLoginForm, setOpenLoginForm] = useState(true);
	const [openRegsiterForm, setOpenRegisterForm] = useState(false);
	const [isLoading, setIsLoading] = useState(false);
	const [openSnackBar, setSnackBar] = useState(false);

	const handleChangeLoading = (status) => {
		setIsLoading(status);
	};

	const handleLoginRegisterFormChange = () => {
		setOpenLoginForm((openLoginForm) => !openLoginForm);
		setOpenRegisterForm((openRegsiterForm) => !openRegsiterForm);
	};

	const getCardTitle = () => {
		if (openLoginForm && !openRegsiterForm) {
			return "Login";
		} else if (openRegsiterForm && !openLoginForm) {
			return "Register";
		} else {
			return "Forgot Password";
		}
	};

	const getBody = () => {
		if (openLoginForm && !openRegsiterForm) {
			return (
				<LoginBody
					setSnackBar={setSnackBar}
					handleChangeLoading={handleChangeLoading}
				/>
			);
		} else if (openRegsiterForm && !openLoginForm) {
			return (
				<RegisterBody
					setSnackBar={setSnackBar}
					handleChangeLoading={handleChangeLoading}
				/>
			);
		} else {
			return "Forgot Password";
		}
	};

	const dimContentWhileLoading = () => {
		return isLoading
			? {
					backgroundColor: "rgba(0, 0, 0, 0.1)",
					pointerEvents: "none",
			  }
			: {};
	};

	return (
		<>
			<center>
				<Snackbar
					open={openSnackBar}
					anchorOrigin={{ vertical: "top", horizontal: "center" }}
					autoHideDuration={6000}
					onClose={() => setSnackBar(false)}
				>
					<Alert onClose={() => setSnackBar(false)} severity="error">
						Unable to Sign In/Up
					</Alert>
				</Snackbar>
				<Container className={classes.loginContainer}>
					<Card
						style={dimContentWhileLoading()}
						className={classes.root}
					>
						<CardHeader title={getCardTitle()} />

						{/* Main content of the card */}
						<CardContent
							style={{
								width: "min(400px, 80vw)",
								// width: "80vw",
								// maxWidth: "400px",
							}}
						>
							{isLoading && (
								<CircularProgress
									style={{ position: "absolute", top: "40%" }}
								/>
							)}
							{getBody()}
						</CardContent>

						{/* Footer */}
						<CardActions
							style={{
								justifyContent: "center",
								alignItems: "center",
							}}
						>
							{openLoginForm && !openRegsiterForm && (
								<>
									<p>
										<small>Dont have an account yet?</small>
									</p>
									<Button
										onClick={() =>
											handleLoginRegisterFormChange()
										}
										className={
											classes.loginRegisterSwitchBtn
										}
										style={{
											margin: 0,
										}}
									>
										Register
									</Button>
								</>
							)}
							{openRegsiterForm && !openLoginForm && (
								<>
									<p>
										<small>Already have an account?</small>
									</p>
									<Button
										onClick={() =>
											handleLoginRegisterFormChange()
										}
										className={
											classes.loginRegisterSwitchBtn
										}
										style={{
											margin: 0,
										}}
									>
										Login
									</Button>
								</>
							)}
						</CardActions>
					</Card>
				</Container>
			</center>
		</>
	);
}

export default Login;
