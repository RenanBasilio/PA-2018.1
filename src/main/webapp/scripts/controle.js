/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Faz um pedido Ajax ao servidor. Utiliza o controlador /ajax? com os 
 * parametros type e date, na convenção:
 * /TempoClimaNet/ajax?type=${tipo}&date=${data}
 * 
 * Tipos de pedido:
 *      lookup, scrollleftMed, scrollrightMed, scrollleftObs, scrollrightObs
 * Formato da string de data:
 *      dd/MM/yyyy HH:mm:ss
 * 
 * @param {String} tipo O tipo do pedido.
 * @param {String} data A data sendo relacionada ao pedido.
 * @param {function} callback Um método a ser chamado quando o pedido retornar.
 */
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

/**
 * Converte um valor numérico em uma string com n ou mais casas decimais.
 * @param {Numer} value O valor a ser convertido.
 * @param {Integer} deciPlaces O número mínimo de casas decimais.
 * @return {String} A string convertida.
 */
function getDecimalString(value, deciPlaces) {
    return value.toFixed(deciPlaces);
}

/**
 * Constroi o html do display de medição a partir de sua representação json.
 * @param {Object} jsonMedicao O objeto json que representa a medição.
 * @return {String} O html referente ao display de medição.
 */
function buildHtmlMedicao(jsonMedicao) {
    var html = 
        '<div>' +
            'Data-hora: <span class="classTexto1">' + 
            jsonMedicao.datahoraautom + ' </span><br>' +
            'Temperatura: <span class="classTexto1">' +
            getDecimalString(jsonMedicao.temperatura) + ' ºC </span><br>' +
            'Umidade: <span id="umidade" class="classTexto1">' +
            getDecimalString(jsonMedicao.umidade) + ' %</span><br>' +
            'Ponto de orvalho: <span id="orvalho" class="classTexto1">' + 
            getDecimalString(jsonMedicao.orvalho) + ' ºC</span><br>' +
            'Pressão atmosférica: <span id="pressao" class="classTexto1">' +
            getDecimalString(jsonMedicao.pressao) + ' hPa</span><br>' +
            'Taxa de precipitação: <span id="precipitacao" class="classTexto1">' +
            getDecimalString(jsonMedicao.precipitacao) + ' mm/h</span><br>'+
            'Precipitação acumulada: <span id="precipacumul" class="classTexto1">' +
            getDecimalString(jsonMedicao.precipacumul) + ' mm</span><br>(últimas 24h)<br>' + 
            'Velocidade do Vento: <span id="velvento" class="classTexto1">' +
            getDecimalString(jsonMedicao.velvento) + ' km/h</span><br>' + 
            'Direção do vento: <span id="dirvento" class="classTexto1">' +
            getDecimalString(jsonMedicao.dirvento) + '</span><br>' + 
        '</div>';

    return html;
}

/**
 * Constroi o html do display de observação a partir de sua representação json.
 * @param {Object} jsonObservacao O objeto json que representa a observação.
 * @return {String} O html referente ao display de observação.
 */
function buildHtmlObservacao(jsonObservacao) {
    var html =
        '<div>' +
            'Data-hora: ' +
            '<span id="datahoraobs" class="classTexto1">' + 
                jsonObservacao.datahoraobs + '</span><br>' +
            'Altura das ondas: ' +
            '<span id="altondas" class="classTexto1">' +
                getDecimalString(jsonObservacao.altondas, 1) +
                ' m</span><br>' +
            'Temperatura da água: ' +
            '<span id="tempagua" class="classTexto1">' +
                getDecimalString(jsonObservacao.tempagua, 1) +
                ' ºC</span><br>' +
            'Bandeira: ' +
            '<span id="bandsalvavidas" style="color:' + 
            jsonObservacao.bandeira.cor + '; font-weight:bold;">' +
                '<div class="tooltip">' +
                    jsonObservacao.bandeira.nome +
                    '<span class="tooltiptext">' +
                        jsonObservacao.bandeira.desc +
                    '</span>' +
                '</div>' + 
            '</span>' +
            '<br><br><br>'
        '</div>';
    return html;
}

