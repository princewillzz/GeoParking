import {
	Box,
	Button,
	Card,
	CardContent,
	CardMedia,
	Divider,
	IconButton,
	makeStyles,
	TextField,
	Typography,
} from "@material-ui/core";
import { LocationOn } from "@material-ui/icons";
import React, { useState } from "react";
import { Link } from "react-router-dom";
import { deleteParking, updateParking } from "../../api/parking-admin-api";

const useStyles = makeStyles((theme) => ({
	root: {
		maxWidth: 400,
		minWidth: 400,
		width: "auto",
		[theme.breakpoints.down("xs")]: {
			width: "90vw",
		},
	},
	heading: {
		textAlign: "center",
	},
	carasoul: {
		height: 160,
	},
	divider: {
		marginTop: 20,
		marginBottom: 5,
	},
	cardBodyContainer: {},
	bottom: {
		display: "flex",
		justifyContent: "center",
		alignItems: "center",
	},
}));

function AdminParkingCard({ parkingData, handleRemoveParkingCard }) {
	const classes = useStyles();

	const [isRaised, setIsRaised] = useState(false);

	const [isEditable, setIsEditable] = useState(false);

	const [parkingEditInfo, setParkingEditInfo] = useState({
		id: parkingData.id,
		name: parkingData.name,
		address: parkingData.address,
		hourlyRent: parkingData.hourlyRent,
		totalSlots: parkingData.totalSlots,
	});

	const handleChangeParkingData = (e) => {
		setParkingEditInfo({
			...parkingEditInfo,
			[e.target.name]: e.target.value,
		});
	};

	const handleParkingUpdate = (e) => {
		e.preventDefault();
		console.log(parkingEditInfo);
		updateParking(parkingEditInfo).then(() => setIsEditable(false));
	};

	const handleDeleteParking = () => {
		console.log(parkingData.id);
		deleteParking(parkingData.id).then(() => {
			handleRemoveParkingCard(parkingData.id);
		});
	};

	return (
		<Card
			raised={isRaised}
			onMouseOver={() => setIsRaised(true)}
			onMouseOut={() => setIsRaised(false)}
			className={classes.root}
		>
			{/* <CardHeader /> */}
			<CardMedia
				className={classes.carasoul}
				image="/p1.jpg"
				title="parking photos"
			/>

			<CardContent className={classes.cardBodyContainer}>
				{!isEditable ? (
					<>
						<Typography
							gutterBottom
							variant="h5"
							component="h2"
							className={classes.heading}
						>
							{parkingEditInfo.name}
						</Typography>
						<Typography
							variant="body2"
							color="textSecondary"
							component="p"
						>
							{parkingEditInfo.address}
						</Typography>
						<Typography
							style={{
								textAlign: "center",
								marginTop: 10,
							}}
							gutterBottom
						>
							Rs {parkingEditInfo.hourlyRent} /- per hour
						</Typography>
						<Typography
							style={{
								textAlign: "center",
								marginTop: 10,
							}}
							gutterBottom
						>
							Total Slots {"=>"} {parkingEditInfo.totalSlots}
						</Typography>
					</>
				) : (
					<>
						<form onSubmit={handleParkingUpdate}>
							<TextField
								name="name"
								value={parkingEditInfo.name}
								onChange={handleChangeParkingData}
								label="Parking Name"
								aria-label="parking name"
								aria-labelledby="Parking name"
								fullWidth
								type="text"
							/>
							<TextField
								name="address"
								value={parkingEditInfo.address}
								onChange={handleChangeParkingData}
								multiline
								fullWidth
								label="Address"
								aria-label="parking location"
								aria-labelledby="location"
							/>
							<TextField
								name="hourlyRent"
								value={parkingEditInfo.hourlyRent}
								onChange={handleChangeParkingData}
								label="Parking Hourly Rent"
								aria-label="Parking hourly Rent"
								aria-labelledby="Parking rent per hour"
								fullWidth
								type="number"
							/>
							<TextField
								name="totalSlots"
								value={parkingEditInfo.totalSlots}
								onChange={handleChangeParkingData}
								label="Parking Slots"
								aria-label="Parking slots"
								aria-labelledby="Parking slots"
								fullWidth
								type="number"
							/>
							<button type="submit" hidden>
								submit
							</button>
						</form>
					</>
				)}

				<Divider className={classes.divider} />
				<Typography
					variant="body2"
					color="textSecondary"
					component="center"
				>
					<p>
						Times Booked: <b>{parkingData.timeBooked}</b>
						<Link
							to={`/admin/bookings?parking-id=${parkingData.id}`}
							style={{ textDecoration: "none" }}
						>
							<Button
								variant="outlined"
								style={{
									marginLeft: 10,
									color: "green",
								}}
							>
								bookings
							</Button>
						</Link>
					</p>
				</Typography>
				<Divider className={classes.divider} />
				<Box className={classes.bottom}>
					{!isEditable ? (
						<Button
							onClick={() => setIsEditable(true)}
							variant="outlined"
							style={{ color: "green", marginRight: 10 }}
						>
							Edit
						</Button>
					) : (
						<Button
							onClick={handleParkingUpdate}
							variant="outlined"
							style={{ color: "blue", marginRight: 10 }}
						>
							Update
						</Button>
					)}
					<Button
						onClick={handleDeleteParking}
						variant="outlined"
						color="secondary"
					>
						Delete
					</Button>
					<IconButton style={{ margin: 0, padding: 0 }}>
						<LocationOn style={{ fontSize: 50 }} />
					</IconButton>
				</Box>
			</CardContent>
		</Card>
	);
}

export default AdminParkingCard;
