/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var buscaInicial;

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
    //var url = "http://localhost:8080/TempoClimaNet/ajax?type=" + tipo + "&date=" + data;
    var content = JSON.stringify({
        "type":tipo,
        "date":data
    });
    var ajaxRequest = new XMLHttpRequest();
    
    //ajaxRequest.open("GET", url);
    //ajaxRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    ajaxRequest.open("POST", "ajax");
    ajaxRequest.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    
    ajaxRequest.onreadystatechange = function() {
        if(ajaxRequest.readyState===4 && ajaxRequest.status===200){
            var respostaJSON = JSON.parse(ajaxRequest.responseText);
            if(!jQuery.isEmptyObject(respostaJSON)) callback(respostaJSON);
        }
    };
    
    //ajaxRequest.send(null);
    ajaxRequest.send(content);
}

function getCurrentDateString() {
    var currentdate = new Date(); 
    var datetime = currentdate.getDate() + "/"
            + (currentdate.getMonth()+1)  + "/" 
            + currentdate.getFullYear() + " "  
            + currentdate.getHours() + ":"  
            + currentdate.getMinutes() + ":" 
            + currentdate.getSeconds();
    return datetime;
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
            'Data-hora: <span name="datahoraautom" class="texto">' + 
            jsonMedicao.datahoraautom + ' </span><br>' +
            'Temperatura: <span name="temperatura" class="texto">' +
            getDecimalString(jsonMedicao.temperatura) + ' ºC </span><br>' +
            'Umidade: <span name="umidade" class="texto">' +
            getDecimalString(jsonMedicao.umidade) + ' %</span><br>' +
            'Ponto de orvalho: <span name="orvalho" class="texto">' + 
            getDecimalString(jsonMedicao.orvalho) + ' ºC</span><br>' +
            'Pressão atmosférica: <span name="pressao" class="texto">' +
            getDecimalString(jsonMedicao.pressao) + ' hPa</span><br>' +
            'Taxa de precipitação: <span name="precipitacao" class="texto">' +
            getDecimalString(jsonMedicao.precipitacao) + ' mm/h</span><br>'+
            'Precipitação acumulada: <span name="precipacumul" class="texto">' +
            getDecimalString(jsonMedicao.precipacumul) + ' mm</span>' + 
            '<span class="sub-texto">&nbsp;&nbsp;(últimas 24h)</span><br>' + 
            'Velocidade do Vento: <span name="velvento" class="texto">' +
            getDecimalString(jsonMedicao.velvento) + ' km/h</span><br>' + 
            'Direção do vento: <span name="dirvento" class="texto">' +
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
        '<div class="slide">' +
            'Data-hora: ' +
            '<span name="datahoraobs" class="texto">' + 
                jsonObservacao.datahoraobs + '</span><br>' +
            'Altura das ondas: ' +
            '<span name="altondas" class="texto">' +
                getDecimalString(jsonObservacao.altondas, 1) +
                ' m</span><br>' +
            'Temperatura da água: ' +
            '<span name="tempagua" class="texto">' +
                getDecimalString(jsonObservacao.tempagua, 1) +
                ' ºC</span><br>' +
            'Bandeira: ' +
            '<span name="bandsalvavidas" style="color:' + 
            jsonObservacao.bandeira.cor + '; font-weight:bold;">' +
                '<div class="tooltip">' +
                    jsonObservacao.bandeira.nome +
                    '<span class="tooltiptext">' +
                        jsonObservacao.bandeira.desc +
                    '</span>' +
                '</div>' + 
            '</span>' +
            '<br><br><br><br><br>'+
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
    var respostaJson = doAjax("lookup", data, buscarAfter);
}

function buscarAfter(respostaJson) {
    $('#datamed').val(respostaJson.medicoes.datahoraautom);
    $('#dataobs').val(respostaJson.observacoes.datahoraobs);
    
    var htmlMed = buildHtmlMedicao(respostaJson.medicoes);
    var htmlObs = buildHtmlObservacao(respostaJson.observacoes);
    
    slickScrollLeft('.displayMedicao', htmlMed);
    slickScrollLeft('.displayObservacao', htmlObs);
    
    if(!buscaInicial) {
        var uri = 'consulta?data=' + encodeURIComponent(respostaJson.datahora);
        history.pushState({urlPath: uri}, "", uri);
    }
    else { 
        buscaInicial = false;
    }
    $('#databusca').val(respostaJson.datahora);
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
    $('#scrollformControlsMedicao')
            .html('<button type="button" onclick="anteriorMedicao()">&#9664;</button>\n\
                   <button type="button" onclick="proximaMedicao()">&#9654;</button>');
    $('#scrollformControlsObservacao')
            .html('<button type="button" onclick="anteriorObservacao()">&#9664;</button>\n\
                   <button type="button" onclick="proximaObservacao()">&#9654;</button>');     
    $('#botaoBuscar').prop("type", "button");
    $('.displayMedicao').html('<div class="slide"></div>').slick({
        slidesToShow: 1,
        waitForAnimate: true,
        arrows: false,
        draggable: false
    });
    $('.displayObservacao').html('<div class="slide"></div>').slick({
        slidesToShow: 1,
        waitForAnimate: true,
        arrows: false,
        draggable: false
    });
    $('.displayFotos').slick({
        adaptativeHeight: true,
        slidesToShow: 1,
        dots: true,
        autoplay: true,
        autoplaySpeed: 2500
    });

    var pathname = window.location.href;

    // Se isso for -1 então não é uma consulta. Portanto gera uma nova data.
    if(pathname.indexOf('consulta') === -1) {
        buscaInicial = true;
        var datetime = getCurrentDateString();
        $('#caixaBusca').val(datetime);
        buscar(datetime);
    }
    else {
        var datetime = pathname.substring(pathname.indexOf('data=')+5, pathname.length);
        var querydate = decodeURIComponent(datetime).replace('+', ' ');
        $('#caixaBusca').val(querydate);
        buscar(querydate);
    }
});