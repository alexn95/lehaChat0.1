
    var stompClient = null;
    var username = null;
    var currentRoom = null;
    var roomMessage = null;
    var countUser = 0;
    var listOfUser = [];
    var session = null;

    function logout(){
        document.getElementById("logout").submit();
        return false;
    }

    function deleteUser(room, count){
        document.getElementById("userDelete").value = listOfUser[count];
        document.getElementById("roomDelete").value = room;
        if (confirm("Are you sure?") == true) {
                        document.getElementById("deleteUser").submit();
                    }
        return false;
    }

    function addUser(){
        if(document.getElementById("addUser").value == "") return false;
        document.getElementById("addRoom").value = currentRoom;
        if (confirm("Are you sure?") == true) {
            document.getElementById("addUserForm").submit();
        }
        return false;
    }

    window.onload = function() {
        rooms.forEach(function(room) {
            $("#rooms").append('<tr><td><button id="' + room.roomName + '" class="btn btn-warning" type="submit" style="width: 110px">' + room.roomName + '</button></td></tr>');
        });

        rooms.forEach(function(room) {
             $( "#" + room.roomName ).click(function() { setConnect(room); });
        });

        if (remoteUser == null){
        username = 'guest';
            $("#userStatus").append('<a href="/login">Log in</a>');
        } else{
            username = remoteUser;
            $("#userStatus").append('<a href="#" onclick="logout();">Log out</a>');
        }

        $("#username").append(username);

        document.getElementById("addUserDiv").style.display ="none";

        setTimeout(deleteAlerts, 5000);

        if(reqRoom != null){
            rooms.forEach(function(room) {
                if(room.roomName == reqRoom){
                    connect(room);
                }
            });
        } else{
            connect(rooms[0]);
        }

        $("#sendForm").submit( function (e) {
            e.preventDefault();
        });

        $("#roomsForm").submit( function (e) {
            e.preventDefault();
        });

        $( "#send" ).click(function() { sendMessage(); });

        $("#addUserButton").click(function() {
            addUser();
        });
    }

    function setConnect(room) {
        document.getElementById("currentRoom").value = room.roomName;
        document.getElementById("connect").submit();
    }

    function connect(room) {
        currentRoom = room.roomName;

        if(reload == true){

            setConnect(room);
        }

        if (stompClient != null) {
            stompClient.disconnect();
        }

        var socket = new SockJS('/gs-guide-websocket');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {
            for (var i = 0; i != rooms.length; i++){
                $("#" + rooms[i].roomName).prop("disabled", false);
            }
            $("#" + room.roomName).prop("disabled", true);

            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/' + room.roomName, function (message) {
                showMessage(JSON.parse(message.body).content, JSON.parse(message.body).username, JSON.parse(message.body).date);
            });
        });

        $("#chat").html("");

            for (var i = 0; i != rooms.length; i++){
                if (rooms[i].roomName == room.roomName){
                    for (var j = 0; j != rooms[i].messages.length; j++){
                        var date = new Date(rooms[i].messages[j].date);
                        $("#chat").append('<tr><td><b>' + rooms[i].messages[j].username + '</b> ' +
                        date.getHours() + ':' + date.getMinutes() + ' : ' + rooms[i].messages[j].content + '</td></tr>');
                    }
                }
            }

            $("#users").html("");
            if (role){
                countUser = 0;
                listOfUser = [];
                room.users.forEach(function(user) {
                    if (user != username){
                        listOfUser[countUser] = user;
                        $("#users").append('<tr><td>' + user + '</td><td align="right"><button type="button"  onclick="deleteUser(currentRoom, '
                        + countUser + ')" class="btn btn-danger btn-xs" data-toggle="confirmation">Delete</button></td></tr>');
                        countUser++;
                    }
                });
            } else {
                room.users.forEach(function(user) {
                    $("#users").append('<tr><td>' + user + '</td></tr>');
                });
            }


            document.getElementById("scroll").scrollTop = 9999;

            if(role){
                document.getElementById("addUserDiv").style.display ="inline";
            }

            document.getElementById("currentRoom1").value = currentRoom;
            document.getElementById("currentRoom2").value = currentRoom;

        }


    function sendMessage() {
        if ($("#message").val() == "") { return; }
        stompClient.send('/app/' + currentRoom, {}, JSON.stringify({'content': $("#message").val(), 'username': username}));
        cashRooms[currentRoom].addMessage($("#message").val(), username, new Date());
        document.getElementById("message").value = "";
    }

    function showMessage(content, username, dateF) {
        var date = new Date(dateF);
        $("#chat").append('<tr><td><b>' + username + '</b> ' +
        date.getHours() + ':' + date.getMinutes() + ' : ' + content + '</td></tr>');
        document.getElementById("scroll").scrollTop = 9999;
    }

    function deleteAlerts(){
        if(document.getElementById('alerts') != null){
            document.getElementById('alerts').style.display ="none";
        }
    }