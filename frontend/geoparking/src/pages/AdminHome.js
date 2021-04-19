import { Button, Divider, makeStyles } from "@material-ui/core";
import React, { useEffect, useState } from "react";
import { fetchAdminParkings } from "../api/parking-admin-api";
import AddParkingModal from "../component/add-parking/AddParkingModal";
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
	heading: {
		textAlign: "center",
		fontWeight: 500,
	},
	divider: {
		width: "90%",
		margin: "auto",
	},
}));

function AdminHome() {
	const classes = useStyles();

	const [isAddParkingModalOpen, setIsAddParkingModalOpen] = useState(false);
	const [myParkings, setMyParkings] = useState([]);

	useEffect(() => {
		fetchAdminParkings();
	}, [myParkings]);

	const handleCloseAddParkingModal = () => {
		setIsAddParkingModalOpen(false);
	};

	return (
		<>
			<h1 className={classes.heading}>Your Parkings</h1>
			<Divider className={classes.divider} />
			<Button
				style={{ marginLeft: 35 }}
				color="primary"
				variant="contained"
				onClick={() => setIsAddParkingModalOpen(true)}
			>
				New Parking
			</Button>
			<main className={classes.root}>
				<AdminParkingCard />
				<AdminParkingCard />
				<AdminParkingCard />
				<AdminParkingCard />
			</main>
			<AddParkingModal
				isAddParkingModalOpen={isAddParkingModalOpen}
				handleCloseAddParkingModal={handleCloseAddParkingModal}
			/>
		</>
	);
}

export default AdminHome;
