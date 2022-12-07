xhr = new XMLHttpRequest();
var password = getCookie("password");
var account = getCookie("account");

window.onload = function (){
    // 判别是否登录，是否有cookie
    if (!contains_cookie()){
        alert("Do not login, cookie missed.")
        window.location = "../login"
    }else if (login_verify()){
        alert("Welcome!")
    }else {
        alert("Invalid login info.")
        window.location = "../login"
    }
}

function getCookie(cname){
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++)
    {
        var c = ca[i].trim();
        if (c.indexOf(name)===0) return c.substring(name.length,c.length);
    }
    return "";
}

function contains_cookie(){
    // alert(`${password} ${account}`);
    return account !== "" && password !== "";
}

function login_verify(){
    xhr.open("GET", `/My_resume/login?account=${account}&password=${password}`, false);
    xhr.send();
    let res = xhr.responseText;
    res = "res = " + res;
    eval(res);
    return res["is_login"] === "true";
}
