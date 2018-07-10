function getCurrentDateString() {
    var currentdate = new Date(); 
    var datetime = currentdate.getDate() + "/"
            + (currentdate.getMonth()+1)  + "/" 
            + currentdate.getFullYear() + " "  
            + (currentdate.getHours().toString().length == 2 ? currentdate.getHours() : "0" + currentdate.getHours() ) + ":"  
            + (currentdate.getMinutes().toString().length == 2 ? currentdate.getMinutes() : "0" + currentdate.getMinutes() ) + ":" 
            + (currentdate.getSeconds().toString().length == 2 ? currentdate.getSeconds() : "0" + currentdate.getSeconds() );
    return datetime;
}

