

document.addEventListener('DOMContentLoaded', ()=>{
    document.getElementById('searchParkingBtn').addEventListener('click', fetchParkings);



    // Some feature
    $('#searchParking').on('mouseover', function(){
        $(this).popover({
            content: '3-letter at least', placement: 'left'
        });
    }) 
    $('#searchParking').on('mouseleave', function(){
        $(this).popover('hide');
    })    

})

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

function loadParkingCards(data) {

    const ele = `
        <div class="card">
            <div class="card-header">
                Featured
            </div>
            <div class="card-body">
                <h5 class="card-title">Special title treatment</h5>
                <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                <a href="#" class="btn btn-primary">Go somewhere</a>
            </div>
        </div>
    `

    // document.getElementById('parkingsFetched').innerHTML = null;

    document.getElementById('parkingsFetched').innerHTML += (ele);

    
}