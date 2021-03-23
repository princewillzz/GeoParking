import React from "react";
import PrimarySearchAppBar from "../component/appbar/PrimarySearchAppBar";
import SearchParking from "../component/search-parking/SearchParking";

function Home() {
	return (
		<>
			<PrimarySearchAppBar />
			<SearchParking />
		</>
	);
}

export default Home;
