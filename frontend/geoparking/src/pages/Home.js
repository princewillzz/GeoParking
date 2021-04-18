import React, { useEffect, useState } from "react";
import { fetchFeaturedParkings } from "../api/parking-public-api";
import SearchParking from "../component/search-parking/SearchParking";

function Home() {
	const [featuredParkings, setFeaturedParkings] = useState([]);

	useEffect(() => {
		setTimeout(() => {
			fetchFeaturedParkings().then((featuredParkingList) =>
				setFeaturedParkings(featuredParkingList)
			);
		}, 0);
	}, []);

	return (
		<>
			<SearchParking featuredParkings={featuredParkings} />
		</>
	);
}

export default Home;
