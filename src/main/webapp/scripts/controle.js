/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var htmlMedicao = [
    '<div> Data-hora: <span class="classTexto1">', 
    ' </span><br> Temperatura: <span class="classTexto1">',
    ' ºC </span><br>Umidade: <span id="umidade" class="classTexto1">',
    ' %</span><br>Ponto de orvalho: <span id="orvalho" class="classTexto1">',
    ' ºC</span><br>Pressão atmosférica: <span id="pressao" class="classTexto1">',
    ' hPa</span><br>Taxa de precipitação: <span id="precipitacao" class="classTexto1">',
    ' mm/h</span><br>Precipitação acumulada: <span id="precipacumul" class="classTexto1">',
    ' mm</span><br>(últimas 24h)<br>Velocidade do Vento: <span id="velvento" class="classTexto1">',
    ' km/h</span><br>Direção do vento: <span id="dirvento" class="classTexto1">',
    '</span><br></div>'
]

function doAjax(tipo, data, callback) {
    var url = "http://localhost:8080/TempoClimaNet/ajax?type=" + tipo + "&date=" + data;
    var ajaxRequest = new XMLHttpRequest();
    ajaxRequest.open("GET", url);
    ajaxRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    
    ajaxRequest.onreadystatechange = function() {
        if(ajaxRequest.readyState===4 && ajaxRequest.status===200){
            var respostaJSON = JSON.parse(ajaxRequest.responseText);
            if(!jQuery.isEmptyObject(respostaJSON)) callback(respostaJSON);
        }
    };
    
    ajaxRequest.send(null);
}

function buscar(data) {
    var respostaJson = doAjax("lookup", data);
}

function proximaMedicao() {
    var respostaJson = doAjax("scrollrightMed", $('#datamed').val(), proximaMedicaoAfter);
}

function proximaMedicaoAfter(respostaJson) {
    if (respostaJson.datahoraautom !== "00/00/00 00:00:00")
    {
        $('#datamed').val(respostaJson.datahoraautom);
        var html = htmlMedicao[0] + respostaJson.datahoraautom +
                htmlMedicao[1] + respostaJson.temperatura.toFixed(
                    Math.max(1, (respostaJson.temperatura.toString().split('.')[1] || []).length)) +
                htmlMedicao[2] + respostaJson.umidade.toFixed(
                    Math.max(1, (respostaJson.umidade.toString().split('.')[1] || []).length)) +
                htmlMedicao[3] + respostaJson.orvalho.toFixed(
                    Math.max(1, (respostaJson.orvalho.toString().split('.')[1] || []).length)) + 
                htmlMedicao[4] + respostaJson.pressao.toFixed(
                    Math.max(1, (respostaJson.pressao.toString().split('.')[1] || []).length)) + 
                htmlMedicao[5] + respostaJson.precipitacao.toFixed(
                    Math.max(1, (respostaJson.precipitacao.toString().split('.')[1] || []).length)) +
                htmlMedicao[6] + respostaJson.precipacumul.toFixed(
                    Math.max(1, (respostaJson.precipacumul.toString().split('.')[1] || []).length)) +
                htmlMedicao[7] + respostaJson.velvento.toFixed(
                    Math.max(1, (respostaJson.velvento.toString().split('.')[1] || []).length)) +
                htmlMedicao[8] + respostaJson.dirvento.toFixed(
                    Math.max(1, (respostaJson.dirvento.toString().split('.')[1] || []).length)) +
                htmlMedicao[9];
                        
        var currentSlide = $('.medidasAutomaticas').slick('slickCurrentSlide');
        $('.medidasAutomaticas')
                .slick('slickAdd', html, currentSlide)
                .one('afterChange', function(event, slick, currentSlide) {
                    $('.medidasAutomaticas')
                        .slick('slickRemove', currentSlide, true);});
        $('.medidasAutomaticas').slick('slickPrev');
    }
}

function anteriorMedicao() {
    var respostaJson = doAjax("scrollleftMed", $('#datamed').val(), anteriorMedicaoAfter);
}

function anteriorMedicaoAfter(respostaJson) {
    if (respostaJson.datahoraautom !== "00/00/00 00:00:00")
    {
        $('#datamed').val(respostaJson.datahoraautom);
        var html = htmlMedicao[0] + respostaJson.datahoraautom +
                htmlMedicao[1] + respostaJson.temperatura.toFixed(
                    Math.max(1, (respostaJson.temperatura.toString().split('.')[1] || []).length)) +
                htmlMedicao[2] + respostaJson.umidade.toFixed(
                    Math.max(1, (respostaJson.umidade.toString().split('.')[1] || []).length)) +
                htmlMedicao[3] + respostaJson.orvalho.toFixed(
                    Math.max(1, (respostaJson.orvalho.toString().split('.')[1] || []).length)) + 
                htmlMedicao[4] + respostaJson.pressao.toFixed(
                    Math.max(1, (respostaJson.pressao.toString().split('.')[1] || []).length)) + 
                htmlMedicao[5] + respostaJson.precipitacao.toFixed(
                    Math.max(1, (respostaJson.precipitacao.toString().split('.')[1] || []).length)) +
                htmlMedicao[6] + respostaJson.precipacumul.toFixed(
                    Math.max(1, (respostaJson.precipacumul.toString().split('.')[1] || []).length)) +
                htmlMedicao[7] + respostaJson.velvento.toFixed(
                    Math.max(1, (respostaJson.velvento.toString().split('.')[1] || []).length)) +
                htmlMedicao[8] + respostaJson.dirvento.toFixed(
                    Math.max(1, (respostaJson.dirvento.toString().split('.')[1] || []).length)) +
                htmlMedicao[9];

        var currentSlide = $('.medidasAutomaticas').slick('slickCurrentSlide');
        $('.medidasAutomaticas')
                .slick('slickAdd', html, currentSlide)
                .one('afterChange', function(event, slick, currentSlide) {
                    $('.medidasAutomaticas')
                        .slick('slickRemove', currentSlide, true);});
        $('.medidasAutomaticas').slick('slickNext');
    }
}

function proximaObservacao(data) {
    
}

function anteriorObservacao(data) {
    
}

$(document).ready(function(){
  $('#divMedsJsButtons')
          .html('<input type="button" value="<" onclick="anteriorMedicao()"/>\n\
                 <input type="button" value=">" onclick="proximaMedicao()"/>');
  $('.medidasAutomaticas').slick({
      slidesToShow: 1,
      waitForAnimate: true,
      arrows: false
  });
});