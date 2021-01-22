var cityField;
var stateField;
var streetField;
var zipField;
var neighborhoodField;

function init(neighborhoodFieldParam, stateFieldParam, cityFieldParam, streetFieldParam, zipFieldParam) {
    streetField = streetFieldParam;
    cityField = cityFieldParam;
    stateField = stateFieldParam;
    neighborhoodField = neighborhoodFieldParam;
    zipField = zipFieldParam;

    console.log("Started address api for fields");
    console.log(streetField + " contains street value");
    console.log(cityField + " contains city value");
    console.log(stateField + " contains state value");
    console.log(neighborhoodField + " contains neighborhood value");
    console.log(zipField + " contains zip value");

    handleAddress();
}


function handleAddress() {

    function clearAdressInfo() {

        $(cityField).val("");
        $(stateField).val("");
        $(streetField).val("");
        $(neighborhoodField).val("");
    }

    $(zipField).blur(function () {

        console.log("Localizando endereço ... ");
        var cep = $(this).val().replace(/\D/g, '');

        if (cep !== "") {
            var validacep = /^[0-9]{8}$/;

            if (validacep.test(cep)) {

                $(cityField).val("...");
                $(stateField).val("...");
                $(streetField).val("...");
                $(neighborhoodField).val("...");

                //Consulta o webservice viacep.com.br/
                $.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?", function (dados) {

                    if (!("erro" in dados)) {

                        $(cityField).val(dados.localidade);
                        $(stateField).val(dados.uf);
                        $(streetField).val(dados.logradouro);
                        $(neighborhoodField).val(dados.bairro);
                        console.log("Success");

                    } else {
                        //CEP pesquisado não foi encontrado.
                        clearAdressInfo();
                        alert("CEP não encontrado, por favor informe os dados manualmente.");
                    }
                });
            } //end if.
            else {
                //cep é inválido.
                clearAdressInfo();
                alert("Formato de CEP inválido.");
            }
        } //end if.
        else {
            //cep sem valor, limpa formulário.
            clearAdressInfo();
        }
    });
}