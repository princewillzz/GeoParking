import {
	makeStyles,
	Paper,
	Table,
	TableBody,
	TableCell,
	TableContainer,
	TableHead,
	TableRow,
} from "@material-ui/core";
import React from "react";
import BookingRow from "./BookingRow";

const useStyles = makeStyles((theme) => ({
	tableContainer: {
		maxHeight: "94vh",
	},
}));

function BookingsTable() {
	const classes = useStyles();

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
							<TableCell align="right">un/Paid</TableCell>
							<TableCell align="right">Amount ($)</TableCell>
							<TableCell align="right">Status</TableCell>
						</TableRow>
					</TableHead>
					<TableBody>
						<BookingRow />
						<BookingRow />
						<BookingRow />
						<BookingRow />
						<BookingRow />
						<BookingRow />
						<BookingRow />
						<BookingRow />
						<BookingRow />
						<BookingRow />
						<BookingRow />
						<BookingRow />
						<BookingRow />
						<BookingRow />
						<BookingRow />
					</TableBody>
				</Table>
			</TableContainer>
		</>
	);
}

export default BookingsTable;
