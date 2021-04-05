import { Divider, makeStyles } from "@material-ui/core";
import React from "react";
import MyBookedParkingCard from "../component/booked-slot-card.js/MyBookedParkingCard";

const useStyles = makeStyles((theme) => ({
	root: {
		marginTop: 50,
	},
	parkingCardContainer: {
		padding: 10,
		margin: 10,
		display: "grid",
		justifyContent: "center",
		gap: 10,
		gridTemplateColumns: "repeat(auto-fit, minmax(30rem, 1fr))",
		// grid-template-columns: ;
		placeItems: "center",
	},
}));

function MyBookings() {
	const classes = useStyles();

	return (
		<>
			<main className={classes.root}>
				<h2 style={{ textAlign: "center", fontWeight: 500 }}>
					Ongoing bookings
				</h2>
				<div className={classes.parkingCardContainer}>
					<MyBookedParkingCard />
					<MyBookedParkingCard />
					<MyBookedParkingCard />
					<MyBookedParkingCard />
				</div>
				<Divider />
				<h2 style={{ textAlign: "center", fontWeight: 500 }}>
					Past bookings
				</h2>
				<div className={classes.parkingCardContainer}>
					<MyBookedParkingCard />
					<MyBookedParkingCard />
					<MyBookedParkingCard />
					<MyBookedParkingCard />
				</div>
			</main>
		</>
	);
}

export default MyBookings;
