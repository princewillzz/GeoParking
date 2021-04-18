import {
	Divider,
	IconButton,
	InputBase,
	makeStyles,
	Paper,
} from "@material-ui/core";
import { RestoreOutlined, SearchOutlined } from "@material-ui/icons";
import React, { useState } from "react";
import BookSlotModal from "../book-slot-modal/BookSlotModal";
import ParkingCard from "../parking-card/ParkingCard";
import "./SearchParking.css";

import { fetchParkingsWithAddress } from "../../api/parking-public-api";

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

// const parkingCardContainerStyle = makeStyles((theme) => ({
// 	root: {
// 		flexGrow: 1,
// 		maxWidth: 400,
// 	},
// 	paper: {
// 		padding: theme.spacing(2),
// 		textAlign: "center",
// 		color: theme.palette.text.secondary,
// 	},
// }));

export default function SearchParking({ featuredParkings }) {
	const searchBarClasses = searchBarStyle();
	// const parkingCardContainerClasses = parkingCardContainerStyle();

	// Selected parking's id for modal
	const [selectedParkingIdForModal, setSelectedParkingIdForModal] = useState(
		null
	);

	// styling state values
	const [isSearchElevated, setIsSearchElevated] = useState(false);

	// State values
	const [searchParkingString, setSearchParkingString] = useState("");
	const [searchedParkingList, setSearchedParkingList] = useState([]);

	const handleSearchParking = (e) => {
		e.preventDefault();

		fetchParkingsWithAddress(searchParkingString).then((parkingList) => {
			setSearchedParkingList(parkingList);
		});
	};

	const [openBookSlotModal, setOpenBookSlotModal] = useState(false);
	const handleOpenBookSlotModal = (selectedParkingIdForModal) => {
		setSelectedParkingIdForModal(selectedParkingIdForModal);
		setOpenBookSlotModal(true);
	};

	const handleCloseBookSlotModal = () => {
		setOpenBookSlotModal(false);
	};

	return (
		<>
			<h1 style={{ textAlign: "center", fontWeight: 400, marginTop: 50 }}>
				Parkings
			</h1>

			<div className="parkingSearchContainer">
				<Paper
					onMouseOver={() => setIsSearchElevated(true)}
					onMouseLeave={() => setIsSearchElevated(false)}
					elevation={isSearchElevated ? 4 : 1}
					onSubmit={handleSearchParking}
					component="form"
					className={searchBarClasses.root}
				>
					<IconButton type="button">
						<RestoreOutlined />
					</IconButton>
					<Divider
						className={searchBarClasses.divider}
						orientation="vertical"
					/>
					<InputBase
						value={searchParkingString}
						onChange={(e) => setSearchParkingString(e.target.value)}
						className={searchBarClasses.input}
						placeholder="Search Parkings"
						inputProps={{ "aria-label": "search parkings" }}
					/>

					<IconButton
						type="submit"
						className={searchBarClasses.iconButton}
						aria-label="search"
					>
						<SearchOutlined />
					</IconButton>
					<Divider
						className={searchBarClasses.divider}
						orientation="vertical"
					/>
				</Paper>
			</div>

			<div className="parkingsFetched">
				{searchedParkingList.map((eachParking) => (
					<ParkingCard
						key={eachParking.id}
						handleOpenBookSlotModal={handleOpenBookSlotModal}
						parkingData={eachParking}
					/>
				))}
			</div>

			<Divider style={{ width: "80%", marginInline: "auto" }} />

			<>
				<h1 style={{ textAlign: "center", fontWeight: 400 }}>
					<u>Featured Parkings</u>
				</h1>

				<div id="featuredParkingsContainer" className="parkingsFetched">
					{featuredParkings.map((parking) => (
						<ParkingCard
							key={parking.id}
							handleOpenBookSlotModal={handleOpenBookSlotModal}
							parkingData={parking}
						/>
					))}
				</div>
			</>

			<hr style={{ width: "80%" }} />

			<BookSlotModal
				selectedParkingId={selectedParkingIdForModal}
				openBookSlotModal={openBookSlotModal}
				handleCloseBookSlotModal={handleCloseBookSlotModal}
			/>
		</>
	);
}
