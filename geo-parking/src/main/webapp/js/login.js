
var element = document.querySelector(".changeForm");

element.addEventListener('click', () => {
    var login = document.querySelector(".login_form");
    var register = document.querySelector(".registration_form");
    
    console.log(element.innerHTML);
    if(element.innerHTML === "Register") {
        element.innerHTML = "Login";
        console.log("hii");
        login.style.display = "none";
        register.style.display = "block";
    } else {
        element.innerHTML = "Register";
        console.log("bye");
        register.style.display = "none";
        login.style.display = "block";
    }
 
});


