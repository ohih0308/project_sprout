document.addEventListener("DOMContentLoaded", preparation);

function preparation() {
    boardClickEvent();

    document.querySelector(".selectAll").addEventListener("click", selectAll);

    document.querySelector(".deleteButton").addEventListener("click", deleteSelected);
}

function boardClickEvent() {
    let boards = document.querySelectorAll(".board");

    for (let board of boards) {
        board.addEventListener("click", () => {
            document.querySelector(".selectAll").checked = false;

            let boardName = board.getAttribute("data-value");
            loadRecord(boardName);
        });
    }
}

function loadRecord(boardName, presentPage) {
    if (presentPage == null) {
        presentPage == 0;
    }

    let url = "/my-page/post-record";
    let data = {
        name: document.querySelector("#owner").value,
        boardName: boardName,
        presentPage: presentPage
    }

    $.ajax(
        {
            url: url,
            type: "POST",
            dataType: "JSON",
            data: data,

            success: (map) => {
                drawList(boardName, map.postList);
                drawPaging(boardName, map.paging);
            },
            error: () => {

            }
        }
    )
}

function drawList(boardName, postList) {
    document.querySelector(".tbody").innerHTML = "";

    document.querySelector(".boardName").value = boardName

    postList.forEach((obj) => {
            let post = document.createElement("div");
            post.classList.add("post");
            post.classList.add("row");

            // subject start
            let subject = document.createElement("div");
            subject.classList.add("subject");

            let subjectA = document.createElement("a");
            subjectA.innerHTML = obj.subject;
            subjectA.href = "/post/" + obj.postId;


            subject.appendChild(subjectA);
            post.appendChild(subject);
            // subject end

            // post info start
            let postInfo = document.createElement("div");
            postInfo.classList.add("postInfo");
            postInfo.classList.add("column");

            // post info - date start
            let dateWrapper = document.createElement("div");
            dateWrapper.classList.add("dateWrapper");
            dateWrapper.classList.add("row");

            let dateIcon = document.createElement("img");
            dateIcon.classList.add("dateIcon");
            dateIcon.src = "/img/clock.svg";

            let date = document.createElement("span");
            date.classList.add("date");
            date.innerHTML = dateFormat(obj.regDate);


            dateWrapper.appendChild(dateIcon);
            dateWrapper.appendChild(date);
            postInfo.appendChild(dateWrapper);
            // post info - date end

            // post info - views start
            let viewWrapper = document.createElement("div");
            viewWrapper.classList.add("viewWrapper");
            viewWrapper.classList.add("row");

            let viewIcon = document.createElement("img");
            viewIcon.classList.add("viewIcon");
            viewIcon.src = "/img/eye.png";

            let view = document.createElement("span");
            view.classList.add("view");
            view.innerHTML = obj.views;

            viewWrapper.appendChild(viewIcon);
            viewWrapper.appendChild(view);
            postInfo.appendChild(viewWrapper);
            // post info - views end

            post.appendChild(postInfo);
            // post info end


            // delete start
            let del = document.createElement("div");
            del.classList.add("delete");
            del.classList.add("row");
            let checkbox = document.createElement("input");
            checkbox.type = "checkbox";
            checkbox.classList.add("row");
            checkbox.value = obj.postId;

            del.appendChild(checkbox);
            post.appendChild(del);
            // delete end

            document.querySelector(".tbody").appendChild(post);
        }
    )
}

function drawPaging(boardName, paging) {
    document.querySelector(".postPaging").style.display = "flex";
    document.querySelector(".postPaging").textContent = ""

    if (paging.startPage > 0) {
        let previous = document.createElement("div");
        previous.classList.add("previous");
        previous.classList.add("paging");
        previous.setAttribute("value", paging.startPage - paging.page);
        let text = document.createTextNode("< ");

        previous.appendChild(text);

        document.querySelector(".postPaging").appendChild(previous);


        document.querySelector(".previous").addEventListener("click", () => {
            loadRecord(boardName, paging.startPage - paging.pages);
        })
    }

    for (let num = paging.startPage; num <= paging.endPage; num++) {
        let page = document.createElement("div");
        page.classList.add("paging");
        page.setAttribute("value", num);
        let text = document.createTextNode((num + 1));

        page.appendChild(text);

        document.querySelector(".postPaging").appendChild(page);
    }

    if (paging.endPage < paging.totalPages - 1) {
        let next = document.createElement("div");
        next.classList.add("next");
        next.classList.add("paging");
        next.setAttribute("value", paging.startPage + paging.page);
        let text = document.createTextNode(" >");

        next.appendChild(text);

        document.querySelector(".postPaging").appendChild(next);


        document.querySelector(".next").addEventListener("click", () => {
            loadRecord(boardName, paging.startPage + paging.pages);
        })
    }


    document.querySelectorAll(".paging").forEach((element) => {
        element.addEventListener("click", () => {
            loadRecord(boardName, element.getAttribute("value"))
        });
    })
}

function selectAll() {
    let dels = document.querySelectorAll(".delete");

    for (let del of dels) {
        del.firstChild.checked = !del.firstChild.checked;
    }

}

function deleteSelected() {
    let selectedPostIdList = new Array();

    for (let selectBox of document.querySelectorAll(".delete")) {
        if (selectBox.childNodes[0].checked) {
            selectedPostIdList.push(selectBox.childNodes[0].value);
        }
    }

    $.ajax(
        {
            url: "/my-page/post-record/delete",
            type: "POST",
            traditional: true,
            // dataType: "JSON",
            data: {"selectedPostIdList": selectedPostIdList},

            success: (simpleResponse) => {
                alert(simpleResponse.message);

                if (simpleResponse.result) {
                    let boardName = document.querySelector(".boardName").value;

                    loadRecord(boardName, 0);
                } else {
                }
            },
            error: function (request, status, error) {

                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
            }
        }
    )
}
