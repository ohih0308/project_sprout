$(document).ready(function () {
        addEvent();
    }
)


function addEvent() {
    let postList = document.querySelectorAll(".post");

    for (let post of postList) {
        post.addEventListener("click", () => {
            let postId = post.getAttribute('data-value');

            location.href = "/post/" + postId;
        })

    }
}