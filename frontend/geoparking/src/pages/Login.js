import {
	Button,
	Card,
	CardActions,
	CardContent,
	CardHeader,
	Container,
	makeStyles,
} from "@material-ui/core";
import React, { useState } from "react";
import LoginBody from "../component/login/LoginBody";
import RegisterBody from "../component/login/RegisterBody";

const useStyles = makeStyles((theme) => ({
	root: {
		width: "min(600px, 80vw)",
	},
	loginRegisterSwitchBtn: {
		textTransform: "none",
		color: "blueviolet",
	},
}));

function Login() {
	const classes = useStyles();

	const [openLoginForm, setOpenLoginForm] = useState(true);
	const [openRegsiterForm, setOpenRegisterForm] = useState(false);

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
			return <LoginBody />;
		} else if (openRegsiterForm && !openLoginForm) {
			return <RegisterBody />;
		} else {
			return "Forgot Password";
		}
	};

	return (
		<>
			<center>
				<Container style={{ marginTop: "10vh" }}>
					<Card className={classes.root}>
						<CardHeader title={getCardTitle()} />

						{/* Main content of the card */}
						<CardContent
							style={{
								width: "min(400px, 80vw)",
							}}
						>
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
