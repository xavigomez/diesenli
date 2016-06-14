(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('BuscadorController', BuscadorController);

    function BuscadorController ( $timeout, $q, $log, Motto,MottoDefinition, $scope, Principal, LoginService, $state) {

        var vm = this;

        vm.isList = function (item) {
            if(item.indexOf(',') !== -1){
                if(item.slice(-1) === 'a'){
                    return true;
                }
                return false;
            }
            return true;
        }


        vm.penes = [];

        vm.advancedSearchDiv = false;

        vm.advancedSearchDivControl = function(){
            vm.advancedSearchDiv = ! vm.advancedSearchDiv;
        }

        vm.advancedSearch = function(searchText) {


            var searchBy = vm.searchBy;

            console.log(searchText);
            //Función para limpiar arrays
            Array.prototype.clean = function(deleteValue) {
                for (var i = 0; i < this.length; i++) {
                    if (this[i] == deleteValue) {
                        this.splice(i, 1);
                        i--;
                    }
                }
                return this;
            };

            //logica para montar los arrays;

            var advancedSearchCategory = vm.advancedSearchCategory;
            advancedSearchCategory.clean(null);
            console.log(advancedSearchCategory);
            var categoryStr = advancedSearchCategory.join("-");


            //------ Materia
            var advancedSearchMateria = vm.advancedSearchMateria;
            advancedSearchMateria.clean(null);
            var materiaStr = advancedSearchMateria.join("-");

            console.log(materiaStr);

            //------ Region
            var advancedSearchRegion = vm.advancedSearchRegion;
            advancedSearchRegion.clean(null);
            var regionStr = advancedSearchRegion.join("-");
            console.log(regionStr);

            //------ Level
            var advancedSearchLevel = vm.advancedSearchLevel;
            advancedSearchLevel.clean(null);
            var levelStr = advancedSearchLevel.join("-");
            console.log(levelStr);


            if(categoryStr.length == 0){ categoryStr = 'Empty';}
            if(materiaStr.length == 0){ materiaStr = 'Empty';}
            if(regionStr.length == 0){ regionStr = 'Empty';}
            if(levelStr.length == 0){ levelStr = 'Empty';}




                MottoDefinition.byFilters({
                searchTerm: searchText,
                searchBy: searchBy,
                categorias: categoryStr,
                registros: levelStr,
                materias: materiaStr,
                regiones: regionStr
                },
                function(result){
                    vm.avanzados = result;
                }
            );

        }


        vm.isAdvanced = true;

        vm.simulateQuery = true;
        vm.isDisabled = false;
        vm.querySearch = querySearch;
        vm.selectedItemChange = selectedItemChange;
        vm.itemClicked = itemClicked;
        vm.searchTextChange = searchTextChange;
        vm.newState = newState;

        vm.searchBy = 1;
        vm.searchByControl = searchByControl;

        vm.withSynonyms = true;
        vm.withAntonyms = true;
        vm.withEtymology = true;
        vm.relatedMottos = true;
        vm.withExamples = true;
        vm.orderedBy = true;
        vm.withRelateds = true;

        vm.advancedSearchCategory = [null, null, null, null, null];
        vm.advancedSearchMateria = [null, null, null];
        vm.advancedSearchRegion = [null, null, null];
        vm.advancedSearchLevel = [null, null];

        vm.relateds = true;
        vm.oposition = true;
        vm.similarity = true;

        vm.advancedSearchAdvervio = false;
        vm.advancedSearchAdverbioControl = function(){
            vm.advancedSearchAdvervio = !vm.advancedSearchAdvervio;
            (vm.advancedSearchAdvervio)?
                vm.advancedSearchCategory[0]='adverbio':
                vm.advancedSearchCategory[0] = null;
        }

        vm.advancedSearchArticulo = false;
        vm.advancedSearchArticuloControl = function(){
            vm.advancedSearchArticulo = !vm.advancedSearchArticulo;
            (vm.advancedSearchArticulo)?
                vm.advancedSearchCategory[1]='articulo':
                vm.advancedSearchCategory[1] = null;
        }

        vm.advancedSearchAdjetivo = false;
        vm.advancedSearchAdjetivoControl = function(){
            vm.advancedSearchAdjetivo = !vm.advancedSearchAdjetivo;
            (vm.advancedSearchAdjetivo)?
                vm.advancedSearchCategory[2]='adjetivo':
                vm.advancedSearchCategory[2] = null;
        }

        vm.advancedSearchNombre = false;
        vm.advancedSearchNombreControl = function(){
            vm.advancedSearchNombre = !vm.advancedSearchNombre;
            (vm.advancedSearchNombre)?
                vm.advancedSearchCategory[3]='nombre':
                vm.advancedSearchCategory[3] = null;
        }

        vm.advancedSearchVerbo = false;
        vm.advancedSearchVerboControl = function(){
            vm.advancedSearchVerbo = !vm.advancedSearchVerbo;
            (vm.advancedSearchNombre)?
                vm.advancedSearchCategory[4]='verbo':
                vm.advancedSearchCategory[4] = null;
        }

        vm.advancedSearchCine = false;
        vm.advancedSearchCineControl = function(){
            vm.advancedSearchCine = !vm.advancedSearchCine;
            (vm.advancedSearchCine)?
                vm.advancedSearchMateria[0]='Cinem.':
                vm.advancedSearchMateria[0] = null;
        }

        vm.advancedSearchTeatro = false;
        vm.advancedSearchTeatroControl = function(){
            vm.advancedSearchTeatro = !vm.advancedSearchTeatro;
            (vm.advancedSearchTeatro)?
                vm.advancedSearchMateria[1]='Teatro.':
                vm.advancedSearchMateria[1] = null;
        }

        vm.advancedSearchTelevision = false;
        vm.advancedSearchTelevisionControl = function(){
            vm.advancedSearchTelevision = !vm.advancedSearchTelevision;
            (vm.advancedSearchTelevision)?
                vm.advancedSearchMateria[2]='TV.':
                vm.advancedSearchMateria[2] = null;
        }

        vm.advancedSearchArgentina = false;
        vm.advancedSearchArgentinaControl = function(){
            vm.advancedSearchArgentina = !vm.advancedSearchArgentina;
            (vm.advancedSearchArgentina)?
                vm.advancedSearchRegion[0]='Arg.':
                vm.advancedSearchRegion[0] = null;
        }

        vm.advancedSearchSalamanca = false;
        vm.advancedSearchSalamancaControl = function(){
            vm.advancedSearchSalamanca = !vm.advancedSearchSalamanca;
            (vm.advancedSearchSalamanca)?
                vm.advancedSearchRegion[1]='Sal.':
                vm.advancedSearchRegion[1] = null;
        }

        vm.advancedSearchUruguay = false;
        vm.advancedSearchUruguayControl = function(){
            vm.advancedSearchUruguay = !vm.advancedSearchUruguay;
            (vm.advancedSearchUruguay)?
                vm.advancedSearchRegion[2]='Ur.':
                vm.advancedSearchRegion[2] = null;
        }

        vm.advancedSearchColoquial = false;
        vm.advancedSearchColoquialControl = function(){
            vm.advancedSearchColoquial = !vm.advancedSearchColoquial;
            (vm.advancedSearchColoquial)?
                vm.advancedSearchLevel[0]='coloquial':
                vm.advancedSearchLevel[0] = null;
        }

        vm.advancedSearchDesuso = false;
        vm.advancedSearchDesusoControl = function(){
            vm.advancedSearchDesuso = !vm.advancedSearchDesuso;
            (vm.advancedSearchDesuso)?
                vm.advancedSearchLevel[1]='desus.':
                vm.advancedSearchLevel[1] = null;
        }

        vm.filterEtimology = function(){
            vm.withEtymology = !vm.withEtymology;
        }

        vm.filterSynonym = function(){
            vm.withSynonyms = !vm.withSynonyms;
        }

        vm.filterAntonym = function(){
            vm.withAntonyms = !vm.withAntonyms;
        }

        vm.filterExamples = function(){
            vm.withExamples = !vm.withExamples;
        }

        vm.filterRelateds = function(option){


            console.log(option);

            var relatedMottos = parseInt(option);

            switch(relatedMottos){
                case 1: $('#related-mottos').html('Con lemas relacionados');
                    vm.relateds = true;
                    vm.oposition = true;
                    vm.similarity = true;
                    break;
                case 2:  $('#related-mottos').html('Sin lemas relacionados');
                    vm.relateds = false;
                    vm.oposition = true;
                    vm.similarity = true;
                    break;
                case 3:  $('#related-mottos').html('Lemas por semejanza');
                    (!vm.relateds)? vm.relateds = true:false;
                    vm.oposition = false;
                    vm.similarity = true;
                    break;
                case 4:  $('#related-mottos').html('Lemas por oposición');
                    (!vm.relateds)? vm.relateds = true:false;
                    vm.oposition = true;
                    vm.similarity = false;
                    break;
                default: console.log('order-by error');
            }
        }

        function isSearchMade(){
            if( !$('.card').length ){
                return false;
            }else{
                return true;
            }
        }



        //recogemos el tipo de búsqueda
        function searchByControl(searchByOptionControl){

            vm.searchBy = searchByOptionControl;
            console.log(vm.searchBy);

            switch(vm.searchBy){
                case 1: $('#search-by').html('Por palabra');
                    break;
                case 2: $('#search-by').html('Lema exacto');
                    break;
                case 3: $('#search-by').html('Empieza por');
                    break;
                case 4: $('#search-by').html('Acaba en');
                    break;
                case 5: $('#search-by').html('Por lema exacto');
                    break;
                case 6: $('#search-by').html('No empieza por');
                    break;
                case 7: $('#search-by').html('No acaba en');
                    break;
                case 8: $('#search-by').html('No contiene');
                    break;
                default: console.log('search-by error');
            }

        }

        //Recogemos el data del tipo de ordenación
        $('.order-by-option').click(function(){
            $('span#order-by').data('order-by', $(this).data('order-by').toString());

            var orderBy = parseInt($('#order-by').data('order-by'));

            switch(orderBy){
                case 1: $('#order-by').html('Ordenación por popularidad');
                    break;
                case 2: $('#order-by').html('Ordenación normal');
                    break;
            }

        });

        /****************************
         CONTROLLER DE FERRÁN
         *****************************/
        vm.simulateQuery = true;
        vm.isDisabled = false;
        vm.querySearch = querySearch;
        vm.selectedItemChange = selectedItemChange;
        vm.itemClicked = itemClicked;
        vm.searchTextChange = searchTextChange;
        vm.newState = newState;

        vm.searchText="";
        vm.items=[];
        vm.mottoDefinitions=[];


        vm.mottos = [];
        vm.loadAll = function () {
            Motto.query(function (result) {
                vm.mottos = result;
            })
        }

        vm.loadAll();

        function newState(state) {
            alert("La función no se ha implementado");
        }

        function querySearch(query) {
            var results = query ? vm.states.filter(createFilterFor(query)) : vm.states, deferred;
            if (vm.simulateQuery) {
                deferred = $q.defer();
                $timeout(function () {
                    deferred.resolve(results);
                },
                    Math.random() * 1000, false);
                return deferred.promise;
            } else {
                return results;
            }
        }

        function searchTextChange(text) {
            $log.info('Text changed to ' + text);
            Motto.searchTerm({searchTerm: text}, function(result){
                    vm.items=result;
                }
            );
        }

        /*function selectedItemChange(item) {
         $log.info('Item changed to ' + JSON.stringify(item.id));
         MottoDefinition.selectedMottoId({selectedMottoId: item.id}, function (result){
         vm.mottoDefinitions=result;
         });
         }*/

        //filter function for search query
        function createFilterFor(query) {
            var lowercaseQuery = angular.lowercase(query);
            return function filterFn(state) {
                return (state.value.indexOf(lowercaseQuery) === 0);
            };
        }

        function selectedItemChange( item ){


            MottoDefinition.selectedMottoId({
                    selectedMottoId: item.id
                },
                function (result){
                    vm.mottoDefinitions=result;
                });

        }

        function itemClicked(mottoId){
            console.log(mottoId);
            MottoDefinition.selectedMottoId({selectedMottoId: mottoId}, function(result){
                vm.mottoDefinitions = result;
            });
        }

        //Scroll Control
        var cardBox = $('#cards-container'),
            height = cardBox.height(),
            scrollHeight = cardBox.get(0).scrollHeight;

        console.log(cardBox);
        console.log(height);
        console.log(scrollHeight);

        cardBox.bind('mousewheel', function(e, d) {
            console.log(scrollHeight);
            if((this.scrollTop === (scrollHeight - height) && d < 0) || (this.scrollTop === 0 && d > 0)) {
                e.preventDefault();
            }
        });

    }
})();
