function colorOnCardNavigationEvent() {
	document
		.querySelectorAll(
			".parkingCard .navigation-for-card .nav-item .nav-link"
		)
		.forEach((link) => {
			link.addEventListener("click", function () {
				// e.preventDefault();
				const linkContainer = this.parentElement.parentElement;

				[...linkContainer.children].forEach((item) => {
					item.children[0].style.color = "#007bff";
					item.children[0].style.backgroundColor = "white";
				});

				this.style.color = "#fff";
				this.style.backgroundColor = "#007bff";
			});
		});
}

function parkingCardDetailEvent() {
	document.querySelectorAll(".parking-detail-link").forEach((link) => {
		link.addEventListener("click", function (e) {
			e.preventDefault();
			const parentCard = this.parentElement.parentElement.parentElement
				.parentElement;
			parentCard.querySelector(".parkingDetailCard").style.display =
				"block";
			parentCard.querySelector(".parkingImageCard").style.display =
				"none";
		});
	});
}
function parkingCardImageEvent() {
	document.querySelectorAll(".parking-image-link").forEach((link) => {
		link.addEventListener("click", function (e) {
			e.preventDefault();
			const parentCard = this.parentElement.parentElement.parentElement
				.parentElement;

			parentCard.querySelector(".parkingDetailCard").style.display =
				"none";
			parentCard.querySelector(".parkingImageCard").style.display =
				"block";
		});
	});
}
function callbackForEvents() {
	colorOnCardNavigationEvent();
	parkingCardDetailEvent();
	parkingCardImageEvent();
}
document.addEventListener("DOMContentLoaded", () => {
	setTimeout(() => {
		loadParkingCards(callbackForEvents);
	}, 0);
});

function loadParkingCards(callback) {
	const parkingCardContainer = document.getElementById(
		"parkingCardContainer"
	);

	const xhr = new XMLHttpRequest();
	const url = "/api/renter/parking";
	xhr.open("GET", url, true);

	xhr.onreadystatechange = function () {
		if (this.readyState === 4 && this.status === 200) {
			const parkingList = JSON.parse(this.response);
			console.log(parkingList);

			parkingList.forEach((parkingData) => {
				const parkingCardWrapper = document.createElement("div");
				parkingCardWrapper.className = "col";
				parkingCardWrapper.innerHTML = generateParkingCard(parkingData);

				parkingCardContainer.appendChild(parkingCardWrapper);
			});

			setTimeout(() => {
				callback();
			}, 0);
		}
	};

	xhr.send(null);
}

function generateParkingCard(parking) {
	return `
		<div class="card text-center parkingCard m-3">
		<div class="card-header navigation-for-card">
			<ul class="nav nav-pills card-header-pills">
				<li class="nav-item">
					<a
						class="nav-link active parking-detail-link"
						href="#"
						>Details</a
					>
				</li>
				<li class="nav-item">
					<a
						class="nav-link parking-image-link"
						href="#"
						>Photos</a
					>
				</li>
				<li class="nav-item">
					<a href="/renter/parking/edit?id=${parking.uid}" class="nav-link"> Edit </a>
				</li>
			</ul>
		</div>

		<div class="parkingDetailCard card">
			<div class="card-body">
				<h5 class="card-title">${parking.name}</h5>
				<h6 class="card-subtitle mb-2 text-muted">
				${parking.address}
				</h6>

				<p>
					Hourly rate:
					<span style="color: green">${parking.hourlyRate}</span>
				</p>

				<hr style="margin-bottom: 0" />
				<ul class="nav justify-content-center">
					<li class="nav-item">
						<a
							class="nav-link"
							style="color: rgb(46, 129, 207)"
						>
							Total <br />
							${parking.total}
						</a>
					</li>
					<li class="nav-item">
						<a
							class="nav-link"
							style="color: rgb(46, 129, 207)"
						>
							Occupied <br />
							${parking.occupied}
						</a>
					</li>
					<li class="nav-item">
						<a
							class="nav-link"
							style="color: rgb(46, 129, 207)"
						>
							Vacant <br />
							${parking.vacant}
						</a>
					</li>
				</ul>
			</div>
			<div class="card-footer text-muted">
				Booked ${parking.noOfTimesBooked} times
			</div>
		</div>

		<div
			class="parkingImageCard card bg-dark text-white"
			style="display: none"
		>
			<div
				id="parkingCarasoulImageControls"
				class="carousel slide"
				data-ride="carousel"
			>
				<ol class="carousel-indicators">
					<li data-slide-to="0"></li>
					<li data-slide-to="1"></li>
					<li data-slide-to="2"></li>
				</ol>
				<div class="carousel-inner">
					<div class="carousel-item active">
						<img
							src="../image/img1.svg"
							alt="First slide"
						/>
					</div>
					<div class="carousel-item">
						<img
							src="../image/map-thumbnail.png"
							alt="Second slide"
						/>
					</div>
					<div class="carousel-item">
						<img
							src="../image/map-thumbnail2.jfif"
							alt="Third slide"
						/>
					</div>
				</div>
				<a
					class="carousel-control-prev"
					href="#parkingCarasoulImageControls"
					role="button"
					data-slide="prev"
				>
					<span
						class="carousel-control-prev-icon"
						aria-hidden="true"
					></span>
					<span class="sr-only">Previous</span>
				</a>
				<a
					class="carousel-control-next"
					href="#parkingCarasoulImageControls"
					role="button"
					data-slide="next"
				>
					<span
						class="carousel-control-next-icon"
						aria-hidden="true"
					></span>
					<span class="sr-only">Next</span>
				</a>
			</div>
		</div>
	</div>	
	`;
}
