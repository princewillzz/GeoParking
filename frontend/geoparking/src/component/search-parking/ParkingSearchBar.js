import {
	Divider,
	IconButton,
	InputBase,
	makeStyles,
	Paper,
} from "@material-ui/core";
import { RestoreOutlined, SearchOutlined } from "@material-ui/icons";
import React, { useState } from "react";

const searchBarStyle = makeStyles((theme) => ({
	root: {
		padding: "2px 4px",
		display: "flex",
		alignItems: "center",
		width: "min(400px, 80vw)",
	},
	input: {
		marginLeft: theme.spacing(1),
		flex: 1,
		// "&.Mui-focused": {		},
	},
	iconButton: {
		padding: 10,
	},
	divider: {
		height: 28,
		margin: 4,
	},
}));

function ParkingSearchBar({ handleSearchParking }) {
	const classes = searchBarStyle();

	const [isSearchElevated, setIsSearchElevated] = useState(false);

	const [searchParkingString, setSearchParkingString] = useState("");

	const handleSearchParkingFormSubmit = (e) => {
		e.preventDefault();

		handleSearchParking(searchParkingString);
		// fetchParkingsWithAddress(searchParkingString).then((parkingList) => {
		// 	setSearchedParkingList(parkingList);
		// });
	};

	return (
		<Paper
			onMouseOver={() => setIsSearchElevated(true)}
			onMouseLeave={() => setIsSearchElevated(false)}
			elevation={isSearchElevated ? 4 : 1}
			onSubmit={handleSearchParkingFormSubmit}
			component="form"
			className={classes.root}
		>
			<IconButton type="button">
				<RestoreOutlined />
			</IconButton>
			<Divider className={classes.divider} orientation="vertical" />
			<InputBase
				value={searchParkingString}
				onChange={(e) => setSearchParkingString(e.target.value)}
				className={classes.input}
				placeholder="Search Parkings"
				inputProps={{ "aria-label": "search parkings" }}
			/>

			<IconButton
				type="submit"
				className={classes.iconButton}
				aria-label="search"
			>
				<SearchOutlined />
			</IconButton>
			<Divider className={classes.divider} orientation="vertical" />
		</Paper>
	);
}

export default ParkingSearchBar;
