document.addEventListener('DOMContentLoaded', () => {
     // Some feature
     setTimeout(() => {
        $('#searchParking').on('mouseover', function () {
            $(this).popover({
                content: '3-letter at least', placement: 'left'
            });
        })
    }, 0);


    // Garbage code needs to be deleted
    let item = document.querySelector('.row').innerHTML;
    for(let i = 0; i < 5; i++)
        document.querySelector('.row').innerHTML += (item);
})


