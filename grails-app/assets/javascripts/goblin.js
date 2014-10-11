
function hideRow(id){
	document.getElementById(id).style.visibility="collapse";
}

function setTextOfElement(id, newText){
    var element = document.getElementById(id);
    if(!element){
        console.log("Failed to set text '"+newText+"' on missing element: #"+id);
        return;
    }
    //console.log("set text of #"+id+" to "+newText);
    element.innerHTML = newText;
}
