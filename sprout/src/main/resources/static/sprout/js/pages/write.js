$(document).ready(function () {
    //여기 아래 부분
    $('#summernote').summernote({
        height: 400,                 // 에디터 높이
        minHeight: null,             // 최소 높이
        maxHeight: null,             // 최대 높이
        focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
        lang: "ko-KR"					// 한글 설정
        // placeholder: '최대 2048자까지 쓸 수 있습니다'	//placeholder 설정
    });
});


let fileCounter = 0;

function fileSelectionButton() {
    drawFileList();

    hideOldInput();

    drawNewInput();

}

function drawFileList() {
    let fileInput = document.getElementById("fileInput");
    let files = fileInput.files;


    for (let file of files) {

        let line = document.createElement("div");
        line.classList.add("line", "row");
        line.id = fileCounter;

        fileCounter++;

        let fileName = document.createElement("div");
        fileName.classList.add("fileName");
        fileName.innerText = file.name;

        let deleteButton = document.createElement("button");
        deleteButton.type = "button";
        deleteButton.classList.add("deleteButton");
        deleteButton.innerText = "X";

        deleteButton.addEventListener("click", () => {
            deleteFile(file, deleteButton);
        });

        line.appendChild(fileName);
        line.appendChild(deleteButton);

        document.querySelector(".fileBox").appendChild(line);
    }
}


function hideOldInput() {
    let oldFileInput = document.querySelector("#fileInput");
    let oldLabel = document.querySelector(".fileAddButton");

    oldFileInput.removeAttribute("id");

    hideTag(oldFileInput);
    deleteTag(oldLabel);
}


function drawNewInput() {
    let fileInput = document.createElement("input");
    fileInput.type = "file";
    fileInput.id = "fileInput";
    fileInput.classList.add("fileInput");
    fileInput.name = "multipartFileList";
    fileInput.multiple = true;

    fileInput.addEventListener("change", () => {
        fileSelectionButton();
    });


    let label = document.createElement("label");
    label.htmlFor = "fileInput";
    label.classList.add("fileAddButton", "column");
    label.innerText = "Choose Files";


    document.querySelector(".fileInputWrapper").appendChild(fileInput);
    document.querySelector(".fileInputWrapper").appendChild(label);
}

function deleteFile(selectedFile, tag) {

    let fileInputList = document.querySelectorAll(".fileInput");

    for (let i = 0; i < fileInputList.length; i++) {
        const dataTransfer = new DataTransfer();

        let fileInput = fileInputList[i];
        let files = fileInput.files;

        let filesArray = Array.from(files);


        for (let j = 0; j < filesArray.length; j++) {
            if (filesArray[j] == selectedFile) {
                filesArray.splice(j, 0);
            } else {
                dataTransfer.items.add(filesArray[j]);
            }
        }

        fileInput.files = dataTransfer.files;
        dataTransfer.clearData();
    }

    deleteTag(tag.parentElement);
}