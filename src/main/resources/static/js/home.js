var username = null;
    function logout(){
        document.getElementById("logout").submit();
        return false;
    }

    function deleteAlerts(){
    if(document.getElementById('alerts') != null)
        document.getElementById('alerts').style.display ="none";
    }

    window.onload = function() {
        if (remoteUser == null){
            document.getElementById('user').style.display ="none";
            document.getElementById('guest').style.display ="block";
            username = 'guest';
            $("#userStatus").append('<a href="/login">Log in</a>');
        } else{
            document.getElementById('guest').style.display ="none";
            username = remoteUser;
            $("#userStatus").append('<a href="#" onclick="logout();">Log out</a>');
        }

        $("#username").append(username);


        setTimeout(deleteAlerts, 5000);

    }