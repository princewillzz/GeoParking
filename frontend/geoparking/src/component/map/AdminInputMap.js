/* eslint import/no-webpack-loader-syntax: off */

import Geocoder from "@mapbox/mapbox-gl-geocoder";
import "@mapbox/mapbox-gl-geocoder/dist/mapbox-gl-geocoder.css";
import mapboxgl from "mapbox-gl/dist/mapbox-gl-csp";
import React, { useEffect, useRef } from "react";
import MapboxWorker from "worker-loader!mapbox-gl/dist/mapbox-gl-csp-worker";
import "./mapbox.css";

mapboxgl.workerClass = MapboxWorker;
mapboxgl.accessToken =
	"pk.eyJ1IjoicHJpbmNld2lsbHoiLCJhIjoiY2tvMzFqMWFpMG8yNTJ3czU4NWVjcG5kdyJ9.F20bN0FJ8byK6BxdoBFOfA";

let map;

function AdminInputMap({ handleLocationChange }) {
	const mapContainer = useRef();
	// const [lng, setLng] = useState(-70.9);
	// const [lat, setLat] = useState(42.35);

	useEffect(() => {
		console.log("use effect render", map);

		let lng = -70.9;
		let lat = 42.35;
		let zoom = 8;
		// Getting current location
		// if (!map) {
		console.log("Initializing everthing ");

		// Creating an instance of map
		map = new mapboxgl.Map({
			container: mapContainer.current,
			style: "mapbox://styles/mapbox/streets-v11",
			center: [lng, lat],
			zoom: zoom,
			minZoom: 1,
		});

		// Adding navbar to zoom in and out
		const nav = new mapboxgl.NavigationControl();
		map.addControl(nav);

		// Adding the geocoder
		const geocoder = new Geocoder({
			accessToken: mapboxgl.accessToken,
			mapboxgl: mapboxgl,
			autocomplete: false,
			localGeocoder: forwardGeocoder,
			proximity: {
				longitude: lng,
				latitude: lat,
			},
			marker: false,
		});

		map.addControl(geocoder, "top-left");

		const previosMarkers = []; //[currentLocationMarker];

		geocoder.on("result", function (e) {
			// remove all marker
			previosMarkers.forEach((marker) => marker.remove());
			console.log("getting result");

			// setLat(e.result.center.lat);
			// setLng(e.result.center.lng);
			// handleLocationChange({
			// 	latitude: e.result.center.lat,
			// 	longitude: e.result.center.lng,
			// });

			// console.log(e.result.center);
			const marker = new mapboxgl.Marker({
				draggable: true,
				color: "#191970",
			})
				.setLngLat(e.result.center)
				.addTo(map);

			lat = marker.getLngLat().lat;
			lng = marker.getLngLat().lng;
			handleLocationChange({
				latitude: marker.getLngLat().lat,
				longitude: marker.getLngLat().lng,
			});

			marker.on("dragend", function (e) {
				let lngLat = e.target.getLngLat();
				console.log(lngLat);
				// setLat(lngLat.lat);
				// setLng(lngLat.lng);
				lat = lngLat.lat;
				lng = lngLat.lng;
				handleLocationChange({
					latitude: lngLat.lat,
					longitude: lngLat.lng,
				});
			});

			// add current marker
			previosMarkers.push(marker);
		});

		// Fly to current location in map
		navigator.geolocation.getCurrentPosition(
			(position) => {
				console.log(
					position.coords.longitude,
					position.coords.latitude
				);

				handleLocationChange({
					latitude: position.coords.latitude,
					longitude: position.coords.longitude,
				});
				map.flyTo({
					center: [
						position.coords.longitude,
						position.coords.latitude,
					],
					zoom: zoom,
				});
				const currentMarker = new mapboxgl.Marker({
					color: "#191970",
					draggable: true,
				})
					.setLngLat([
						position.coords.longitude,
						position.coords.latitude,
					])
					.addTo(map);
				currentMarker.on("dragend", function (e) {
					let lngLat = e.target.getLngLat();
					console.log(lngLat);
					// setLat(lngLat.lat);
					// setLng(lngLat.lng);
					lat = lngLat.lat;
					lng = lngLat.lng;
					handleLocationChange({
						latitude: lngLat.lat,
						longitude: lngLat.lng,
					});
				});
				previosMarkers.push(currentMarker);
			},
			() => {
				console.log("map error!!!!");
			},
			{
				enableHighAccuracy: true,
			}
		);

		// remove the map object on destroy
		return () => map.remove();
	}, [handleLocationChange]);

	const forwardGeocoder = (query) => {
		// console.log("QUERY:", query);
	};

	return (
		<div
			style={{ height: 200, width: "100%" }}
			className="input-map-container"
			ref={mapContainer}
		/>
	);
}

export default AdminInputMap;
