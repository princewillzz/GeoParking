import React, { useCallback, useEffect, useState } from "react";
import { axiosInstance } from "../api/axios-config";
import { fetchFeaturedParkings } from "../api/parking-public-api";
import BookSlotModal from "../component/book-slot-modal/BookSlotModal";
import MapBoxMap from "../component/map/MapBoxMap";
import SearchParking from "../component/search-parking/SearchParking";

function Home() {
	const [featuredParkings, setFeaturedParkings] = useState([]);

	const [searchedParkingList, setSearchedParkingList] = useState([]);

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
		fetchNearbyParking();
		// fetchParkingsWithAddress(searchParkingString).then((parkingList) => {
		// 	setSearchedParkingList(parkingList);
		// 	setParkingForMap(parkingList);
		// });
	};

	useEffect(() => {
		setTimeout(() => {
			fetchFeaturedParkings().then((featuredParkingList) => {
				setFeaturedParkings(featuredParkingList);
			});
		}, 0);
	}, []);

	const fetchNearbyParking = useCallback(async (center) => {
		console.log("api calls");

		return axiosInstance
			.get("/api/parking-service/parking/nearby", {
				params: {
					lat: center[1],
					lng: center[0],
					distance: 1500,
				},
			})
			.then((res) => {
				setSearchedParkingList(res.data);
				return res.data;
			})
			.catch(() => {
				return [];
			});
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
					fetchNearbyParking={fetchNearbyParking}
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
