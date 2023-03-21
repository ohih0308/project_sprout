$(document).ready(function () {
        userInfo();
        addEvent();

        highlightTag(document.querySelector(".userInfo"));
        showTag(document.querySelector(".userInfoCard"));
    }
)

function addEvent() {
    let configNavList = document.querySelectorAll(".configNav");
    for (let configNav of configNavList) {
        configNav.addEventListener("click", () => {
                for (const tag of configNavList) {
                    removeHighlightTag(tag);
                }

                highlightTag(configNav);
            }
        );
    }


    let userInfoCard = document.querySelector(".userInfoCard");
    let editInfoCard = document.querySelector(".editInfoCard");
    let deactivateCard = document.querySelector(".deactivateCard");

    document.querySelector(".userInfo").addEventListener("click", () => {
        showTag(userInfoCard);

        hideTag(editInfoCard);
        hideTag(deactivateCard);
    });

    document.querySelector(".editInfo").addEventListener("click", () => {
        showTag(editInfoCard);

        hideTag(userInfoCard);
        hideTag(deactivateCard);
    })

    document.querySelector(".deactivate").addEventListener("click", () => {
        showTag(deactivateCard);

        hideTag(userInfoCard);
        hideTag(editInfoCard);

        deactivateButtonAppear();
    })

    document.querySelector(".deactivateButton").addEventListener("click", deactivateAccount);

    document.querySelector("#profileImage").addEventListener("change", writeFileName);

    document.querySelector(".nameUpdateButton").addEventListener("click", verifyName);

    document.querySelector(".pwUpdateButton").addEventListener("click", verifyPw);

    document.querySelector(".profileImageUpdateButton").addEventListener("click", updateProfileImage);
    document.querySelector(".diaryActivationSettingButton").addEventListener("click", diaryActivationSetting);
}

function userInfo() {
}

function highlightTag(tag) {
    tag.style.borderBottom = "5px double rgba(230, 230, 230)";
}

function removeHighlightTag(tag) {
    tag.style.borderBottom = "none";
}

function deactivateButtonAppear() {
    let deactivateButtonWrapper = document.querySelector(".deactivateButtonWrapper");

    setTimeout(() =>
        deactivateButtonWrapper.style.display = "flex", 1500)
}

function deactivateAccount() {
    let url = "/user/deactivate-account";

    if (confirm("Account deactivation cannot be undone.")) {
        $.ajax(
            {
                url: url,
                type: "GET",
                success: () => {
                    location.href = "/"
                },
                error: () => {

                }
            }
        )
    }
}

function writeFileName() {
    let fileName = document.querySelector("#profileImage").value;

    document.querySelector(".imageInput").innerText = fileName;
}

function verifyName() {
    let url = "/user/verify-name";
    let name = document.querySelector(".nameInput").value;

    $.ajax(
        {
            url: url,
            data: {"name": name},
            type: "POST",
            dataType: "JSON",
            success: (simpleResponse) => {
                if (simpleResponse.result) {
                    updateName()
                }
            },
            error: () => {

            }

        }
    )
}

function updateName() {
    let url = "/user/update-name";
    let name = document.querySelector(".nameInput").value;

    $.ajax(
        {
            url: url,
            data: {"name": name},
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

function verifyPw() {
    let url = "/user/verify-pw-confirmation";
    let pw = document.querySelector(".pwInput").value;

    $.ajax(
        {
            url: url,
            data: {"pw": pw, "confirmation": pw},
            type: "POST",
            dataType: "JSON",
            success: (simpleResponse) => {
                if (simpleResponse.result) {
                    updatePw();
                }
            },
            error: () => {

            }
        }
    )
}

function updatePw() {
    let url = "/user/update-pw";
    let pw = document.querySelector(".pwInput").value;


    $.ajax(
        {
            url: url,
            data: {"pw": pw},
            type: "POST",
            dataType: "JSON",
            success: (simpleResponse) => {
                alert(simpleResponse.message);
                if (simpleResponse.result) {
                    location.reload();
                }
            },
            error: () => {
                console.log("error")
            }
        }
    )
}

function updateProfileImage() {
    let url = "/user/update-profile-image";
    let data = new FormData(document.querySelector("#profileImageForm"));

    $.ajax(
        {
            url: url,
            data: data,
            type: "POST",
            dataType: "JSON",
            processData: false,
            contentType: false,
            success: (simpleResponse) => {
                alert(simpleResponse.message);
                if (simpleResponse.result) {
                    location.reload();
                }
            },
            error: () => {
                console.log("error")
            }
        }
    )
}

function diaryActivationSetting() {
    let diaryActivation = document.querySelector("input[name='diaryActivation']:checked").value
    let url = "";

    if (diaryActivation == 0) {
        url = "/diary/deactivate";
    }else{
        url = "/diary/activate";
    }

    $.ajax(
        {
            url: url,
            type:"POST",
            dataType: "JSON",
            success: (simpleResponse) => {
                alert(simpleResponse.message);
                if (simpleResponse.result) {
                    location.reload();
                }
            },
            error:function(request,status,error){
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            }
        }
    )
}