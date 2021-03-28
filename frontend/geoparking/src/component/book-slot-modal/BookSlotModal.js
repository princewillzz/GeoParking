import {
	Box,
	Button,
	Container,
	makeStyles,
	Modal,
	TextField,
} from "@material-ui/core";
import { CheckCircleOutlineRounded, Close } from "@material-ui/icons";
import React from "react";

const useStyles = makeStyles((theme) => ({
	modal: {
		display: "flex",
		alignItems: "center",
		justifyContent: "center",
	},
	paper: {
		position: "relative",
		display: "flex",
		flexDirection: "column",
		width: "auto",
		backgroundColor: theme.palette.background.paper,
		boxShadow: theme.shadows[5],
		borderRadius: "0.3rem",
		outline: 0,
	},

	modalHeader: {
		padding: "1rem 1rem",
		display: "flex",
		justifyContent: "space-between",
		alignItems: "center",
		borderBottom: "1px solid #dee2e6",
		borderTopLeftRadius: "calc(.3rem - 1px)",
		borderTopRightRadius: "calc(.3 - 1px)",
		backgroundColor: "rgba(0, 0, 0, 0.03)",
		borderTop: "1px solid rgba(0, 0,0,  0.125)",
	},
	modalTitle: {
		fontSize: "1.25rem",
		lineHeight: 1.5,
		marginTop: "0.5rem",
		marginBottom: 0,
		fontWeight: 500,
		display: "flex",
		alignItems: "center",
	},
	modalContent: {
		display: "block",
		padding: 15,
		marginRight: "auto",
		marginLeft: "auto",
	},
	modalFooter: {
		display: "flex",
		justifyContent: "flex-end",
		alignItems: "center",
		padding: "1rem 1rem",
		backgroundColor: "rgba(0, 0, 0, 0.03)",
		borderTop: "1px solid rgba(0, 0, 0, 0.125)",
	},

	modalSuccessBtn: {
		color: "#fff",
		textTransform: "none",
		fontSize: 15,
		marginInline: 5,
		backgroundColor: "#28a745",
		"&:hover": {
			backgroundColor: "green",
		},
	},
	modalPrimarybtn: {
		color: "#fff",
		textTransform: "none",
		fontSize: 15,
		marginInline: 5,
		backgroundColor: "#007bff",
		"&:hover": {
			backgroundColor: "primary",
		},
	},
	prependInputBtn: {
		height: 55,
		disabled: true,
		borderRadius: "3px 3px 0px",
	},
}));

function BookSlotModal({ openBookSlotModal, handleCloseBookSlotModal }) {
	const classes = useStyles();

	return (
		<Modal
			className={classes.modal}
			open={openBookSlotModal}
			onClose={handleCloseBookSlotModal}
			aria-labelledby="book-slot-modal-title"
			aria-describedby="book-slot-modal-description"
			closeAfterTransition
		>
			<div className={classes.paper}>
				<div className={classes.modalHeader} id="book-slot-modal-title">
					<h5 className={classes.modalTitle}>
						Check Availability
						<CheckCircleOutlineRounded
							color="primary"
							style={{ color: "green", marginInline: 5 }}
						/>
					</h5>
					<Close
						onClick={handleCloseBookSlotModal}
						style={{ cursor: "pointer" }}
					/>
				</div>
				<div
					className={classes.modalContent}
					id="book-slot-modal-description"
				>
					<Container fixed>
						<Box my={2}>
							<Button
								className={classes.prependInputBtn}
								disableElevation
								variant="contained"
								disabled
							>
								<span
									style={{
										color: "black",
									}}
								>
									Arrive
								</span>
							</Button>
							<TextField type="date" variant="outlined" />
							<TextField type="time" variant="outlined" />
						</Box>
						<Box my={2}>
							<Button
								className={classes.prependInputBtn}
								disableElevation
								variant="contained"
								disabled
							>
								<span
									style={{
										color: "black",
									}}
								>
									Depart
								</span>
							</Button>
							<TextField type="date" variant="outlined" />
							<TextField type="time" variant="outlined" />
						</Box>
					</Container>
				</div>
				<div className={classes.modalFooter}>
					<Button
						className={classes.modalSuccessBtn}
						color="primary"
						variant="contained"
					>
						Pay to Book
					</Button>
					<Button
						className={classes.modalPrimarybtn}
						color="primary"
						variant="contained"
					>
						Check Availability
					</Button>
				</div>
			</div>
		</Modal>
	);
}

export default BookSlotModal;