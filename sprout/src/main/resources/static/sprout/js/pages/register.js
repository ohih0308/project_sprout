document.addEventListener("DOMContentLoaded", addEvent);

function addEvent() {
    document.querySelector("#name").addEventListener("focusout", verifyName);
    document.querySelector("#pw").addEventListener("focusout", verifyPw);
    document.querySelector("#pwConfirmation").addEventListener("focusout", verifyPwConfirmation);
    document.querySelector("#multipartFile").addEventListener("change", writeFileName);
    document.querySelector(".emailVerificationCodeSendButton").addEventListener("click", sendVerificationCode);
    document.querySelector(".emailVerificationButton").addEventListener("click", validateVerificationCode);
}

function sendVerificationCode() {
    let email = document.querySelector("#email").value
    let loadingImg = document.querySelector(".loadingImg");
    let emailVerification = document.querySelector(".emailVerification");
    let data = {address: email};

    hideTag(emailVerification);
    showTag(loadingImg);


    $.ajax(
        {
            url: "/email/send-code",
            type: "POST",
            data: data,
            async: true,

            success: () => {
                hideTag(loadingImg)
                showTag(emailVerification);
            },
            error: (response) => {
                alert(response.responseJSON.message);

                hideTag(loadingImg);
            }
        }
    )
}

function validateVerificationCode() {

    let data = {input: document.querySelector(".emailVerificationCodeInput").value};
    let emailVerification = document.querySelector(".emailVerification");

    let emailMessage = document.querySelector("#emailMessage");

    document.querySelector(".emailVerificationCodeInput").value = "";

    $.ajax(
        {
            url: "/email/verify-code",
            type: "POST",
            data: data,
            async: true,

            success: (simpleResponse) => {
                if (simpleResponse.result) {
                    hideTag(emailVerification);

                    emailMessage.style.color = "green";

                    emailMessage.innerHTML = simpleResponse.message;

                    showTag(emailMessage)
                } else {
                    emailMessage.innerHTML = simpleResponse.message;

                    emailMessage.style.color = "red";

                    showTag(emailMessage)
                }
            },
            error: (response) => {
                alert(response.responseJSON.message);
            }
        }
    );
}

function verifyPw() {
    let data = {
        pw: document.querySelector("#pw").value
    };

    let pwMessage = document.querySelector("#pwMessage");

    $.ajax(
        {
            url: "/user/verify-pw",
            type: "POST",
            data: data,
            async: false,

            success: (simpleResponse) => {
                pwMessage.style.color = "green";

                pwMessage.innerHTML = simpleResponse.message;

                showTag(pwMessage)
            },
            error: (response) => {
                pwMessage.innerHTML = response.responseJSON.message;

                pwMessage.style.color = "red";

                showTag(pwMessage)
            }
        }
    )
}

function verifyPwConfirmation() {
    let data = {
        pw: document.querySelector("#pw").value,
        confirmation: document.querySelector("#pwConfirmation").value
    };

    let pwConfirmationMessage = document.querySelector("#pwConfirmationMessage");

    $.ajax(
        {
            url: "/user/verify-pw-confirmation",
            type: "POST",
            data: data,
            async: false,

            success: (simpleResponse) => {
                if (simpleResponse.result) {
                    pwConfirmationMessage.style.color = "green";

                    pwConfirmationMessage.innerHTML = simpleResponse.message;

                    showTag(pwConfirmationMessage)
                } else {
                    pwConfirmationMessage.innerHTML = simpleResponse.message;

                    pwConfirmationMessage.style.color = "red";

                    showTag(pwConfirmationMessage)
                }
            },
            error: (response) => {
                console.log(response);
            }
        }
    )
}

function verifyName() {
    let data = {name: document.querySelector("#name").value};

    let nameMessage = document.querySelector("#nameMessage");

    $.ajax(
        {
            url: "/user/verify-name",
            type: "POST",
            data: data,
            async: false,

            success: (simpleResponse) => {
                nameMessage.style.color = "green";

                nameMessage.innerHTML = simpleResponse.message;

                showTag(nameMessage)
            },
            error: (response) => {

                nameMessage.innerHTML = response.responseJSON.message;

                nameMessage.style.color = "red";

                showTag(nameMessage)
            }
        }
    )
}


function writeFileName() {
    let fileName = document.querySelector("#multipartFile").value;
    document.querySelector(".selectedFile").value = fileName;
}


function showTag(tag) {
    tag.style.display = "inline-block";
}

function hideTag(tag) {
    tag.style.display = "none";
}