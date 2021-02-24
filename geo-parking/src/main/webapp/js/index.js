document.addEventListener("DOMContentLoaded", () => {
    // Some feature
    setTimeout(() => {
        $("#searchParking").on("mouseover", function () {
            $(this).popover({
                content: "3-letter at least",
                placement: "left",
            });
        });
    }, 0);

    // Fetch featured parking
    setTimeout(() => {
        fetchFeaturedParkings();
    }, 0);
});

// Load featured parkings from the backend
// -------TODO----------
function fetchFeaturedParkings() {
    const featuredParkingContainer = document.getElementById(
        "featuredParkingContainer"
    );

    // Remove this garbage code
    let index = 0;
    const loadParkingInterval = setInterval(() => {
        index++;

        const parkingCard = document.createElement("div");
        parkingCard.className = "col-sm-6 col-lg-3";

        parkingCard.innerHTML = createParkingCard({
            uid: parseInt(Math.random() * 100),
        });

        featuredParkingContainer.appendChild(parkingCard);

        if (index === 3) {
            clearInterval(loadParkingInterval);
        }
    }, 1000);

    const xhr = new XMLHttpRequest();
    const url = "/api/parking/featured";
    xhr.open("GET", url, true);

    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            const parkingsData = JSON.parse(this.response);
            parkingsData.forEach((parking) => {
                // creating a column
                const parkingCard = document.createElement("div");
                parkingCard.className = "col-sm-6 col-lg-3";
                // Create a card
                parkingCard.innerHTML = createParkingCard(parking);
                // loading on the webpage
                featuredParkingContainer.appendChild(parkingCard);
            });
        }
    };

    xhr.send(null);
}
