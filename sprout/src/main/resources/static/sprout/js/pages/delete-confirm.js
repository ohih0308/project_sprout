function postDelete() {
    let postId = document.querySelector(".postId").value;
    let url = "/post/" + postId + "/delete";
    let data = {postId: postId}


    $.ajax(
        {
            url: url,
            type: "post",
            data: data,
            dataType: "JSON",
            async: false,

            success: (simpleResponse) => {
                alert(simpleResponse.message);

                if (simpleResponse.result) {
                    let boardName = document.querySelector(".boardName").value;
                    location.href = "/board/" + boardName;
                } else {
                }
            },
            error: () => {

            }
        }
    )
}

function back() {
    let postId = document.querySelector(".postId").value;

    location.href = "/post/" + postId;
}