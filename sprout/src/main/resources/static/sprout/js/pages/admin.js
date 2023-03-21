$(document).ready(function () {
        highlightTag(document.querySelector(".configSideBar").children[0]);
        showTag(document.querySelector(".configBody").children[0]);

        addEvent();
    }
)

function addEvent() {
    sideBarClick();

    document.querySelector(".createCategoryButton").addEventListener("click", createCategory);
    document.querySelector(".deleteCategoryButton").addEventListener("click", deleteCategory);

    document.querySelector(".createBoardButton").addEventListener("click", createBoard);
    document.querySelector(".deleteBoardButton").addEventListener("click", deleteBoard);

    document.querySelector("#multipartFile").addEventListener("change", writeEventFileName);
    document.querySelector("#commercialFile").addEventListener("change", writeCommercialFileName);

    document.querySelector(".addEventButton").addEventListener("click", addEventFile);
    document.querySelector(".addCommercialButton").addEventListener("click", addCommercial);
}

function sideBarClick() {
    document.querySelector(".configSideBar").children[0].addEventListener("click",
        () => {
            removeHighlightTag(document.querySelector(".configSideBar").children[1]);
            removeHighlightTag(document.querySelector(".configSideBar").children[2]);
            removeHighlightTag(document.querySelector(".configSideBar").children[3]);
            removeHighlightTag(document.querySelector(".configSideBar").children[4]);

            highlightTag(document.querySelector(".configSideBar").children[0]);

            hideTag(document.querySelector(".configBody").children[1]);
            hideTag(document.querySelector(".configBody").children[2]);
            hideTag(document.querySelector(".configBody").children[3]);
            hideTag(document.querySelector(".configBody").children[4]);

            showTag(document.querySelector(".configBody").children[0]);
        });
    document.querySelector(".configSideBar").children[1].addEventListener("click",
        () => {
            removeHighlightTag(document.querySelector(".configSideBar").children[0]);
            removeHighlightTag(document.querySelector(".configSideBar").children[2]);
            removeHighlightTag(document.querySelector(".configSideBar").children[3]);
            removeHighlightTag(document.querySelector(".configSideBar").children[4]);

            highlightTag(document.querySelector(".configSideBar").children[1]);

            hideTag(document.querySelector(".configBody").children[0]);
            hideTag(document.querySelector(".configBody").children[2]);
            hideTag(document.querySelector(".configBody").children[3]);
            hideTag(document.querySelector(".configBody").children[4]);

            showTag(document.querySelector(".configBody").children[1]);
        });
    document.querySelector(".configSideBar").children[2].addEventListener("click",
        () => {
            removeHighlightTag(document.querySelector(".configSideBar").children[0]);
            removeHighlightTag(document.querySelector(".configSideBar").children[1]);
            removeHighlightTag(document.querySelector(".configSideBar").children[3]);
            removeHighlightTag(document.querySelector(".configSideBar").children[4]);

            highlightTag(document.querySelector(".configSideBar").children[2]);

            hideTag(document.querySelector(".configBody").children[0]);
            hideTag(document.querySelector(".configBody").children[1]);
            hideTag(document.querySelector(".configBody").children[3]);
            hideTag(document.querySelector(".configBody").children[4]);

            showTag(document.querySelector(".configBody").children[2]);
        });
    document.querySelector(".configSideBar").children[3].addEventListener("click",
        () => {
            removeHighlightTag(document.querySelector(".configSideBar").children[0]);
            removeHighlightTag(document.querySelector(".configSideBar").children[1]);
            removeHighlightTag(document.querySelector(".configSideBar").children[2]);
            removeHighlightTag(document.querySelector(".configSideBar").children[4]);

            highlightTag(document.querySelector(".configSideBar").children[3]);

            hideTag(document.querySelector(".configBody").children[0]);
            hideTag(document.querySelector(".configBody").children[1]);
            hideTag(document.querySelector(".configBody").children[2]);
            hideTag(document.querySelector(".configBody").children[4]);

            showTag(document.querySelector(".configBody").children[3]);
        });

    document.querySelector(".configSideBar").children[4].addEventListener("click",
        () => {
            removeHighlightTag(document.querySelector(".configSideBar").children[0]);
            removeHighlightTag(document.querySelector(".configSideBar").children[1]);
            removeHighlightTag(document.querySelector(".configSideBar").children[2]);
            removeHighlightTag(document.querySelector(".configSideBar").children[3]);

            highlightTag(document.querySelector(".configSideBar").children[4]);

            hideTag(document.querySelector(".configBody").children[0]);
            hideTag(document.querySelector(".configBody").children[1]);
            hideTag(document.querySelector(".configBody").children[2]);
            hideTag(document.querySelector(".configBody").children[3]);

            showTag(document.querySelector(".configBody").children[4]);
        });
}

