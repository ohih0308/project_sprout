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

    let url = "/my-page/comment-record";
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
                drawList(boardName, map.commentList);
                drawPaging(boardName, map.paging);
            },
            error: () => {

            }
        }
    )
}

function drawList(boardName, commentList) {
    document.querySelector(".tbody").innerHTML = "";

    document.querySelector(".boardName").value = boardName

    commentList.forEach((obj) => {
            let comment = document.createElement("div");
            comment.classList.add("comment");
            comment.classList.add("row");

            // subject start
            let subject = document.createElement("div");
            subject.classList.add("subject");

            let subjectA = document.createElement("a");
            subjectA.innerHTML = obj.text;
            subjectA.href = "/post/" + obj.postId;


            subject.appendChild(subjectA);
            comment.appendChild(subject);
            // subject end

            // comment info start
            let commentInfo = document.createElement("div");
            commentInfo.classList.add("commentInfo");
            commentInfo.classList.add("column");

            // comment info - date start
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
            commentInfo.appendChild(dateWrapper);
            // post info - date end

            // post info - views start
            let postIdWrapper = document.createElement("div");
            postIdWrapper.classList.add("postIdWrapper");
            postIdWrapper.classList.add("row");

            let postIdIcon = document.createElement("img");
            postIdIcon.classList.add("postIdIcon");
            postIdIcon.src = "/img/id-card.png";

            let postId = document.createElement("span");
            postId.classList.add("postId");
            postId.innerHTML = obj.postId;

            postIdWrapper.appendChild(postIdIcon);
            postIdWrapper.appendChild(postId);
            commentInfo.appendChild(postIdWrapper);
            // post info - views end

            comment.appendChild(commentInfo);
            // post info end


            // delete start
            let del = document.createElement("div");
            del.classList.add("delete");
            del.classList.add("row");
            let checkbox = document.createElement("input");
            checkbox.type = "checkbox";
            checkbox.classList.add("row");
            checkbox.value = obj.commentId;

            del.appendChild(checkbox);
            comment.appendChild(del);
            // delete end

            document.querySelector(".tbody").appendChild(comment);
        }
    )
}

function drawPaging(boardName, paging) {
    document.querySelector(".commentPaging").style.display = "flex";
    document.querySelector(".commentPaging").textContent = ""

    if (paging.startPage > 0) {
        let previous = document.createElement("div");
        previous.classList.add("previous");
        previous.classList.add("paging");
        previous.setAttribute("value", paging.startPage - paging.page);
        let text = document.createTextNode("< ");

        previous.appendChild(text);

        document.querySelector(".commentPaging").appendChild(previous);


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

        document.querySelector(".commentPaging").appendChild(page);
    }

    if (paging.endPage < paging.totalPages - 1) {
        let next = document.createElement("div");
        next.classList.add("next");
        next.classList.add("paging");
        next.setAttribute("value", paging.startPage + paging.page);
        let text = document.createTextNode(" >");

        next.appendChild(text);

        document.querySelector(".commentPaging").appendChild(next);


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
    let selectedCommentIdList = new Array();

    for (let selectBox of document.querySelectorAll(".delete")) {
        if (selectBox.childNodes[0].checked) {
            selectedCommentIdList.push(selectBox.childNodes[0].value);
        }
    }

    $.ajax(
        {
            url: "/my-page/comment-record/delete",
            type: "POST",
            traditional: true,
            // dataType: "JSON",
            data: {"selectedCommentIdList": selectedCommentIdList},

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
