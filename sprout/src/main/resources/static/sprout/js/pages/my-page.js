document.addEventListener("DOMContentLoaded", () => {
    clock();
    setInterval(clock, 1000);
});

function clock() {
    let time = getTime();

    document.querySelector(".clock").innerHTML = time;
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
                let inputList = document.querySelectorAll(".userInfo");
                inputList.forEach((input) => {
                        input.value = "";
                    }
                );

                let textInput = document.querySelector(".textInput")
                textInput.value = "";

                alert(simpleResponse.message)
            },
            error: () => {

            }

        }
    )
}

function checkBox() {
    let checkBox = document.querySelector(".privatePost");

    if (checkBox.value == "false") {
        checkBox.value = true;
    } else {
        checkBox.value = false;
    }
}