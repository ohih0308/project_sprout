function dateFormat(regDate) {
    let date = new Date(regDate);

    let yy = date.getFullYear()
    let MM = date.getMonth() + 1;
    let dd = date.getDate();

    let hh = date.getHours();
    let mm = date.getMinutes();

    let formattedDate = yy + "." + MM + "." + dd + " " + hh + ":" + mm;

    return formattedDate;
}

function showTag(tag) {
    tag.style.display = "inline-block";
}

function hideTag(tag) {
    tag.style.display = "none";
}

function deleteTag(tag) {
    tag.remove();
}

function getTime() {
    let date = new Date();

    let year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();

    let hour = date.getHours();
    let minute = date.getMinutes();
    let second = date.getSeconds();

    return year + "." + month + "." + day + " " + hour + ":" + minute + ":" + second;
}