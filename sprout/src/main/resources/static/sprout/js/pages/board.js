document.addEventListener("DOMContentLoaded", addEvent);

function addEvent() {
    let length = document.querySelectorAll(".paging").length;
    let lastIdx = document.querySelectorAll(".paging").length - 1;
    let previous = document.querySelector(".previous");

    if (previous != null) {
        previous.addEventListener("click", () => {
            turnPage(parseInt(document.querySelectorAll(".paging")[0].dataset.value) - length);
        })
    }

    document.querySelectorAll(".paging").forEach((page) => {
        page.addEventListener("click", () => {
            turnPage(page.dataset.value);
        })
    });

    let next = document.querySelector(".next");
    if (next != null) {

        next.addEventListener("click", () => {
            turnPage(parseInt(document.querySelectorAll(".paging")[lastIdx].dataset.value) + 1);
        })
    }

    document.querySelector(".searchButton").addEventListener("click", search);
}

function turnPage(page) {
    document.querySelector(".presentPage").value = page;
    document.querySelector(".pageForm").submit();
}

function search() {
    let searchFilter = document.querySelector(".searchFilter").value;
    let searchValue = document.querySelector(".searchValue").value;

    document.querySelector("#searchFilter").value = searchFilter;
    document.querySelector("#searchValue").value = searchValue;

    document.querySelector(".pageForm").submit();
}