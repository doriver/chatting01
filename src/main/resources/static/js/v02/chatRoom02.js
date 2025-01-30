

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/chatRoom-v02-websocket'
});

stompClient.onConnect = (frame) => {    
    setConnected(true);
    console.log('Connected: ' + frame);

    var loginUser = $("#userName").val();
    var roomId = $("#roomId").val();
 
    stompClient.subscribe('/chatRoom/' + roomId + '/message', (messageDTO) => {
        const message = JSON.parse(messageDTO.body);
        var isMine = 0;
        if (loginUser === message.sender) {
            isMine = 1;
        }
        showMessage(message.sender, message.content, message.sendedAt, isMine);
    });

    stompClient.subscribe('/chatRoom/' + roomId + '/door', (message) => {
        if (message.body === "DISCONNECT") {
            disconnect();
            alert("채팅방이 종료 되었습니다.");
        } else {
            // 출입 표시
            showDoor(message.body);
        }
    });

    stompClient.subscribe('/chatRoom/' + roomId + '/participants', (participantDTO) => {
        const participant = JSON.parse(participantDTO.body);
        updateParticipants(participant.chatterId, participant.chatterName, participant.access);
    });
};

function updateParticipants(chatterId, chatterName, access) {
    const chattersTbody = document.getElementById("chatters");
    if (access === 1) { // 입장
        const newChatter = document.createElement("tr");
        newChatter.className = "user-" + chatterId;
        newChatter.innerHTML =`
            <td style="text-align: center;"> 참석자 이미지 </td>
            <td>${chatterName}</td>
            <td style="text-align: right;">기타등등</td>
        `;
        chattersTbody.appendChild(newChatter);
    } else if (access === 0) { // 퇴장
        var exitChatter = chattersTbody.querySelector(".user-" + chatterId);
        exitChatter.remove();
    } else {
        alert("참석자 업데이트 실패");
    }
}

function sendMessage() {
    var params = {
        'sender': $("#userName").val()
        , 'senderId': $("#userId").val()
        , 'message': $("#messageInput").val()  
    }
    var roomId = $("#roomId").val();
    stompClient.publish({
        destination: "/chatApp/" + roomId,
        body: JSON.stringify(params)
    });
}

function showDoor(message) {
    const doorDiv = document.createElement("div");
    doorDiv.className = "text-center mt-2 mb-3";
    doorDiv.style.fontSize = "0.7rem";
    doorDiv.style.backgroundColor = "lightgrey";
    doorDiv.textContent = message;
    chatContainer.appendChild(doorDiv);
}

function showMessage(sender, content, sendedAt, isMine) {
    const chatContainer = document.getElementById("chatContainer");

    const chatItemDiv = document.createElement("div");

    if (isMine === 0) {
        // 받은 메시지
        chatItemDiv.innerHTML = `
            <div>
                <img src=""  width="28" style="border-radius: 30%">
                <span style="font-size: 0.6rem;">${sender}</span>
            </div>
            <div class="message received">
                <p>${content}</p>
                <span style="font-size: 0.6rem;">${sendedAt}</span>
            </div>
        `;
    } else {
        // 보낸 메시지
        chatItemDiv.innerHTML = `
            <div class="message sent">
                <span style="font-size: 0.6rem; margin-right: 3px;">
                    ${sendedAt}
                </span>
                <p>${content}</p>
            </div>
        `;
    }
    chatContainer.appendChild(chatItemDiv);

    // 스크롤을 가장 아래로 내리기
    chatContainer.scrollTop = chatContainer.scrollHeight;
}

function connect() {
    stompClient.activate();
}

async function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function exitRoom() {
    var roomId = $("#roomId").val();
    var userId = $("#userId").val();

    $.ajax({
        type:"PATCH",
        url:"/v02/api/rooms/" + roomId + "/" + userId,
        success:async function(response) {
            await disconnect();
            location.href="/v02/list";
        },
        error:function(xhr) {
            let response = xhr.responseJSON;
            console.log(response);
            alert("단톡방 나가기 실패 \n" + response.message);
        }
    });
}

function endRoom() {
    var roomId = $("#roomId").val();
   
    var userId = $("#userId").val();
    var roomMentorId = $("#roomMentorId").val();

    if (userId === roomMentorId) {
        $.ajax({
            type:"PATCH",
            url:"/v02/api/rooms/" + roomId ,
            success:async function(response) {
                await disconnect(); // spring에서 소켓연결 종료하는 쪽으로 바꿔야함
                alert("채팅방이 종료 되었습니다.");
                // location.href="/v02/list";
            },
            error:function(xhr) {
                let response = xhr.responseJSON;
                console.log(response);
                alert("단톡방 종료 실패 \n" + response.message);
            }
        });

    }

}


function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}


stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};



$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendMessage());
});



function handleEnterKey(event) {
	if (event.key === 'Enter') {
		event.preventDefault();     // 줄바꿈 방지(기본 엔터 키 동작 방지)
		sendMessage();
	}
}


async function fetchChatItems() {
    const userId = $('#userId').val();
    const recipientId  = $('#recipientId').val();
    try {
        const response = await fetch(`/chatting/getChatItems?userId=${userId}&recipientId=${recipientId}`);
        if (response.ok) {
            setTimeout(async() => {
                const chatItemsByDate = await response.json();
                updateChatContainer(chatItemsByDate);
            }, 100 );
        }
    } catch (error) {
        console.error("Failed to fetch messages:", error);
    }
}

function updateChatContainer(chatItemsByDate) {
    const chatContainer = document.getElementById("chatContainer");
    chatContainer.innerHTML = ""; // 기존 메시지 초기화

    for (const [date, chatItems] of Object.entries(chatItemsByDate)) { // map받았을때 이렇게
        // 날짜 표시
        const dateDiv = document.createElement("div");
        dateDiv.className = "text-center mt-2 mb-3";
        dateDiv.style.fontSize = "0.7rem";
        dateDiv.style.backgroundColor = "lightgrey";
        dateDiv.textContent = date;
        chatContainer.appendChild(dateDiv);

        // 메시지 표시
        chatItems.forEach(chat => {
            const chatItemDiv = document.createElement("div");

            if (chat.isMine === 0) {
                // 받은 메시지
                chatItemDiv.innerHTML = `
                    <div>
                        <img src=""  width="28" style="border-radius: 30%">
                        <span style="font-size: 0.6rem;">${chat.friendUname}</span>
                    </div>
                    <div class="message received">
                        <p>${chat.message}</p>
                        <span style="font-size: 0.6rem;">${chat.timeStr}</span>
                    </div>
                `;
            } else {
                // 보낸 메시지
                chatItemDiv.innerHTML = `
                    <div class="message sent">
                        <span style="font-size: 0.6rem; margin-right: 3px;">
                            ${chat.timeStr}
                        </span>
                        <p>${chat.message}</p>
                    </div>
                `;
            }
            chatContainer.appendChild(chatItemDiv);
        });
    }
    // input tag
    const inputTag = document.createElement("input");
    inputTag.className = "form-control mt-2";
    inputTag.type = "text";
    inputTag.id = "messageInput";
    inputTag.placeholder = "메시지 입력";
    inputTag.addEventListener("keydown", handleEnterKey);
    chatContainer.appendChild(inputTag);

    // 스크롤을 가장 아래로 내리기
    chatContainer.scrollTop = chatContainer.scrollHeight;
}

