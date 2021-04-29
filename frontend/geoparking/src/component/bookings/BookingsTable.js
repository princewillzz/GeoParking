import {
	IconButton,
	LinearProgress,
	makeStyles,
	Paper,
	Table,
	TableBody,
	TableCell,
	TableContainer,
	TableHead,
	TableRow,
} from "@material-ui/core";
import { CachedOutlined } from "@material-ui/icons";
import { Alert } from "@material-ui/lab";
import React from "react";
import BookingRow from "./BookingRow";

const useStyles = makeStyles((theme) => ({
	tableContainer: {
		minHeight: "70vh",
	},
}));

function BookingsTable({ bookings, reloadBookings, isLoading }) {
	const classes = useStyles();

	const loadBookingsRows = () => {
		const bookingRows = bookings.map((booking) => (
			<BookingRow key={booking.id} bookingData={booking} />
		));

		return bookingRows;
	};

	return (
		<>
			<TableContainer
				component={Paper}
				className={classes.tableContainer}
			>
				<Table stickyHeader aria-label="collapsible table">
					<TableHead>
						<TableRow>
							<TableCell />
							<TableCell>Created At</TableCell>
							<TableCell align="right">Customer</TableCell>
							<TableCell align="right">Paid(?)</TableCell>
							<TableCell align="right">Amount($)</TableCell>
							<TableCell align="right">Discount($)</TableCell>
							<TableCell align="right">To Pay($)</TableCell>
							<TableCell align="right">Status</TableCell>
						</TableRow>
					</TableHead>

					<TableBody>{loadBookingsRows()}</TableBody>
				</Table>
				{isLoading && <LinearProgress />}

				{(!bookings || bookings.length === 0) && !isLoading && (
					<Alert
						severity="error"
						action={
							<IconButton
								onClick={reloadBookings}
								color="inherit"
								size="small"
							>
								<CachedOutlined />
							</IconButton>
						}
					>
						No Bookings yet
					</Alert>
				)}
			</TableContainer>
		</>
	);
}

export default BookingsTable;
