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
}


function writeDiary() {
    let data = $('.diaryInput').serialize();
    let url = "/diary/write";

    $.ajax(
        {
            url: url,
            type: "POST",
            data: data,
            async: false,

            success: (simpleResponse) => {
                if (simpleResponse.result) {
                    location.reload();
                }
            },
            error: () => {

            }

        }
    )
}

function checkBox() {
    let checkBox = document.querySelector(".privatePostInput");

    if (checkBox.value == "false") {
        checkBox.value = true;
    } else {
        checkBox.value = false;
    }
}

function turnPage(page) {
    document.querySelector(".presentPage").value = page;
    document.querySelector(".pageForm").submit();
}