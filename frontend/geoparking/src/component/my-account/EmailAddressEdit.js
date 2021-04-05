import {
	Button,
	Divider,
	Grid,
	makeStyles,
	TextField,
} from "@material-ui/core";
import React, { useState } from "react";
import EmailUpdateModal from "./EmailUpdateModal";

const useStyles = makeStyles((theme) => ({
	root: {
		padding: 10,
		margin: 10,
		marginLeft: 20,
	},
	heading: {
		fontWeight: 500,
		fontSize: "1.5rem",
	},
	editLink: {
		textDecoration: "none",
		textTransform: "none",
		color: "blue",
		cursor: "pointer",
	},
}));

function EmailAddressEdit() {
	const styles = useStyles();

	const [isEditable, setIsEditable] = useState(false);

	const [email, setEmail] = useState("prince@gmail.com");
	const [isEmailUpdateModalOpen, setIsEmailUpdateModalOpen] = useState(false);

	const handleClickOnEditBtn = () => {
		setIsEditable((isEditable) => !isEditable);
	};

	const handleEmailUpdateModalClose = () => {
		setIsEmailUpdateModalOpen(false);
	};

	return (
		<div className={styles.root}>
			<p>
				<b className={styles.heading}>Email Address</b>
				<Button
					className={styles.editLink}
					onClick={handleClickOnEditBtn}
				>
					Edit
				</Button>
			</p>
			<Grid container direction="row">
				<Grid item style={{}} xs={8}>
					<TextField
						disabled={!isEditable}
						onChange={(e) => setEmail(e.target.value)}
						value={email}
						label="email"
						variant="outlined"
						style={{ width: "90%" }}
					/>
				</Grid>
				<Grid item xs={4}>
					{isEditable && (
						<Button
							onClick={() => setIsEmailUpdateModalOpen(true)}
							style={{ height: "100%", width: "80%" }}
							variant="contained"
							color="primary"
						>
							Save
						</Button>
					)}
				</Grid>
			</Grid>
			<Divider
				style={{ marginInline: "auto", marginTop: 40, width: "80%" }}
			/>
			<EmailUpdateModal
				isEmailUpdateModalOpen={isEmailUpdateModalOpen}
				handleEmailUpdateModalClose={handleEmailUpdateModalClose}
			/>
		</div>
	);
}

export default EmailAddressEdit;
