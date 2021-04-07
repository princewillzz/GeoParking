import { makeStyles } from "@material-ui/core";
import React from "react";
import AdminParkingCard from "../component/parking-card/AdminParkingCard";

const useStyles = makeStyles((theme) => ({
	root: {
		marginTop: "5rem",
		marginBottom: "5rem",
		display: "grid",
		justifyContent: "center",
		gridTemplateColumns: "repeat(auto-fit, minmax(30rem, 1fr))",
		placeItems: "center",
		gap: 20,
	},
}));

function AdminHome() {
	const classes = useStyles();

	return (
		<>
			<main className={classes.root}>
				<AdminParkingCard />
				<AdminParkingCard />
				<AdminParkingCard />
				<AdminParkingCard />
			</main>
		</>
	);
}

export default AdminHome;
