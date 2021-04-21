import {
	Button,
	Checkbox,
	Divider,
	FormControlLabel,
	Grid,
	makeStyles,
	TextField,
} from "@material-ui/core";
import React, { useEffect, useState } from "react";
import { updateBasicProfileInfo } from "../../api/profile-api";

const useStyles = makeStyles((theme) => ({
	root: {
		paddingTop: 30,
		paddingInline: 10,
	},
}));

function BasicProfileInfoEdit({ profile }) {
	const style = useStyles();

	const [basicProfileInfo, setBasicProfileInfo] = useState({
		firstname: "",
		lastname: "",
		gender: null,
	});

	useEffect(() => {
		setBasicProfileInfo({
			firstname: profile.firstName || "",
			lastname: profile.lastName || "",
			gender: profile.gender,
		});
	}, [profile]);

	console.log(profile, basicProfileInfo);

	const handleChange = (e) => {
		setBasicProfileInfo({
			...basicProfileInfo,
			[e.target.name]: e.target.value,
		});
	};

	const handleFormSubmit = (e) => {
		e.preventDefault();

		updateBasicProfileInfo(basicProfileInfo).then((profile) =>
			console.log(profile)
		);
	};

	return (
		<>
			<form onSubmit={handleFormSubmit} className={style.root}>
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
							name="firstname"
							value={basicProfileInfo.firstname}
							onChange={handleChange}
							variant="outlined"
							aria-label="first name"
							aria-labelledby="first name of user"
							fullWidth
						/>
					</Grid>
					<Grid item xs={6}>
						<TextField
							label="lastname"
							name="lastname"
							value={basicProfileInfo.lastname}
							onChange={handleChange}
							aria-label="last name"
							aria-labelledby="last name of user"
							variant="outlined"
							fullWidth
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
									checked={
										basicProfileInfo.gender
											? basicProfileInfo.gender === "male"
											: false
									}
									name="gender"
									value="male"
									onClick={handleChange}
								/>
							}
							label="male"
						/>
						<FormControlLabel
							control={
								<Checkbox
									checked={
										basicProfileInfo.gender
											? basicProfileInfo.gender ===
											  "female"
											: false
									}
									name="gender"
									value="female"
									onClick={handleChange}
								/>
							}
							label="female"
						/>
						<FormControlLabel
							control={
								<Checkbox
									checked={
										basicProfileInfo.gender
											? basicProfileInfo.gender ===
											  "other"
											: false
									}
									name="gender"
									value="other"
									onClick={handleChange}
								/>
							}
							label="other"
						/>
					</Grid>
					<Grid></Grid>
					<Grid style={{ marginLeft: "auto", paddingRight: 30 }}>
						<Button
							type="submit"
							color="primary"
							variant="contained"
						>
							Save
						</Button>
					</Grid>
				</Grid>
			</form>
			<Divider
				style={{ marginInline: "auto", marginTop: 40, width: "80%" }}
			/>
			{/* <hr style={{ marginTop: 40, width: "80%" }} /> */}
		</>
	);
}

export default BasicProfileInfoEdit;
