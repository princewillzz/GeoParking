import {
	makeStyles,
	Paper,
	TablePagination,
	Typography,
} from "@material-ui/core";
import React, { useEffect } from "react";
import { useHistory, useLocation } from "react-router";
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

	useEffect(() => {
		if (!parkingId) {
			history.push("/admin");
		}
	});

	console.log(useLocation());
	console.log(parkingId);

	const [page, setPage] = React.useState(0);
	const [rowsPerPage, setRowsPerPage] = React.useState(10);

	const handleChangePage = (event, newPage) => {
		setPage(newPage);
	};

	const handleChangeRowsPerPage = (event) => {
		setRowsPerPage(+event.target.value);
		setPage(0);
	};

	return (
		<>
			<Typography className={classes.heading} component="p">
				Parking Name
			</Typography>

			<Paper className={classes.root}>
				<BookingsTable className={classes.table} />
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
