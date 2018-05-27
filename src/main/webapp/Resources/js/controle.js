/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function doAjax(tipo, data) {
    var url = "http://localhost:8080/TempoClimaNet/ajax?type=" + tipo + "&date=" + data;
    var ajaxRequest = new XMLHttpRequest();
    ajaxRequest.open("GET", url);
    ajaxRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    
    ajaxRequest.onreadystatechange = function() {
        if(ajaxRequest.readyState===4 && ajaxRequest.status===200){
            var respostaJSON = JSON.parse(ajaxRequest.responseText);
        }
    };
    
    ajaxRequest.send(null);
    
    return respostaJSON;
}

function buscar(data) {
    var respostaJson = doAjax("lookup", data);
}

function proximaMedicao(data) {
    
}

function anteriorMedicao(data) {
    
}

function proximaObservacao(data) {
    
}

function anteriorObservacao(data) {
    
}