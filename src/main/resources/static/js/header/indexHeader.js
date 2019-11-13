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

    if (age <= 25) {
        ageDiv.innerText = "🐣";
    } else if (25 < age && age < 29) {
        ageDiv.innerText = "🐥";
    } else if (29 == age) {
        ageDiv.innerText = "🐔";
    } else {
        ageDiv.innerText = "🐲";
    }
}