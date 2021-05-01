import {
	Backdrop,
	Button,
	Fade,
	FormControl,
	FormGroup,
	IconButton,
	InputAdornment,
	InputLabel,
	makeStyles,
	Modal,
	OutlinedInput,
	TextField,
} from "@material-ui/core";
import { Close, PhotoCamera } from "@material-ui/icons";
import React, { useCallback, useState } from "react";
import { createParking } from "../../api/parking-admin-api";
import AdminInputMap from "../map/AdminInputMap";

const useStyles = makeStyles((theme) => ({
	paper: {
		position: "absolute",
		minWidth: 450,
		width: "auto",
		backgroundColor: theme.palette.background.paper,
		boxShadow: theme.shadows[5],
		padding: theme.spacing(2, 4, 3),
		top: "50%",
		left: "50%",
		transform: `translate(-50%, -50%)`,
		outline: 0,
		borderRadius: "0.3rem",
		// [theme.breakpoints.up())],
		[theme.breakpoints.down("xs")]: {
			minWidth: "90vw",
		},
	},
	modalHeader: {
		display: "flex",
		alignItems: "center",
		justifyContent: "space-between",
		borderBottom: "1px solid #dee2e6",
	},
	title: {
		fontSize: 22,
		fontWeight: 500,
	},
	map: {
		height: 200,
		width: "100%",
	},
}));

const initialState = {
	name: "",
	address: "",
	hourlyRent: 0,
};

function AddParkingModal({
	isAddParkingModalOpen,
	handleCloseAddParkingModal,
	handleNewParkingAdded,
}) {
	const classes = useStyles();

	const [parkingData, setParkingData] = useState(initialState);
	const [lat, setLat] = useState(0);
	const [lng, setLng] = useState(0);

	const handleFormDataChange = (e) => {
		setParkingData({ ...parkingData, [e.target.name]: e.target.value });
	};

	const handleFormSubmit = (e) => {
		e.preventDefault();

		console.log({ ...parkingData, latitude: lat, longitude: lng });

		createParking({
			...parkingData,
			position: { latitude: lat, longitude: lng },
		})
			.then((parking) => {
				handleNewParkingAdded(parking);
				setParkingData(initialState);
				handleCloseAddParkingModal();
			})
			.catch((error) => console.log(error));
	};

	const handleLocationChange = useCallback((location) => {
		setLng(location.longitude);
		setLat(location.latitude);
	}, []);

	return (
		<>
			<Modal
				open={isAddParkingModalOpen}
				onClose={handleCloseAddParkingModal}
				aria-labelledby="password-update-modal"
				aria-describedby="modal-for-password-update"
				closeAfterTransition
				BackdropComponent={Backdrop}
				BackdropProps={{
					timeout: 500,
				}}
			>
				<Fade in={isAddParkingModalOpen}>
					<form className={classes.paper} onSubmit={handleFormSubmit}>
						<div className={classes.modalHeader}>
							<div className={classes.title}>
								<p>Add Parking</p>
							</div>

							<Close
								onClick={handleCloseAddParkingModal}
								style={{ cursor: "pointer" }}
							/>
						</div>

						<FormGroup style={{ marginTop: 10 }}>
							<FormControl style={{ marginBottom: 15 }}>
								<TextField
									label={"Parking Name"}
									variant="outlined"
									fullWidth
									name={"name"}
									value={parkingData.name}
									onChange={handleFormDataChange}
								/>
							</FormControl>
							<FormControl style={{ marginBottom: 15 }}>
								<TextField
									label={"Parking Address"}
									variant="outlined"
									fullWidth
									multiline
									name={"address"}
									value={parkingData.address}
									onChange={handleFormDataChange}
								/>
							</FormControl>

							<FormControl
								style={{ marginBottom: 15 }}
								fullWidth
								variant="outlined"
							>
								<InputLabel htmlFor="outline-parking-Amount">
									Hourly Cost
								</InputLabel>
								<OutlinedInput
									id="outline-parking-Amount"
									startAdornment={
										<InputAdornment position="start">
											$
										</InputAdornment>
									}
									labelWidth={90}
									type="number"
									name={"hourlyRent"}
									value={parkingData.hourlyRent}
									onChange={handleFormDataChange}
								/>
							</FormControl>

							<FormControl
								style={{
									marginBottom: 15,
									alignSelf: "center",
								}}
							>
								<IconButton>
									<PhotoCamera />
								</IconButton>
							</FormControl>

							<FormControl
								style={{
									marginBottom: 15,
									alignSelf: "center",
								}}
								fullWidth
							>
								<AdminInputMap
									lat={lat}
									lng={lng}
									setLat={setLat}
									setLng={setLng}
									handleLocationChange={handleLocationChange}
								/>
							</FormControl>

							<FormControl>
								<Button
									variant="contained"
									color="primary"
									type="submit"
								>
									Add Parking
								</Button>
							</FormControl>
						</FormGroup>
					</form>
				</Fade>
			</Modal>
		</>
	);
}

export default AddParkingModal;
