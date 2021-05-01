import React, { useCallback, useEffect, useState } from "react";
import {
	fetchFeaturedParkings,
	fetchParkingsWithAddress,
} from "../api/parking-public-api";
import BookSlotModal from "../component/book-slot-modal/BookSlotModal";
import MapBoxMap from "../component/map/MapBoxMap";
import SearchParking from "../component/search-parking/SearchParking";

function Home() {
	const [featuredParkings, setFeaturedParkings] = useState([]);

	const [searchedParkingList, setSearchedParkingList] = useState([]);

	const [parkingsForMap, setParkingForMap] = useState([]);

	// Selected parking's id for modal
	const [selectedParkingIdForModal, setSelectedParkingIdForModal] = useState(
		null
	);

	const [openBookSlotModal, setOpenBookSlotModal] = useState(false);
	const handleOpenBookSlotModal = useCallback((selectedParkingIdForModal) => {
		setSelectedParkingIdForModal(selectedParkingIdForModal);
		setOpenBookSlotModal(true);
	}, []);

	const handleCloseBookSlotModal = () => {
		setOpenBookSlotModal(false);
	};

	const handleSearchParking = (searchParkingString) => {
		fetchParkingsWithAddress(searchParkingString).then((parkingList) => {
			setSearchedParkingList(parkingList);
			setParkingForMap(parkingList);
		});
	};

	useEffect(() => {
		setTimeout(() => {
			fetchFeaturedParkings().then((featuredParkingList) => {
				setFeaturedParkings(featuredParkingList);
				setParkingForMap(featuredParkingList);
			});
		}, 0);
	}, []);

	return (
		<>
			<SearchParking
				featuredParkings={featuredParkings}
				handleSearchParking={handleSearchParking}
				searchedParkingList={searchedParkingList}
				openBookSlotModal={openBookSlotModal}
				handleOpenBookSlotModal={handleOpenBookSlotModal}
				selectedParkingIdForModal={selectedParkingIdForModal}
				handleCloseBookSlotModal={handleCloseBookSlotModal}
			>
				<MapBoxMap
					parkings={parkingsForMap}
					handleOpenBookSlotModal={handleOpenBookSlotModal}
				/>
			</SearchParking>

			<BookSlotModal
				selectedParkingId={selectedParkingIdForModal}
				openBookSlotModal={openBookSlotModal}
				handleCloseBookSlotModal={handleCloseBookSlotModal}
			/>
		</>
	);
}

export default Home;
