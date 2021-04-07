import {
	Box,
	Collapse,
	IconButton,
	makeStyles,
	Table,
	TableBody,
	TableCell,
	TableHead,
	TableRow,
	Typography,
} from "@material-ui/core";
import { KeyboardArrowDown, KeyboardArrowUp } from "@material-ui/icons";
import React, { useState } from "react";

const useRowStyles = makeStyles((theme) => ({
	root: {
		"& > *": {
			borderBottom: "unset",
		},
	},
}));

function BookingRow() {
	const classes = useRowStyles();

	const [open, setOpen] = useState(false);

	return (
		<>
			<TableRow className={classes.root}>
				<TableCell>
					<IconButton
						aria-label="expand row"
						size="small"
						onClick={() => setOpen(!open)}
					>
						{open ? <KeyboardArrowUp /> : <KeyboardArrowDown />}
					</IconButton>
				</TableCell>
				<TableCell component="th" scope="row">
					Created At
				</TableCell>
				<TableCell align="right">Customer</TableCell>
				<TableCell align="right">un/Paid</TableCell>
				<TableCell align="right">Amount</TableCell>
				<TableCell align="right">Status</TableCell>
			</TableRow>
			<TableRow>
				<TableCell
					style={{ paddingBottom: 0, paddingTop: 0 }}
					colSpan={6}
				>
					<Collapse in={open} timeout="auto" unmountOnExit>
						<Box margin={1}>
							<Typography
								variant="h6"
								gutterBottom
								component="div"
							></Typography>
							<Table size="small" aria-label="purchases">
								<TableHead>
									<TableRow>
										<TableCell>Arrival Date</TableCell>
										<TableCell>Arrival Time</TableCell>
										<TableCell align="right">
											Departure Date
										</TableCell>
										<TableCell align="right">
											Departure Time
										</TableCell>
									</TableRow>
								</TableHead>
								<TableBody>
									<TableRow>
										<TableCell component="th" scope="row">
											Date
										</TableCell>
										<TableCell>time</TableCell>
										<TableCell align="right">
											date
										</TableCell>
										<TableCell align="right">
											time
										</TableCell>
									</TableRow>
								</TableBody>
							</Table>
						</Box>
					</Collapse>
				</TableCell>
			</TableRow>
		</>
	);
}

export default BookingRow;
