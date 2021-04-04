import { Button, Grid, makeStyles, TextField } from "@material-ui/core";
import React, { useState } from "react";
import ChangePasswordModal from "./ChangePasswordModal";
import MobileUpdateModal from "./MobileUpdateModal";

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
		// cursor: "pointer",
	},
}));

function MobileInfoEdit() {
	const styles = useStyles();

	const [mobileNumber, setMobileNumber] = useState();

	// fancy stuff
	const [isEditable, setIsEditable] = useState(false);
	const handleClickOnEditBtn = () => {
		setIsEditable((isEditable) => !isEditable);
	};

	// Mobile update modal
	const [isMobileUpdateModalOpen, setIsMobileUpdateModalOpen] = useState(
		false
	);
	const handleMobileUpdateModalClose = () => {
		setIsMobileUpdateModalOpen(false);
	};
	// password chang modal
	const [isPasswordUpdateModalOpen, setIsPasswordUpdateModalOpen] = useState(
		false
	);
	const handlePasswordUpdateModalClose = () => {
		setIsPasswordUpdateModalOpen(false);
	};

	return (
		<div className={styles.root}>
			<p>
				<b className={styles.heading}>Mobile Number</b>
				<Button
					className={styles.editLink}
					onClick={handleClickOnEditBtn}
				>
					Edit
				</Button>
				<Button
					onClick={() => setIsPasswordUpdateModalOpen(true)}
					className={styles.editLink}
				>
					Change Password
				</Button>
			</p>
			<Grid container direction="row">
				<Grid item style={{}} xs={8}>
					<TextField
						disabled={!isEditable}
						onChange={(e) => setMobileNumber(e.target.value)}
						value={mobileNumber}
						label="mobile"
						variant="outlined"
						style={{ width: "90%" }}
					/>
				</Grid>
				<Grid item xs={4}>
					{isEditable && (
						<Button
							style={{ height: "100%", width: "80%" }}
							variant="contained"
							color="primary"
							onClick={() => setIsMobileUpdateModalOpen(true)}
						>
							Save
						</Button>
					)}
				</Grid>
			</Grid>
			{/* <hr style={{ marginTop: 30, width: "80%" }} /> */}
			<MobileUpdateModal
				isMobileUpdateModalOpen={isMobileUpdateModalOpen}
				handleMobileUpdateModalClose={handleMobileUpdateModalClose}
			/>
			<ChangePasswordModal
				isPasswordUpdateModalOpen={isPasswordUpdateModalOpen}
				handlePasswordUpdateModalClose={handlePasswordUpdateModalClose}
			/>
		</div>
	);
}

export default MobileInfoEdit;
