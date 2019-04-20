var stompClient = null;


function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#tableListNotification").show();
    } else {
        $("#tableListNotification").hide();
    }
    $("#textNotification").html("");
}

function connect() {
    socket = new SockJS('/websocket-example');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/message', function (mess) {
            showMessage(JSON.parse(mess.body).message);
        });
    });
}


function sendMessage() {
    stompClient.send("/app/message",{}, JSON.stringify({'message': '1'}));
}

function showMessage(message) {
    $("#textNotification").append("<tr><td>" + message + "</td></tr>");
}
