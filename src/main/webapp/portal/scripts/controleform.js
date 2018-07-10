function submit() {
    $("#botaoSubmit").prop("disabled", true);
    
    //var url = "http://localhost:8080/TempoClimaNet/ajax?type=" + tipo + "&date=" + data;
    var content = JSON.stringify({
        "datahoraObs":$("#datahoraObs").val(),
        "altOndas":$("#altOndas").val(),
        "tempAgua":$("#tempAgua").val(),
        "bandeira":translateColor($("#bandeira").spectrum("get").toName())
    });
    var ajaxRequest = new XMLHttpRequest();
    
    //ajaxRequest.open("GET", url);
    //ajaxRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    ajaxRequest.open("POST", "portal");
    ajaxRequest.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    
    ajaxRequest.onreadystatechange = function() {
        if(ajaxRequest.readyState===4 && ajaxRequest.status===200){
            var respostaJSON = JSON.parse(ajaxRequest.responseText);
            $("#botaoSubmit").prop("disabled", false);
            showResult(respostaJSON);
        }
    };
    
    //ajaxRequest.send(null);
    ajaxRequest.send(content);
}

function translateColor(name) {
   switch (name){
       case "green":
           return "verd";
       case "red":
           return "verm";
       case "yellow":
           return "amar";
       case "blue":
           return "azul";
       case "black":
           return "pret";
       case "purple":
           return "roxo";
       default:
           return "";
   }
}

function showResult(result) {
    var text;
    switch(result.status) {
        case "succeeded":
            text = "Observação submetida com sucesso.";
            color = "#62c462";
            break;
        case "failed":
            text = "Falha ao submeter observação (" + result.error + ")";
            color = "#EE5757";
            break;
    }
    $("#displayResultado")
            .text(text)
            .css("background-color", color)
            .css("visibility", "visible");
}

function updateCurrentTime() {
    $("#datahoraObs").val(getCurrentDateString());
}

$(document).ready(function(){
    $("#bandeira").spectrum({
        color: "green",    
        showPaletteOnly: true,
        showPalette: true,
        hideAfterPaletteSelect:true,
        palette: [
            ["green", "yellow", "red", "black", "blue", "purple" ]
        ]
    }).on("show.spectrum", (e) => {
       $("span[title='rgb(0, 128, 0)']").attr("title", "Verde");
       $("span[title='rgb(255, 255, 0)']").attr("title", "Amarela");
       $("span[title='rgb(255, 0, 0)']").attr("title", "Vermelha");
       $("span[title='rgb(0, 0, 0)']").attr("title", "Preta");
       $("span[title='rgb(0, 0, 255)']").attr("title", "Azul");
       $("span[title='rgb(128, 0, 128)']").attr("title", "Roxa");
    });
    
    setInterval(updateCurrentTime, 1000);
    
    updateCurrentTime();
});
