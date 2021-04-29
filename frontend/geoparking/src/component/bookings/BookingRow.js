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
import {
	Cancel,
	CheckCircle,
	KeyboardArrowDown,
	KeyboardArrowUp,
} from "@material-ui/icons";
import React, { useEffect, useState } from "react";
import { fetchCustomerInfoForBooking } from "../../api/admin-booking-api";

const useRowStyles = makeStyles((theme) => ({
	root: {
		"& > *": {
			borderBottom: "unset",
		},
	},
	bookingTotalAmount: {
		color: "red",
		textDecoration: "line-through grey",
	},
	bookingDiscount: {
		color: "blue",
	},
	bookingAmountToPay: {
		color: "green",
	},
	bookingStatus: {},
}));

function BookingRow({ bookingData }) {
	const classes = useRowStyles();

	const [openBookingSubTable, setOpenBookingSubTable] = useState(false);
	const [openCustomerInfoSubTable, setCustomerInfoSubTable] = useState(false);

	return (
		<>
			<TableRow className={classes.root}>
				<TableCell>
					<IconButton
						aria-label="expand row"
						size="small"
						onClick={() =>
							setOpenBookingSubTable(!openBookingSubTable)
						}
					>
						{openBookingSubTable ? (
							<KeyboardArrowUp />
						) : (
							<KeyboardArrowDown />
						)}
					</IconButton>
				</TableCell>
				<TableCell
					component="th"
					scope="row"
					className="bookingCreatedAt"
				>
					{new Date(bookingData.createdAt).toLocaleString()}
				</TableCell>
				<TableCell align="right">
					<IconButton
						aria-label="expand row"
						size="small"
						onClick={() =>
							setCustomerInfoSubTable(!openCustomerInfoSubTable)
						}
					>
						{openCustomerInfoSubTable ? (
							<KeyboardArrowUp />
						) : (
							<KeyboardArrowDown />
						)}
					</IconButton>
				</TableCell>
				<TableCell align="right" className="bookingPaymentStatus">
					{bookingData.paymentDone ? (
						<CheckCircle style={{ color: "green" }} />
					) : (
						<Cancel color={"secondary"} />
					)}
				</TableCell>
				<TableCell align="right" className={classes.bookingTotalAmount}>
					{bookingData.bill.totalAmount}
				</TableCell>
				<TableCell align="right" className={classes.bookingDiscount}>
					-{bookingData.bill.discount}
				</TableCell>
				<TableCell align="right" className={classes.bookingAmountToPay}>
					{bookingData.bill.amountToPay}
				</TableCell>
				<TableCell align="right" className={classes.bookingStatus}>
					{bookingData.status}
				</TableCell>
			</TableRow>

			<TableRow>
				<TableCell
					style={{
						paddingBottom: 0,
						paddingTop: 0,
						borderBottom: "none",
					}}
					colSpan={8}
				>
					{/* Booking info sub table */}
					<BookingInfoSubTable
						open={openBookingSubTable}
						bookingData={bookingData}
					/>
				</TableCell>
			</TableRow>
			<TableRow>
				<TableCell
					style={{ paddingBottom: 0, paddingTop: 0 }}
					colSpan={8}
				>
					{/* Customer info Sub table */}
					<CustomerInfoSubTable
						open={openCustomerInfoSubTable}
						customerId={bookingData.customerId}
						bookingId={bookingData.id}
					/>
				</TableCell>
			</TableRow>
		</>
	);
}

function CustomerInfoSubTable({ open, customerId, bookingId }) {
	const [customer, setCustomer] = useState({
		name: "",
		contact: "",
		email: "",
	});

	const [isCustomerInfoLoaded, setIsCustomerInfoLoaded] = useState(false);

	useEffect(() => {
		if (open && !isCustomerInfoLoaded) {
			fetchCustomerInfoForBooking(bookingId, customerId)
				.then((data) => {
					setIsCustomerInfoLoaded(true);
					console.log(data);

					setCustomer({
						name:
							data.firstName +
							(data.lastName ? data.lastName : ""),
						contact: data.mobile,
						email: data.email,
					});
				})
				.catch((error) => alert("Unable to load customer info...!"));
		}
	}, [open, isCustomerInfoLoaded, bookingId, customerId]);

	return (
		<Collapse in={open} timeout="auto" unmountOnExit>
			<Box margin={1}>
				<Typography
					variant="h6"
					gutterBottom
					component="div"
				></Typography>
				<Table size="small" aria-label="purchases">
					<TableHead
						style={{
							backgroundColor: "rgba(255, 222, 222)",
						}}
					>
						<TableRow>
							<TableCell>Name</TableCell>
							<TableCell align="right">Contact</TableCell>
							<TableCell align="right">Email</TableCell>
						</TableRow>
					</TableHead>
					<TableBody>
						<TableRow>
							<TableCell component="th" scope="row">
								{customer.name}
							</TableCell>
							<TableCell align="right">
								{customer.contact}
							</TableCell>
							<TableCell align="right">
								{customer.email}
							</TableCell>
						</TableRow>
					</TableBody>
				</Table>
			</Box>
		</Collapse>
	);
}

function BookingInfoSubTable({ open, bookingData }) {
	return (
		<Collapse in={open} timeout="auto" unmountOnExit>
			<Box margin={1}>
				<Typography
					variant="h6"
					gutterBottom
					component="div"
				></Typography>
				<Table size="small" aria-label="purchases">
					<TableHead
						style={{
							backgroundColor: "rgba(255, 255, 205)",
						}}
					>
						<TableRow>
							<TableCell>Arrival Date</TableCell>
							<TableCell>Arrival Time</TableCell>
							<TableCell align="right">Departure Date</TableCell>
							<TableCell align="right">Departure Time</TableCell>
						</TableRow>
					</TableHead>
					<TableBody>
						<TableRow>
							<TableCell component="th" scope="row">
								{new Date(
									bookingData.arrivalTimeDate
								).toDateString()}
							</TableCell>
							<TableCell>
								{new Date(
									bookingData.arrivalTimeDate
								).toLocaleString("en-US", {
									hour: "numeric",
									minute: "numeric",
									hour12: true,
								})}
							</TableCell>
							<TableCell align="right">
								{new Date(
									bookingData.departureTimeDate
								).toDateString()}
							</TableCell>
							<TableCell align="right">
								{new Date(
									bookingData.departureTimeDate
								).toLocaleString("en-US", {
									hour: "numeric",
									minute: "numeric",
									hour12: true,
								})}
							</TableCell>
						</TableRow>
					</TableBody>
				</Table>
			</Box>
		</Collapse>
	);
}

export default BookingRow;
