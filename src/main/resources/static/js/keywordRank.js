document.getElementById("keyword-rank").addEventListener("click", function(event) {
    const target = event.target;
    if (target.closest("tr").className === "keyword") {
        const keywordArea = target.closest("tr");
        const keywordId = keywordArea.id;

        fetch("/search/" + keywordId, {
            method: "POST"
        }).then(function (response) {
            const url = response.headers.get("Location");
            window.location.replace(url);
        });
    }
});
