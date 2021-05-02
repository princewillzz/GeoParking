import { Divider } from "@material-ui/core";
import React from "react";
import ParkingCard from "../parking-card/ParkingCard";
import ParkingSearchBar from "./ParkingSearchBar";
import "./SearchParking.css";

export default function SearchParking({
	featuredParkings,
	searchedParkingList,
	handleSearchParking,
	openBookSlotModal,
	handleOpenBookSlotModal,
	selectedParkingIdForModal,
	handleCloseBookSlotModal,
	children,
}) {
	return (
		<>
			<h1 style={{ textAlign: "center", fontWeight: 400, marginTop: 50 }}>
				Parkings
			</h1>

			<div className="parkingSearchContainer">
				<ParkingSearchBar handleSearchParking={handleSearchParking} />
			</div>

			{/* Render the map here */}
			<>{children}</>

			<div className="parkingsFetched">
				{searchedParkingList.map((eachParking) => (
					<ParkingCard
						key={eachParking.id}
						handleOpenBookSlotModal={handleOpenBookSlotModal}
						parkingData={eachParking}
					/>
				))}
			</div>

			<Divider
				style={{
					width: "80%",
					marginInline: "auto",
					marginTop: 30,
					marginBottom: 30,
				}}
			/>

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
		</>
	);
}
