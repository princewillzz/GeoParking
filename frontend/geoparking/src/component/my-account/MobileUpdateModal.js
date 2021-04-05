import {
	Backdrop,
	Button,
	Fade,
	FormControl,
	FormGroup,
	makeStyles,
	Modal,
	TextField,
} from "@material-ui/core";
import React from "react";

const useStyles = makeStyles((theme) => ({
	paper: {
		position: "absolute",
		minWidth: 320,
		width: "auto",
		backgroundColor: theme.palette.background.paper,
		boxShadow: theme.shadows[5],
		padding: theme.spacing(2, 4, 3),
		top: "50%",
		left: "50%",
		transform: `translate(-50%, -50%)`,
		outline: 0,
		borderRadius: "0.3rem",
	},
	modalHeader: {
		display: "flex",
		alignItems: "center",
		justifyContent: "space-between",
	},
	title: {
		fontSize: 18,
		fontWeight: 500,
	},
	resendOTP: {
		textTransform: "none",
		color: "blue",
	},
}));

function MobileUpdateModal({
	isMobileUpdateModalOpen,
	handleMobileUpdateModalClose,
}) {
	const classes = useStyles();

	return (
		<Modal
			open={isMobileUpdateModalOpen}
			onClose={handleMobileUpdateModalClose}
			aria-labelledby="email-update-modal"
			aria-describedby="modal-for-email-update"
			closeAfterTransition
			BackdropComponent={Backdrop}
			BackdropProps={{
				timeout: 500,
			}}
		>
			<Fade in={isMobileUpdateModalOpen}>
				<div className={classes.paper}>
					{/* <Cancel /> */}
					<div className={classes.modalHeader}>
						<div className={classes.title}>
							<p>Verify OTP</p>
						</div>
						<div>
							<Button className={classes.resendOTP}>
								Resend OTP
							</Button>
						</div>
					</div>

					<FormGroup style={{ marginTop: 10 }}>
						<FormControl style={{ marginBottom: 15 }}>
							<TextField
								label={"Enter OTP sent to mobile"}
								variant="outlined"
								fullWidth
								autoComplete={"none"}
							/>
						</FormControl>
						<FormControl style={{ marginBottom: 15 }}>
							<TextField
								label={"Enter OTP sent to mobile"}
								variant="outlined"
								fullWidth
								autoComplete={"none"}
							/>
						</FormControl>
						<FormControl style={{ marginBottom: 15 }}>
							<TextField
								label={"Enter password"}
								variant="outlined"
								fullWidth
								type={"password"}
							/>
						</FormControl>
						<FormControl>
							<Button variant="contained" color="primary">
								Submit
							</Button>
						</FormControl>
					</FormGroup>
				</div>
			</Fade>
		</Modal>
	);
}

export default MobileUpdateModal;
