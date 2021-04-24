import { Divider, makeStyles } from "@material-ui/core";
import { Alert } from "@material-ui/lab";
import React, { useEffect, useState } from "react";
import { fetchMyBookings } from "../api/userBookingAndPayment";
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

	const [bookingList, setBookingList] = useState([]);

	useEffect(() => {
		fetchMyBookings()
			.then(({ status, data }) => {
				if (status === 200) {
					setBookingList(data.bookings);
				} else if (status === 503) {
					alert("service unavailable... Retry!");
				} else {
					alert("something went wrong");
				}
			})
			.catch(() => alert("error"));
	}, []);

	const loadOngoingBookings = () => {
		const bookingComponentList = bookingList
			.filter((booking) => booking.status.toUpperCase() !== "COMPLETED")
			.map((booking) => (
				<MyBookedParkingCard key={booking.id} bookingData={booking} />
			));

		if (bookingComponentList.length > 0) {
			return bookingComponentList;
		}

		return (
			<Alert style={{ minWidth: "60%" }} severity="info">
				No Bookings yet!
			</Alert>
		);
	};

	const loadPastBookings = () => {
		const bookingComponentList = bookingList
			.filter((booking) => booking.status.toUpperCase() === "COMPLETED")
			.map((booking) => (
				<MyBookedParkingCard key={booking.id} bookingData={booking} />
			));

		if (bookingComponentList.length > 0) {
			return bookingComponentList;
		}

		return (
			<Alert style={{ minWidth: "60%" }} severity="info">
				No Past Bookings yet!
			</Alert>
		);
	};

	return (
		<>
			<main className={classes.root}>
				<h2 style={{ textAlign: "center", fontWeight: 500 }}>
					Ongoing bookings
				</h2>
				<div className={classes.parkingCardContainer}>
					{loadOngoingBookings()}
				</div>
				<Divider />
				<h2 style={{ textAlign: "center", fontWeight: 500 }}>
					Past bookings
				</h2>
				<div className={classes.parkingCardContainer}>
					{loadPastBookings()}
				</div>
			</main>
		</>
	);
}

export default MyBookings;
