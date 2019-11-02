var i = 0;
var myBar = document.getElementById("myBar")
var date = Math.ceil(new Date().getMonth() / 12  * 100);

myBar.style.width = date + "%"
myBar.innerText = date + "%"

function move() {
    if (i == 0) {
        i = 1;
        var elem = document.getElementById("myBar");
        var width = 0;
        var id = setInterval(frame, 5);
        function frame() {
            if (width >= date) {
                clearInterval(id);
                i = 0;
            } else {
                width++;
                elem.style.width = width + "%";
                elem.innerHTML = width  + "%";
            }
        }
    }
}