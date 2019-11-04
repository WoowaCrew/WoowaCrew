move();
age();

function move() {
    var date = Math.ceil(new Date().getMonth() / 12  * 100);
    var progressBarDiv = document.getElementById("myBar");
    var width = 0;
    var id = setInterval(frame, 5);
    function frame() {
        if (width >= date) {
            clearInterval(id);
        } else {
            width++;
            progressBarDiv.style.width = width + "%";
            progressBarDiv.innerHTML = width  + "%";
        }
    }
}

function age() {
    var ageDiv = document.getElementById("userAge");
    var age = ageDiv.dataset.age;

    if (age < 25) {
        ageDiv.innerText = "🐣";
    } else if (25 <= age && age < 30) {
        ageDiv.innerText = "🐥";
    } else if (age >= 30) {
        ageDiv.innerText = "🐲";
    }
}

function toggleNav() {
    var sidenavDiv = document.getElementById("mySidenav");
    if (sidenavDiv.style.width === "250px") {
        sidenavDiv.style.width = "0px";
    } else {
        sidenavDiv.style.width = "250px";
    }
}