function highlightTag(tag) {
    tag.style.borderBottom = "5px double rgba(230, 230, 230)";
}

function removeHighlightTag(tag) {
    tag.style.borderBottom = "none";
}

function createCategory() {
    let newCategoryName = document.querySelector(".categoryNameInput").value;
    let url = "/admin/create-category";
    let data = {"categoryName": newCategoryName};

    $.ajax(
        {
            url: url,
            data: data,
            type: "POST",
            dateType: "JSON",
            success: (simpleResponse) => {
                alert(simpleResponse.message);

                if (simpleResponse.result) {
                    location.reload();
                }
            },
            error: () => {

            }
        }
    )

}

function deleteCategory() {
    let categoryName = document.querySelector("#deleteCategorySelection").value
    let url = "/admin/delete-category";
    let data = {"categoryName": categoryName};

    $.ajax(
        {
            url: url,
            data: data,
            type: "POST",
            dataType: "JSON",
            success: (simpleResponse) => {
                alert(simpleResponse.message);

                if (simpleResponse.result) {
                    location.reload();
                }
            },
            error: () => {

            }
        }
    )

}

function createBoard() {
    let categoryName = document.querySelector(".boardCategorySelection").value;
    let boardName = document.querySelector(".boardNameInput").value;

    let url = "/admin/create-board";
    let data = {"categoryName": categoryName, "boardName": boardName}

    $.ajax(
        {
            url: url,
            data: data,
            type: "POST",
            dataType: "JSON",
            success: (simpleResponse) => {
                alert(simpleResponse.message);

                if (simpleResponse.result) {
                    location.reload();
                }
            },
            error: () => {

            }
        }
    )
}

function deleteBoard() {
    let data = {"boardName": document.querySelector("#deleteBoardSelection").value};
    let url = "/admin/delete-board";

    $.ajax(
        {
            url: url,
            data: data,
            type: "POST",
            dataType: "JSON",
            success: (simpleResponse) => {
                alert(simpleResponse.message);

                if (simpleResponse.result) {
                    location.reload();
                }
            },
            error: () => {

            }
        }
    )
}

function writeEventFileName() {
    let fileName = document.querySelector("#multipartFile").value;
    document.querySelectorAll(".selectedFile")[0].value = fileName;
}

function writeCommercialFileName() {
    let fileName = document.querySelector("#commercialFile").value;
    document.querySelectorAll(".selectedFile")[1].value = fileName;

}

function addEventFile() {
    let url = "/file/save/event-file";
    let data = new FormData(document.querySelector(".eventAddForm"));

    $.ajax(
        {
            url: url,
            type: "POST",
            processData: false,
            contentType: false,
            dataType: "JSON",
            data: data,
            success: (simpleResponse) => {
                alert(simpleResponse.message);

                if (simpleResponse.result) {
                    location.reload();
                }
            },
            error: () => {

            }
        }
    )
}

function deleteEvent(savedFileName, ext) {
    let url = "/file/delete/event-file";
    let data = {"savedFileName": savedFileName, "ext": ext}

    $.ajax(
        {
            url: url,
            data: data,
            type: "POST",
            dataType: "JSON",
            success: (simpleResponse) => {
                alert(simpleResponse.message);

                if (simpleResponse.result) {
                    location.reload();
                }
            },
            error: () => {

            }
        }
    )
}

function addCommercial() {
    let url = "/file/save/ad-file";
    let data = new FormData(document.querySelector(".commercialAddForm"));

    $.ajax(
        {
            url: url,
            type: "POST",
            processData: false,
            contentType: false,
            dataType: "JSON",
            data: data,
            success: (simpleResponse) => {
                alert(simpleResponse.message);

                if (simpleResponse.result) {
                    location.reload();
                }
            },
            error: () => {

            }
        }
    )
}