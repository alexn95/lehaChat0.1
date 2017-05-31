var username = null;
    function logout(){
        document.getElementById("logout").submit();
        return false;
    }

     function deleteAlerts(){
        document.getElementById('alerts').style.display ="none";
    }
    window.onload = function() {
        if (remoteUser == null){
        username = 'guest';
            $("#userStatus").append('<a href="/login">Log in</a>');
        } else{
            username = remoteUser;
            $("#userStatus").append('<a href="#" onclick="logout();">Log out</a>');
        }

        $("#username").append(username);

        setTimeout(deleteAlerts, 5000);
    }