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

function AdminParkingCard({ parkingData }) {
	console.log(parkingData);

	const classes = useStyles();

	const [isRaised, setIsRaised] = useState(false);

	const [isEditable, setIsEditable] = useState(false);

	const [parkingEditInfo, setParkingEditInfo] = useState({
		parkingName: parkingData.name,
		parkingLocation: parkingData.address,
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

		setIsEditable(false);
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
							{parkingEditInfo.parkingName}
						</Typography>
						<Typography
							variant="body2"
							color="textSecondary"
							component="p"
						>
							{parkingEditInfo.parkingLocation}
						</Typography>
					</>
				) : (
					<>
						<form onSubmit={handleParkingUpdate}>
							<TextField
								name="parkingName"
								value={parkingEditInfo.parkingName}
								onChange={handleChangeParkingData}
								label="Parking Name"
								aria-label="parking name "
								aria-labelledby="Parking name"
								fullWidth
							/>
							<TextField
								name="parkingLocation"
								value={parkingEditInfo.parkingLocation}
								onChange={handleChangeParkingData}
								multiline
								fullWidth
								label="Address"
								aria-label="parking location "
								aria-labelledby="location"
							/>
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
							to="/admin/bookings?parking-id=123213"
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
					<Button variant="outlined" color="secondary">
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
