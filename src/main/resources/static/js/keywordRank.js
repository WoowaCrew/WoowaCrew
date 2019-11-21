document.getElementById("keyword-rank").addEventListener("click", function(event) {
    const content = event.target.closest(".keyword").getElementsByClassName("keyword-content")[0].textContent;

    fetch("/api/search?content=" + content, {
        method: 'GET'
    }).then(function (response) {
        const url = response.headers.get("Location");
        window.location.replace(url);
    });
});
