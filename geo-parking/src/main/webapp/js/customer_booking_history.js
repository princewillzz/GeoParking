document.addEventListener("DOMContentLoaded", () => {
	fetchCustomerBookings();
});

function fetchCustomerBookings() {
	const xhr = new XMLHttpRequest();
	const url = "/api/secured/my-bookings";

	xhr.open("GET", url, true);

	xhr.onreadystatechange = function () {
		if (this.readyState === 4 && this.status === 200) {
			const bookingList = JSON.parse(this.response);

			generateBookingCards(bookingList);
		}
	};

	xhr.send(null);
}

// how to dynaically generate card using the json data and after receving the data from the called api
function generateBookingCards(bookingList) {
	// console.log(bookingList);

	const bookingCardContainer = document.querySelector(
		"#bookingCardContainer"
	);

	bookingList.forEach((booking) => {
		const card = document.createElement("div");
		card.className = "card booking-card";

		const fromTimeDate = new Date(booking.fromTimeDate);
		const toTimeDate = new Date(booking.toTimeDate);
		console.log(fromTimeDate.toLocaleTimeString());

		let paymentDependentDiv = ``;

		if (booking.paymentDone) {
			paymentDependentDiv = `
            <div id="paymentDoneDiv">
                <button type="button" class="btn btn-paid-success"><b> Paid</b> <i style="color: green;" class="fas fa-check-circle"></i></button>
                <button type="button" class="btn btn-primary btn-receipt">Receipt Link</button>
            </div>            
            `;
		} else {
			paymentDependentDiv = `
            <div id="failureDiv" class="mx-auto ">
                <button class="btn btn-danger disabled" style="border-radius: 2rem;" ><b> Cancelled </b> <i class="fas fa-times-circle"></i></button> 
            </div>
            `;
		}

		const parkingName = booking.parking.name;
		const parkingAddress = booking.parking.address.substring(0, 20);
		card.innerHTML += `
	    <div class="card-body">
	    <img class="card-img-top" style="opacity: 0.7; border-radius: 1rem;" src="https://b.kisscc0.com/20180517/rhq/kisscc0-parking-transport-car-park-house-traffic-5afd7a09a9a3a4.2002531015265612896949.jpg" alt="Card image" style="width:100%">
	    <div class="card-img-overlay"> </div>

	    <h4 class="card-title-first" style="text-align: center; color: rgb(80, 49, 80);"><b>${parkingName}</b></h4>
	      <h5 class="card-title" style="text-align: center; color: brown;"><b>${parkingAddress}...</b></h5>
	        <hr>
	      <p class="card-text" style="text-align: center; ', sans-serif;">
	        <b>${fromTimeDate.toLocaleDateString()}: ${fromTimeDate.toLocaleTimeString()}<br>
	        ${toTimeDate.toLocaleDateString()}: ${toTimeDate.toLocaleTimeString()} </b><br>
	    </p>
	    <hr>
        ${paymentDependentDiv}
	    </div>`;

		bookingCardContainer.appendChild(card);
	});

	// for (let i = 0; i < bookingdata.length; i++) {

	// 	value.appendChild(card);
	// }
}
