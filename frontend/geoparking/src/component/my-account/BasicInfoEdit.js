import {
	Button,
	Checkbox,
	Divider,
	FormControlLabel,
	FormGroup,
	Grid,
	makeStyles,
	TextField,
} from "@material-ui/core";
import React, { useState } from "react";

const useStyles = makeStyles((theme) => ({
	root: {
		paddingTop: 30,
		paddingInline: 10,
	},
}));

function BasicInfoEdit() {
	const style = useStyles();

	const [firstName, setFirstName] = useState();
	const [lastName, setLastName] = useState();

	const [genderCheckBox, setGenderCheckbox] = useState({
		male: false,
		female: false,
		other: false,
	});

	return (
		<>
			<FormGroup className={style.root}>
				{/* <p style={{ fontSize: "1.5rem" }}>Basic Info</p> */}
				<Grid
					style={{ paddingInline: 10 }}
					container
					direction="row"
					justify="space-around"
					alignItems="center"
					spacing={4}
				>
					<Grid item xs={6}>
						<TextField
							label="firstname"
							value={firstName}
							onChange={(e) => setFirstName(e.target.value)}
							variant="outlined"
							fullWidth={true}
						/>
					</Grid>
					<Grid item xs={6}>
						<TextField
							label="lastname"
							value={lastName}
							onChange={(e) => setLastName(e.target.value)}
							variant="outlined"
							fullWidth={true}
						/>
					</Grid>
				</Grid>
				<Grid
					container
					direction="row"
					style={{ marginTop: 10, paddingTop: 10, paddingLeft: 20 }}
				>
					<Grid>
						<FormControlLabel
							control={
								<Checkbox
									checked={genderCheckBox.male}
									onClick={() =>
										setGenderCheckbox({
											male: true,
											female: false,
											other: false,
										})
									}
								/>
							}
							label="male"
						/>
						<FormControlLabel
							control={
								<Checkbox
									checked={genderCheckBox.female}
									onClick={() =>
										setGenderCheckbox({
											male: false,
											female: true,
											other: false,
										})
									}
								/>
							}
							label="female"
						/>
						<FormControlLabel
							control={
								<Checkbox
									checked={genderCheckBox.other}
									onClick={() =>
										setGenderCheckbox({
											male: false,
											female: false,
											other: true,
										})
									}
								/>
							}
							label="other"
						/>
					</Grid>
					<Grid></Grid>
					<Grid style={{ marginLeft: "auto", paddingRight: 30 }}>
						<Button color="primary" variant="contained">
							Save
						</Button>
					</Grid>
				</Grid>
			</FormGroup>
			<Divider
				style={{ marginInline: "auto", marginTop: 40, width: "80%" }}
			/>
			{/* <hr style={{ marginTop: 40, width: "80%" }} /> */}
		</>
	);
}

export default BasicInfoEdit;
