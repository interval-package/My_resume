xhr = new XMLHttpRequest();


window.onload = function(){
    // render_contest();
    // render_project();
}

function render_contest(){
    xhr.open("GET","./base_info?content=contest",false);
    xhr.send();
    console.log(xhr.responseText);
}

function render_project(){
    xhr.open("GET","./base_info?content=projects",false);
    xhr.send();
    console.log(xhr.responseText);
    var projects_info = eval(xhr.responseText);

    var tar  = document.getElementById("work_content_add");

    // var const_path = "images\\works\\uucsr.jpg"

    for(let i in projects_info){
        if(i >= 3)break;
        let cur_project = projects_info[i];
        let temp;
        if(i % 3 == 0){
            temp = "frist "
        }else {
            temp = "";
        }
        tar.innerText += `
        <div class="works-item ${temp}fade fade${parseInt(i)+1}">
            <a href="${cur_project["project_web"]}" target="_blank">
                <img src="${cur_project["project_pic_path"]}" alt="" width="300" height="180">
                <div class="work-info">
                    <h2>${cur_project["project_name"]}</h2>
                    <p><strong>时间</strong>${cur_project["project_time"]}<br>
                    <strong>工作详情</strong>${cur_project["project_info"]}</p>
                </div>
            </a>
        </div>`
        
    }
}

