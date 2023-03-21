$(document).ready(function () {
    addEvent()
});


function addEvent() {
    let boardList = document.querySelectorAll(".board");

    for (let board of boardList) {
        board.addEventListener("click", () => {
            toBoard(board);
        })
    }

}

function toBoard(board) {
    let boardName = board.getAttribute("data-value");

    location.href = "/board/" + boardName;
}