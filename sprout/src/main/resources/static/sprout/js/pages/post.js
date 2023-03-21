document.addEventListener("DOMContentLoaded", addEvent);

function addEvent() {
    document.querySelector(".newCommentSaveButton").addEventListener("click", writeComment);

    getComments();
}

function writeComment() {
    let data = $(".newCommentForm").serialize();

    $.ajax(
        {
            url: "/comment/write",
            type: "POST",
            data: data,
            async: false,

            success: () => {
                location.reload();
            },
            error: (responseException) => {
                alert(responseException.responseJSON.message);
            }

        }
    )
}

function getComments(presentPage) {
    let postId = document.querySelector(".postId").value;

    let url = "/comment/" + postId + "/list";

    if (presentPage == null) {
        presentPage = 0;
    }

    let data = {
        postId: postId,
        presentPage: presentPage
    };

    $.ajax(
        {
            url: url,
            type: "POST",
            data: data,
            dataType: "JSON",
            async: false,

            success: (comments) => {
                putCommentList(comments.commentList);
                putCommentPaging(comments.paging);
            },
            error: function (request, status, error) {
                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);

            }
        }
    )
}

function putCommentList(commentList) {
    document.querySelector(".commentsList").textContent = ""

    let loginMember;

    $.ajax(
        {
            url: "/user/getLoginMember",
            type: "POST",
            async: false,

            success: (data) => {
                loginMember = data;
            },
            error: function (request, status, error) {

                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);

            }
        }
    )


    if (loginMember == null) {
        loginMember = {author: ""};
    }


    commentList.forEach((obj) => {
            let comment = document.createElement("div");
            comment.classList.add("comment");
            comment.classList.add("column");


            let commentHead = document.createElement("div");
            commentHead.classList.add("commentHead");
            commentHead.classList.add("row");


            let rowNum = document.createElement("div");
            rowNum.classList.add("commentIndex");
            let id = document.createTextNode(obj.rowNum);
            rowNum.appendChild(id);
            commentHead.appendChild(rowNum);


            let commentAuthor = document.createElement("div");
            commentAuthor.classList.add("commentAuthor");

            let author;

            if (obj.userType == 2) {
                author = document.createElement("a");
                author.innerHTML = obj.author;
                author.href = "/my-page/" + obj.author;
            } else {
                author = document.createTextNode(obj.author);
            }

            commentAuthor.appendChild(author);
            commentHead.appendChild(commentAuthor);


            let commentDate = document.createElement("div");
            commentDate.classList.add("commentDate");
            let date = document.createTextNode(dateFormat(obj.regDate));
            commentDate.appendChild(date);
            commentHead.appendChild(commentDate);


            if (obj.userType == 0 ||
                obj.userType != 0 && obj.author == loginMember.name) {
                let commentDeleteButton = document.createElement("button");
                commentDeleteButton.classList.add("commentDeleteButton");
                let button = document.createTextNode("X");
                commentDeleteButton.appendChild(button);
                commentHead.appendChild(commentDeleteButton);

                if (obj.userType == 0) {
                    commentDeleteButton.addEventListener("click", () => {
                        let pw = prompt("Password.");
                        deleteComment(obj.commentId, pw);
                    });
                } else {
                    commentDeleteButton.addEventListener("click", () => {
                        deleteComment(obj.commentId, "");
                    });
                }
            }


            let commentBody = document.createElement("div");
            commentBody.classList.add("commentBody");
            let text = document.createTextNode(obj.text);
            commentBody.appendChild(text);


            comment.appendChild(commentHead);
            comment.appendChild(commentBody);

            document.querySelector(".commentsList").appendChild(comment);
        }
    );
}

function putCommentPaging(paging) {
    document.querySelector(".commentPaging").textContent = ""

    if (paging.startPage > 0) {
        let previous = document.createElement("div");
        previous.classList.add("previous");
        previous.setAttribute("value", paging.startPage - paging.page);
        let text = document.createTextNode("< ");

        previous.appendChild(text);

        document.querySelector(".commentPaging").appendChild(previous);


        document.querySelector(".previous").addEventListener("click", () => {
            getComments(paging.startPage - paging.pages);
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
        next.setAttribute("value", paging.startPage + paging.page);
        let text = document.createTextNode(" >");

        next.appendChild(text);

        document.querySelector(".commentPaging").appendChild(next);


        document.querySelector(".next").addEventListener("click", () => {
            getComments(paging.startPage + paging.pages);
        })
    }


    document.querySelectorAll(".paging").forEach((element) => {
        element.addEventListener("click", () => {
            getComments(element.getAttribute("value"))
        });
    })
}

function deleteComment(commentId, pw) {
    let data = {"commentId": commentId, "pw": pw};
    let url = "/comment/" + commentId + "/delete";

    $.ajax(
        {
            url: url,
            type: "POST",
            data: data,
            dataType: "JSON",
            async: false,

            success: (simpleResponse) => {
                alert(simpleResponse.message);
                if (simpleResponse.result) {
                    location.reload();
                }
            },
            error: () => {
                alert("Failure");
            }
        }
    )
}

function fileDownload(savedFileName) {
    document.getElementById("savedFileName").value = savedFileName;

    document.querySelector("#fileDownloadForm").submit();
}

function setReputation(postId, type) {
    let url = "/post/reputation";
    let data = {postId: postId, type: type};

    $.ajax(
        {
            url: url,
            type: "POST",
            data: data,
            success: () => {
                location.reload();
            },
            error: () => {
            }
        }
    )
}