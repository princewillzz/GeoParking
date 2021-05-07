import {
	CircularProgress,
	makeStyles,
	Paper,
	TablePagination,
	Typography,
} from "@material-ui/core";
import React, { useCallback, useEffect, useState } from "react";
import { useHistory, useLocation } from "react-router";
import { fetchBookingsOfParking } from "../api/admin-booking-api";
import BookingsTable from "../component/bookings/BookingsTable";

const useStyles = makeStyles((theme) => ({
	root: {
		width: "100%",
		marginTop: 30,
	},
	heading: {
		textAlign: "center",
		fontWeight: 500,
		fontSize: 40,
		marginTop: 30,
	},
}));

function AdminBookings() {
	const classes = useStyles();

	const history = useHistory();

	const parkingId = new URLSearchParams(useLocation().search).get(
		"parking-id"
	);

	const [bookings, setBookings] = useState([]);
	const [parking, setParking] = useState();

	const [isLoading, setIsLoading] = useState(true);

	const loadBookings = useCallback(() => {
		setIsLoading(true);
		fetchBookingsOfParking(parkingId)
			.then((data) => {
				setBookings(data.bookings);

				setTimeout(() => {
					setParking(data.parking);
					setIsLoading(false);
				}, 500);
			})
			.catch(() => alert("unable to load..."));
	}, [parkingId]);

	useEffect(() => {
		setTimeout(loadBookings, 0);
	}, [parkingId, loadBookings]);

	const [page, setPage] = React.useState(0);
	const [rowsPerPage, setRowsPerPage] = React.useState(10);

	const handleChangePage = (event, newPage) => {
		setPage(newPage);
	};

	const handleChangeRowsPerPage = (event) => {
		setRowsPerPage(+event.target.value);
		setPage(0);
	};

	if (!parkingId) {
		history.push("/admin");
	}

	return (
		<>
			<Typography className={classes.heading} component="div">
				{parking && parking.name ? parking.name : <CircularProgress />}
			</Typography>
			<Paper className={classes.root}>
				<BookingsTable
					isLoading={isLoading}
					reloadBookings={loadBookings}
					bookings={bookings}
					className={classes.table}
				/>
				<TablePagination
					rowsPerPageOptions={[10, 25, 100]}
					component="div"
					count={100000}
					rowsPerPage={rowsPerPage}
					page={page}
					onChangePage={handleChangePage}
					onChangeRowsPerPage={handleChangeRowsPerPage}
				/>
			</Paper>
		</>
	);
}

export default AdminBookings;
