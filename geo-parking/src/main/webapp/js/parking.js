// setTimeout(() => {
//     document.querySelectorAll('#parkingsFetched .row .card').forEach(card => {
//         card.style.opacity = 0.1;
//     })

//     document.querySelectorAll('.fa-circle-notch').forEach(spinner => {
//         spinner.style.display = "inline-flex";
//         spinner.style.opacity = 1;
//     })

// }, 0);

document.addEventListener("DOMContentLoaded", () => {
    document
        .getElementById("searchParkingForm")
        .addEventListener("submit", fetchParkings);
});

// Fetch available parkings by address
function fetchParkings(event) {
    event.preventDefault();
    const searchParkingEle = document.getElementById("searchParking");
    let parkingAddress = searchParkingEle.value;

    console.log(parkingAddress);

    // if(!searchParking || searchParking.length < 3) {
    //     console.log("Enter more data")
    //     return;
    // }

    const xhr = new XMLHttpRequest();
    const url = "/api/parking/search";
    xhr.open("POST", url, true);

    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            console.log(this.response);

            const parkingData = JSON.parse(this.response);

            loadParkingCards(parkingData);

            searchParkingEle.value = "";
        } else if (this.readyState === 4) {
            document.querySelector("#parkingsFetched .row").innerHTML = `
                    <div class="alert alert-danger mx-auto w-50" role="alert">
                        Nothing found...!
                    </div>
                `;
        }
    };

    xhr.send(parkingAddress);

    // loadParkingCards("data");
}

// Create cards and fill the body
function loadParkingCards(data) {
    const parkingCardContainer = document.querySelector(
        "#parkingsFetched .row"
    );

    if (data.length < 1) {
        parkingCardContainer.innerHTML = `
        <div class="alert alert-danger mx-auto w-50" role="alert">
            No Parking found...!
        </div>
        `;
        return;
    }

    parkingCardContainer.innerHTML = null;

    data.forEach((each_parking_data) => {
        const element = document.createElement("div");
        element.className = "col-sm-6 col-lg-3";

        // Parking Card
        element.innerHTML = `        
            <i class="fas fa-circle-notch fa-pulse"></i>  
            ${createParkingCard(each_parking_data)}  
        `;

        parkingCardContainer.appendChild(element);
    });
}

function createParkingCard(parkingData) {
    const parkingId = parkingData.uid;
    console.log(parkingId);
    return `
        <div id="FeatchedParkingCard${parkingId}"
            class="card text-white bg-dark mb-3">
            <div class="card-header">
                Asansol
            </div>
            <div class="card-body">

                <h5 class="card-title">Parking name</h5>
                <p class="card-text">
                <Address>
                    Vardhan meds, Manberia
                    Barakar, Asansol
                    West Bengal-713324
                </Address>
                </p>
                <div>
                    <button onclick="alert('Hii!')"
                    class="payForBookingSlotBtn btn btn-success" 
                    hidden="true">
                    Make Payment
                    </button>

                    <button data-toggle="modal"
                        onclick="setParkingIdInAvailabilityModal('${parkingId}')"
                        data-target="#parkingAvailabilityCheckModal" 
                        class="checkBookingSlotAvailabilityBtn btn btn-primary">Book
                        Spot</button>
                    <img class="card-img" 
                    style="height: 50px; width: 35px; cursor: pointer;"
                        src="./image/map-thumbnail.png" alt="location">
                </div>
            </div>        
    `;
}