/**
 * Utiliza um carrousel do slick para criar uma animação de scroll para a direita.
 * @param {String} carrouselId O id do carrousel a ser utilizado.
 * @param {String} html O html a ser inserido no novo slide.  
 */
function slickScrollRight(carrouselId, html) {
    var carrousel = $(carrouselId);
    var currentSlide = carrousel.slick('slickCurrentSlide');
    carrousel
            .slick('slickAdd', html, currentSlide)
            .one('afterChange', function(event, slick, currentSlide) {
                carrousel
                    .slick('slickRemove', currentSlide, true);});
    carrousel.slick('slickPrev');    
}

/**
 * Utiliza um carrousel do slick para criar uma animação de scroll para a esquerda.
 * @param {String} carrouselId O id do carrousel a ser utilizado.
 * @param {String} html O html a ser inserido no novo slide.  
 */
function slickScrollLeft(carrouselId, html) {
    var carrousel = $(carrouselId);
    var currentSlide = carrousel.slick('slickCurrentSlide');
    carrousel
            .slick('slickAdd', html, currentSlide)
            .one('afterChange', function(event, slick, currentSlide) {
                carrousel
                    .slick('slickRemove', currentSlide, true);});
    carrousel.slick('slickNext');
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

        var html = buildHtmlMedicao(respostaJson);
        
        slickScrollLeft('.displayMedicao', html);
    }
}

function anteriorMedicao() {
    var respostaJson = doAjax("scrollleftMed", $('#datamed').val(), anteriorMedicaoAfter);
}

function anteriorMedicaoAfter(respostaJson) {
    if (respostaJson.datahoraautom !== "00/00/00 00:00:00")
    {
        $('#datamed').val(respostaJson.datahoraautom);
        
        var html = buildHtmlMedicao(respostaJson);
        
        slickScrollRight('.displayMedicao', html);
    }
}

function proximaObservacao() {
    var respostaJson = doAjax("scrollrightObs", $('#dataobs').val(), proximaObservacaoAfter);
}

function proximaObservacaoAfter(respostaJson) {
    if (respostaJson.datahoraobs !== "00/00/00 00:00:00")
    {
        $('#dataobs').val(respostaJson.datahoraobs);
        
        var html = buildHtmlObservacao(respostaJson);
        
        slickScrollLeft('.displayObservacao', html);
    }
}

function anteriorObservacao() {
    var respostaJson = doAjax("scrollleftObs", $('#dataobs').val(), anteriorObservacaoAfter);
}

function anteriorObservacaoAfter(respostaJson) {
    if (respostaJson.datahoraobs !== "00/00/00 00:00:00")
    {
        $('#dataobs').val(respostaJson.datahoraobs);
        
        var html = buildHtmlObservacao(respostaJson);
        
        slickScrollRight('.displayObservacao', html);
    }
}

$(document).ready(function(){
  $('#scrollformControlsJsMedicao')
          .html('<input type="button" value="<" onclick="anteriorMedicao()"/>\n\
                 <input type="button" value=">" onclick="proximaMedicao()"/>');
  $('#scrollformControlsJsObservacao')
          .html('<input type="button" value="<" onclick="anteriorObservacao()"/>\n\
                 <input type="button" value=">" onclick="proximaObservacao()"/>');          
  $('.displayMedicao').slick({
      slidesToShow: 1,
      waitForAnimate: true,
      arrows: false
  });
  $('.displayObservacao').slick({
      slidesToShow: 1,
      waitForAnimate: true,
      arrows: false
  });
  $('.divFotos').slick({
      adaptativeHeight: true,
      slidesToShow: 1,
      dots: true,
      autoplay: true,
      autoplaySpeed: 2500
  });
});