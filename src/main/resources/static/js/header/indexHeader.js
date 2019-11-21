function indexHeader() {
    document.getElementById("myProgress").addEventListener("click", move);

    function move() {
        const date = Math.ceil(new Date().getMonth() / 12  * 100);
        const progressBarDiv = document.getElementById("myBar");
        let width = 0;
        const id = setInterval(frame, 5);
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
        const ageDiv = document.getElementById("userAge");
        const age = ageDiv.dataset.age;

        if (age <= 25) {
            ageDiv.innerText = "🐣";
        } else if (25 < age && age < 29) {
            ageDiv.innerText = "🐥";
        } else if (29 === age) {
            ageDiv.innerText = "🐔";
        } else {
            ageDiv.innerText = "🐲";
        }
    }

    function search() {
        const searchContent = document.getElementsByClassName("search-form")[0].value;
        location.href="https://www.google.com/search?q=" + searchContent;
    }

    move();
    age();
}

indexHeader();