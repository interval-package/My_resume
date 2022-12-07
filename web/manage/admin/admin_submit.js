xhr = new XMLHttpRequest();

function get_base_root(){
    var curWwwPath=window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    var localhostPaht=curWwwPath.substring(0,pos);
    return localhostPaht;
}

function contest_update(target, row){
    var table = document.getElementById("contest_table");

    var tr = table.rows[parseInt(row)];
    if (login_verify){
        var json = 
`{
    "db": {"name": "contest_info"},
    "data": {
        "idx":          "${tr.cells[0].innerText}",
        "contest_name": "${tr.cells[1].innerText}",
        "contest_date": "${tr.cells[2].innerText}",
        "rank":         "${tr.cells[3].innerText}"
    },
    "login": {
        "account":       "${account}",
        "password":     "${password}"
    }
}`;
    
        xhr.open("POST", `${get_base_root()}/My_resume/db_injection?action=${target.value}`);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(json);
        alert(target.value + xhr.responseText);
    } else {
        alert("Login info out of time, please relogin");
        window.location = "../login";
    }
}

function experience_update(target, row){
    var table = document.getElementById("experience_table");

    var tr = table.rows[parseInt(row)];
    if (login_verify){
        var json = 
`{
    "db": {"name": "experience"},
    "data": {
        "idx":          "${tr.cells[0].innerText}",
        "exp_time":     "${tr.cells[1].innerText}",
        "details":       "${tr.cells[2].innerText}"
    },
    "login": {
        "account":      "${account}",
        "password":     "${password}"
    }
}`;
        xhr.open("POST", `${get_base_root()}/My_resume/db_injection?action=${target.value}`);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(json);
        alert(target.value + xhr.responseText);
    } else {
        alert("Login info out of time, please relogin");
        window.location = "../login";
    }
}

function project_update(target, row){
    var table = document.getElementById("project_table");
    var tr = table.rows[parseInt(row)];

    // console.log(target);

    if (login_verify){
        var json = 
`{
    "db": {"name": "projects"},
    "data": {
        "idx":                  "${tr.cells[0].innerText}",
        "project_name":         "${tr.cells[1].innerText}",
        "project_info":         "${tr.cells[2].innerText}",
        "project_time":         "${tr.cells[3].innerText}",
        "project_web":          "${tr.cells[4].innerText}",
        "project_pic_path":     "${tr.cells[5].getElementsByTagName("img")[0].src}"
    },
    "login": {
        "account":      "${account}",
        "password":     "${password}"
    }
}`;
        xhr.open("POST", `${get_base_root()}/My_resume/db_injection?action=${target.value}`);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(json);
        alert(target.value + xhr.responseText);
    } else {
        alert("Login info out of time, please relogin");
        window.location = "../login";
    }
}

function pic_input(event, target, row){
    console.log(event.target.files[0]);
    console.log(target);

    var table = document.getElementById("project_table");
    var tr = table.rows[parseInt(row)];

    var reader = new FileReader();
    reader.onload = (function (file) {
        return function (event) {
            // console.info(this.result); //这个就是base64的数据了
            tr.cells[5].getElementsByTagName("img")[0].src = this.result;
            // console.log(tr.cells[5].getElementsByTagName("img"));
        };
    })(event.target.files[0]);
    reader.readAsDataURL(event.target.files[0]);
}

function contest_add(target){
    if (login_verify){
        var json = 
`{
    "db": {"name": "contest_info"},
    "data": {
        "idx":          "${"1"}",
        "contest_name": "${"#"}",
        "contest_date": "${"#"}",
        "rank":         "${"#"}"
    },
    "login": {
        "account":       "${account}",
        "password":     "${password}"
    }
}`;
    
        xhr.open("POST", `${get_base_root()}/My_resume/db_injection?action=${"add"}`);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(json);
        alert("add" + xhr.responseText);
    } else {
        alert("Login info out of time, please relogin");
        window.location = "../login";
    }
}

function experience_add(target){
    if (login_verify){
        var json = 
`{
    "db": {"name": "experience"},
    "data": {
        "idx":          "${"1"}",
        "exp_time":     "${"#"}",
        "details":       "${"#"}"
    },
    "login": {
        "account":      "${account}",
        "password":     "${password}"
    }
}`;
        xhr.open("POST", `${get_base_root()}/My_resume/db_injection?action=${"add"}`);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(json);
        alert("add" + xhr.responseText);
    } else {
        alert("Login info out of time, please relogin");
        window.location = "../login";
    }
}

function project_add(target){
    if (login_verify){
        var json = 
`{
    "db": {"name": "projects"},
    "data": {
        "idx":                  "${-1}",
        "project_name":         "${"#"}",
        "project_info":         "${"#"}",
        "project_time":         "${"#"}",
        "project_web":          "${"#"}",
        "project_pic_path":     "${"#"}"
    },
    "login": {
        "account":      "${account}",
        "password":     "${password}"
    }
}`;
        xhr.open("POST", `${get_base_root()}/My_resume/db_injection?action=${"add"}`);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(json);
        alert("add" + xhr.responseText);
    } else {
        alert("Login info out of time, please relogin");
        window.location = "../login";
    }
}