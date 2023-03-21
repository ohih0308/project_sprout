$(document).ready(function () {
        connect()
    }
)

function connect() {
    const webSocket = new WebSocket("ws://localhost:8080/ws/chat");


    document.querySelector(".submitButton").addEventListener("click", send);

    webSocket.onmessage = onMessage;
    webSocket.onopen = onOpen;
    webSocket.onclose = onClose;


    function send() {
        let chatInput = document.querySelector(".chatInput").value;

        if (chatInput.length < 1) {

        } else {
            webSocket.send(chatInput);
            document.querySelector(".chatInput").value = "";
        }

    }

    function onClose() {

    }

    function onOpen() {
    }

    function onMessage(message) {
        let data = JSON.parse(message.data);

        setUserName(data.userName);
        setUserCount(data.userCount);
        chatRecord(data.message);
        setUserList(data.userList);
    }
}

function chatRecord(message) {
    let chat = document.createElement("div");
    let line = document.createTextNode(message);

    chat.appendChild(line);

    chat.style.margin = "5px";
    document.querySelector(".chatList").appendChild(chat);

    scroll();
}

function scroll() {
    let chatList = document.querySelector(".chatList");

    chatList.scrollTop = chatList.scrollHeight;
}


function setUserName(userName) {
    document.querySelector(".myName").innerHTML = userName;
}

function setUserCount(userCount) {
    document.getElementById("userCount").innerText = " (" + userCount + ")";
}

function setUserList(userList) {

    document.getElementById("userList").textContent = "";

    for (const user of userList.sort()) {
        let userNameWrapper = document.createElement("div");
        let userName = document.createElement("span");
        userName.innerText = user;

        userNameWrapper.classList.add("userName");

        userNameWrapper.appendChild(userName);

        document.getElementById("userList").appendChild(userNameWrapper);
    }
}