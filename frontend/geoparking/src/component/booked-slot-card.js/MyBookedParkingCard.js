import {
	Box,
	Button,
	Card,
	CardContent,
	Divider,
	makeStyles,
	Typography,
} from "@material-ui/core";
import React, { useEffect, useState } from "react";
import { axiosInstance } from "../../api/axios-config";
import mapThumb from "../../assets/map_thumb.png";

const useStyles = makeStyles((theme) => ({
	root: {
		display: "flex",
		width: "auto",
		maxWidth: "30rem",

		// justifyContent: "center",
		// padding: "5px",
		// margin: "5px",
		// width: "350px",
		// height: "auto",
		// textAlign: "center",
		[theme.breakpoints.down("xs")]: {
			width: "90vw",
		},
	},

	cover: {
		width: 200,
		[theme.breakpoints.down("xs")]: {
			width: "35%",
		},
	},
	details: {
		display: "flex",
		flexDirection: "column",
	},
	content: {
		flex: "1 0 auto",
		textAlign: "center",
	},
	divider: {
		marginTop: 5,
		marginBottom: 5,
	},
}));

function MyBookedParkingCard({ bookingData, handleCancelBooking }) {
	const classes = useStyles();

	const [parking, setParking] = useState({
		id: "",
		name: "",
		address: "",
		hourlyRent: 0,
		active: false,
	});

	const [isRaised, setIsRaised] = useState(false);

	useEffect(() => {
		const fetchParkingInfo = async () => {
			axiosInstance
				.get("/api/parking-service/parking/" + bookingData.parkingId)
				.then((response) => {
					setParking(response.data);
					// console.log(response.data);
				})
				.catch((error) => {
					console.log(error.response.status);
				});
		};

		setTimeout(() => {
			fetchParkingInfo();
		}, 0);
	}, [bookingData.parkingId]);

	return (
		<Card
			className={classes.root}
			onMouseOver={() => setIsRaised(true)}
			onMouseLeave={() => setIsRaised(false)}
			raised={isRaised}
		>
			<img className={classes.cover} src="/p1.jpg" alt="imag" />
			<div className={classes.details}>
				<CardContent className={classes.content}>
					<Typography component="h5" variant="h5">
						{parking.name.length > 10
							? parking.name.substr(0, 15)
							: parking.name}
					</Typography>
					<Typography variant="subtitle1" color="textSecondary">
						Asansol
					</Typography>
					<Typography>{parking.address}</Typography>

					<Box
						display={"flex"}
						alignItems={"center"}
						justifyContent={"center"}
						marginTop={2}
					>
						<Button
							style={{ marginInline: 10 }}
							variant="contained"
							color="primary"
						>
							Receipt
						</Button>
						<img
							style={{
								height: 50,
								width: 35,
							}}
							src={mapThumb}
							alt="mapThumb"
						/>
					</Box>
					<Divider className={classes.divider} />
					<Box display="flex" justifyContent="space-between">
						<Box>
							<p>
								{new Date(
									bookingData.arrivalTimeDate
								).toDateString()}
							</p>
							<p>
								{new Date(
									bookingData.departureTimeDate
								).toDateString()}
							</p>
						</Box>
						<Box>
							<p>
								~
								{new Date(
									bookingData.arrivalTimeDate
								).toLocaleString("en-US", {
									hour: "numeric",
									minute: "numeric",
									hour12: true,
								})}
							</p>
							<p>
								~
								{new Date(
									bookingData.departureTimeDate
								).toLocaleString("en-US", {
									hour: "numeric",
									minute: "numeric",
									hour12: true,
								})}
							</p>
						</Box>
					</Box>

					{bookingData.status &&
						bookingData.status.toUpperCase() !== "CANCELLED" &&
						bookingData.status.toUpperCase() !== "COMPLETED" && (
							<>
								<Divider className={classes.divider} />
								<Button
									onClick={() =>
										handleCancelBooking(bookingData.id)
									}
									style={{ margin: 0 }}
									fullWidth
									variant="outlined"
									color="secondary"
								>
									Cancel
								</Button>
							</>
						)}
				</CardContent>
			</div>
		</Card>
	);
}

export default MyBookedParkingCard;
