const csrfToken = $("meta[name='_csrf']").attr("content");
const csrfHeader = $("meta[name='_csrf_header']").attr("content");
const username = document.getElementById('login_user_email').value;
const senderId = document.getElementById('login_user_id').value;
const lastMessageId = document.getElementById('last_message_id').value;

let stompClient = null;
let subscription = null;
let lastPage = 0;
let totalPage = 0;
let chatRoomId = null;

let chatBox = document.getElementById('chatBox');

window.onload = function() {
    chatRoomId = document.getElementById('room_id').value;
    totalPage = document.getElementById('total_page').value;

    enterRoom(chatRoomId);

    let unReadMessageEl = document.getElementById('unReadMessagesContainer');
    if (unReadMessageEl.getAttribute("data-empty") == "true") {
        chatBox.scrollTop = chatBox.scrollHeight;
    } else {
        chatBox.scrollTop = unReadMessageEl.firstElementChild.offsetTop - chatBox.offsetTop;
    }
}

document.getElementById('message')
    .addEventListener('keydown', function(event) {
    if (event.key === 'Enter') {
        sendMessage();
    }
});

chatBox.addEventListener("scroll", function () {
    if (chatBox.scrollTop === 0 && lastPage < totalPage) {
        lastPage += 1;
        loadNewReadMessages();
    }
});

async function enterRoom() {
    if (stompClient === null || !stompClient.connected) {
        await connect();
    } else {
        await subscribeToRoom();
    }
}

async function connect() {
    let socket = new SockJS('/ws-connect');
    stompClient = Stomp.over(socket);

    let headers = {
        senderId: senderId,
        roomId: chatRoomId
    }

    stompClient.connect(headers, function (frame) {

        console.log('Connected: ' + frame);

        subscribeToRoom(chatRoomId);
    }, function(error) {
        alert('연결이 거부되었습니다.');
        window.location.href="/";
    });
}

async function subscribeToRoom() {
    if (stompClient !== null) {
        if (subscription !== null) {
            await subscription.unsubscribe();
        }

        subscription = stompClient.subscribe(`/topic/${chatRoomId}`, function (message) {
            const receivedMessage = JSON.parse(message.body);
            if (!receivedMessage.send) {
                updateLastMessage(receivedMessage.messageId);
                showMessage(receivedMessage, false);
            }
        });
    }
}

function moveChatRoom(roomId) {
    window.location.href = `/chat?roomId=${roomId}`;
}

function sendMessage() {
    let content = document.getElementById("message").value;
    if (content.trim() !== "") {
        let message = {
            senderId: senderId,
            senderName: username,
            roomId: chatRoomId,
            message: content
        };

        stompClient.send(`/publish/${chatRoomId}`, {}, JSON.stringify(message));
        document.getElementById("message").value = "";
    }
}

function showMessage(message, isRead) {
    let messageElement = document.createElement("div");
    messageElement.classList.add("alert", "w-auto", "mt-2");
    messageElement.setAttribute("data-message-id", message.messageId);

    if (senderId == message.senderId) {
        messageElement.classList.add("my-message");
    } else {
        messageElement.classList.add("other-message");
    }

    let messageHeader = document.createElement("div");
    messageHeader.classList.add("message-header");

    let profileImg = document.createElement("img");
    profileImg.setAttribute("src", message.profileUrl ? message.profileUrl : "/images/source/anonymous.png");
    profileImg.setAttribute("alt", "프로필 사진");
    profileImg.classList.add("profile-pic");

    let senderName = document.createElement("strong");
    senderName.textContent = message.senderName;

    messageHeader.appendChild(profileImg);
    messageHeader.appendChild(senderName);

    let messageTime = document.createElement("div");
    messageTime.classList.add("message-time");
    messageTime.textContent = message.sendDate;

    let messageContent = document.createElement("p");
    messageContent.textContent = message.message;

    messageElement.appendChild(messageHeader);
    messageElement.appendChild(messageTime);
    messageElement.appendChild(messageContent);

    if (!isRead) {
        locateMessagesAsc(messageElement);
    } else {
        locateMessagesDesc(messageElement);
    }
}

function locateMessagesAsc(messageElement) {
    chatBox.appendChild(messageElement);
    chatBox.scrollTop = chatBox.scrollHeight;
}

function locateMessagesDesc(messageElement) {
    chatBox.insertBefore(messageElement, chatBox.firstChild);
    chatBox.scrollTop = chatBox.scrollHeight;
}

function updateLastMessage(messageId) {
    $.ajax({
        url: `/api/chat/${chatRoomId}/last/${messageId}`,
        method: 'PUT',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function(response) {
            console.log(response.message);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            let errorResponse = JSON.parse(jqXHR.responseText);
            console.log('status: ', errorResponse.status, 'message: ', errorResponse.message);
        }
    })
}

function loadNewReadMessages() {
    let oldScrollHeight = chatBox.scrollHeight;
    let oldScrollTop = chatBox.scrollTop;

    $.ajax({
        url: `/api/chat/read/${chatRoomId}?page=${lastPage}&last_message_id=${lastMessageId}`,
        method: 'GET',
        dataType: 'json',
        success: function(response) {
            if (response.data != null) {
                response.data.slice()
                    .reverse()
                    .forEach(val => showMessage(val, true));
            } else {
                console.log('읽은 메시지가 없습니다.');
            }
            chatBox.scrollTop = oldScrollTop + (chatBox.scrollHeight - oldScrollHeight);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            let errorResponse = JSON.parse(jqXHR.responseText);
            console.log(errorResponse.message);
        }
    });
}

function kickMember(memberId) {
    $.ajax({
        url: `/api/chat-rooms/${chatRoomId}/members/${memberId}`,
        method: 'DELETE',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function(response) {
            alert(response.message);
            location.reload();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            let errorResponse = JSON.parse(jqXHR.responseText);
            console.log('status: ', errorResponse.status, 'message: ', errorResponse.message);
            alert(errorResponse.message);
        }
    })
}
