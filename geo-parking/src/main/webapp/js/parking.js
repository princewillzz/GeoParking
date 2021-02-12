
// setTimeout(() => {
//     document.querySelectorAll('#parkingsFetched .row .card').forEach(card => {
//         card.style.opacity = 0.1;
//     })


//     document.querySelectorAll('.fa-circle-notch').forEach(spinner => {
//         spinner.style.display = "inline-flex";
//         spinner.style.opacity = 1;
//     })

// }, 0);


document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('searchParkingBtn').addEventListener('click', fetchParkings);

})

// Fetch available parkings
function fetchParkings() {
    const searchParkingEle = document.getElementById('searchParking');
    let searchParking = searchParkingEle.value;

    // if(!searchParking || searchParking.length < 3) {
    //     console.log("Enter more data")
    //     return;
    // }

    // const xhr = new XMLHttpRequest();
    // const url = "/api/parking";
    // xhr.open('GET', url, true);

    // xhr.onreadystatechange = function() {
    //     if(this.readyState === 5 && this.status === 200) {

    //         searchParkingEle.value = "";
    //     }
    // }

    // xhr.send(searchParking);

    loadParkingCards("data");

    console.log(searchParking);
    searchParkingEle.value = "";

}

// Create cards and fill the body
function loadParkingCards(data) {

    const parkingCardContainer = document.querySelector('#parkingsFetched .row');

    parkingCardContainer.innerHTML = null;

    const element = document.createElement('div');
    element.className = "col-sm-6 col-lg-3";

    const ele = `
        <!-- Card Started -->
        <i class="fas fa-circle-notch fa-pulse"></i>
        <div class="card text-white bg-dark mb-3" >
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
                    <button data-toggle="modal" data-target="#parkingAvailabilityCheckModal" class="btn btn-primary">Book
                        Spot</button>
                    <img class="card-img" style="height: 50px; width: 35px; cursor: pointer;"
                        src="./image/map-thumbnail.png" alt="location">
                </div>
            </div>
        </div>
        <!-- Card ended -->
    `

    

    element.innerHTML += ele;

    parkingCardContainer.appendChild(element);


}