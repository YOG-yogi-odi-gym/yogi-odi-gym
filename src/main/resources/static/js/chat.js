let username = document.getElementById('login_user_email').value;
let senderId = document.getElementById('login_user_id').value;
let senderName = document.getElementById('login_user_name').value;

let stompClient = null;
let subscription = null;
let lastPage = 0;
let totalPage = 0;
let chatRoomId = null;

let chatBox = document.getElementById('chatBox');

window.onload = function() {
    chatRoomId = document.getElementById('room_id').value;
    totalPage = document.getElementById('total_page').value;

    console.log('Total Page: ', totalPage);

    enterRoom(chatRoomId);

    let unReadMessagesEl = document.getElementById('unread_messages');
    if (chatBox && unReadMessagesEl) {
        console.log('스크롤 위치를 변경합니다.');
        chatBox.scrollTop = unReadMessagesEl.offsetTop - chatBox.offsetTop;
    }
}

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
    stompClient.connect({}, function (frame) {

        console.log('Connected: ' + frame);

        subscribeToRoom(chatRoomId);
    });
}

async function subscribeToRoom() {
    if (stompClient !== null) {
        if (subscription !== null) {
            await subscription.unsubscribe();
        }

        subscription = stompClient.subscribe(`/topic/${chatRoomId}`, function (message) {
            const receivedMessage = JSON.parse(message.body);

            console.log('수신한 메시지', JSON.parse(message.body));

            if (receivedMessage.type !== 'SEND') {
                console.log('수신한 메시지입니다.');
                showMessage(receivedMessage, false);  // TODO 본인 메시지일경우 오른쪽으로 배치, 전송자 표시
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
    messageElement.classList.add("alert", "alert-secondary", "mt-2");
    messageElement.textContent = message.messageId + ' : ' + message.message;
    messageElement.setAttribute("data-message-id", message.messageId);

    if (!isRead) {
        updateLastMessage(message.messageId);
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
        success: function(response) {
            if (response.status === 200) {
                console.log(response.message);
            }
        },
        error: function(data, status, err) {
            console.log(data.message);
        }
    })
}

// TODO view로 구현할 경우 필요없어짐
function loadUnReadMessages() {
    $.ajax({
        url: `/api/chat/unread/${roomId}`,
        method: 'GET',
        dataType: 'json',
        success: function(response) {
            let firstMessageId;
            if (response.data != null) {
                console.log('안 읽은 메시지 목록 조회');

                response.data.forEach((val, idx) => {
                    if (idx === 0) {
                        firstMessageId = val.messageId;
                    }
                    showMessage(val, true);
                });
            } else {
                console.log('안읽은 메시지가 없습니다.');
            }

            let targetEl = $(`[data-message-id="${firstMessageId}"]`);
            let chatBox = $(".chat-box");

            if (targetEl.length > 0) {
                let targetPosition = targetEl.position().top + chatBox.scrollTop();
                let scrollTo = targetPosition - (chatBox.height() / 2) + (targetEl.height() / 2);

                chatBox.scrollTop(scrollTo);
            }
        },
        error: function(data, status, err) {
            console.log('안읽은 메시지를 조회하는 데에 실패하였습니다.');
        }
    });
}

// TODO view로 구현하더라도 필요함
function loadReadMessages() {
    $.ajax({
        url: `/api/chat/read/${roomId}?page=${lastPage}`,
        method: 'GET',
        dataType: 'json',
        success: function(response) {
            if (response.data != null) {
                console.log('읽은 메시지 목록 조회, 페이지: ', lastPage);
                console.log(response.data);

                response.data.forEach((val) => {
                    showMessage(val);
                });
            } else {
                console.log('읽은 메시지가 없습니다.');
            }
        },
        error: function(data, status, err) {
            console.log('읽은 메시지를 조회하는 데에 실패하였습니다.');
        }
    });
}

function loadNewReadMessages() {
    let oldScrollHeight = chatBox.scrollHeight;
    let oldScrollTop = chatBox.scrollTop;

    $.ajax({
        url: `/api/chat/read/${chatRoomId}?page=${lastPage}`,
        method: 'GET',
        dataType: 'json',
        success: function(response) {
            if (response.data != null) {
                console.log('새로 읽은 메시지 목록 업데이트, 페이지: ', lastPage);
                console.log(response.data);

                response.data.slice()
                    .reverse()
                    .forEach(val => showMessage(val, true));
            } else {
                console.log('읽은 메시지가 없습니다.');
            }
            chatBox.scrollTop = oldScrollTop + (chatBox.scrollHeight - oldScrollHeight);
        },
        error: function(data, status, err) {
            console.log('읽은 메시지를 조회하는 데에 실패하였습니다.');
        }
    });
}

// TODO 필요 없어짐
function loadChatRooms() {
    let chatRoomsEl = $('#chat_room .content');

    $.ajax({
        url: '/api/chat-rooms',
        method: 'GET',
        dataType: 'json',
        success: function(response) {
            console.log(response.data);
            response.data.forEach((data) => {
               // let chatRoom = '<button onclick="enterRoom(this)" data-room-id="' + data.roomId + '" class="text-bg-light p-3" value="' + data.roomId + '">' + data.lessonTitle + '</button>';
               let chatRoom = '<button onclick="nextRoom(this)" data-room-id="' + data.roomId + '" class="text-bg-light p-3" value="' + data.roomId + '">' + data.lessonTitle + '</button>';
               chatRoomsEl.append(chatRoom);
            });
        },
        error: function(data, status, err) {
            console.log('채팅방 목록을 조회하는 데에 실패하였습니다.');
        }
    });
}

function countChatRoomTotalPages() {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: `/api/chat/total/${roomId}`,
            method: 'GET',
            dataType: 'json',
            success: function (response) {
                console.log('전체 페이지 수: ', response.data);
                totalPage = response.data - 1;
                console.log('Total Page: ', totalPage);
                resolve(response.data);
            },
            error: function (data, status, err) {
                reject(err);
            }
        });
    });
}