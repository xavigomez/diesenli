(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('BuscadorController', BuscadorController);


    BuscadorController.$inject = [];

    function BuscadorController () {

        var vm = this;

        vm.searchBy = 1;

        vm.withSynonyms = true;
        vm.withAntonyms = true;
        vm.withEtymology = true;
        vm.relatedMottos = true;
        vm.withExamples = true;
        vm.orderedBy = true;
        vm.withRelateds = true;

        vm.relateds = true;
        vm.oposition = true;
        vm.similarity = true;


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

        $('span.advanced-search-switch').click(function () {
            $('.logged-user-options').toggleClass('open');
        });
        //Cards
        var lastActiveCard = $( '#cards-container .card:first-child' );

        $( '.card.closed' ).click(function(){
            lastActiveCard.toggleClass( 'closed' );
            console.log( $( this )  );
            $( this ).toggleClass( 'closed' );
            lastActiveCard = $( this );
        });

        //To open advanced options
        $('span.advanced-search-switch').click(function(){
            console.log('click');
            $('i.advanced-options-switch').toggleClass('open-advanced-options');
            $('section#advanced-search').toggleClass('open');
        });

        //Simple options switch
        $('.search-options div.switchable').click(function(){
            $(this).toggleClass('disabled');
        });

        $('.dropdown-search-option').click(function(){
            $('.dropdown-options' ,this).toggleClass('hidden-dd');
            $(this).toggleClass('raised');
        });

        $('.sub-dropdown-options').hover(function(){
            $('.sub-dropdown-options-container.sub-one').toggleClass('hidden-dd');
        });

        $('.sub-dropdown-options-container.sub-one').hover(function(){
            $(this).toggleClass('hidden-dd');
            $('span.search-by-option.sub-dropdown-options').toggleClass('hover');
        });

        $('span.sub-dropdown-options-container').hover(function(){
            $('.sub-dropdown-options-container', this).toggleClass('hidden-dd');
        });

        //recogemos el tipo de búsqueda
        $('.search-by-option').click(function(){
            $('span#search-by').data('search-by', $(this).data('search-by').toString());

            var searchBy = parseInt($('#search-by').data('search-by'));

            switch(searchBy){
                case 1:
                    $('#search-by').html('Por forma');
                    break;
                case 2: $('#search-by').html('Por palabra');
                    break;
                case 3: $('#search-by').html('Lema exacto');
                    break;
                case 4: $('#search-by').html('Empieza por');
                    break;
                case 5: $('#search-by').html('Acaba en');
                    break;
                case 6: $('#search-by').html('Por lema exacto');
                    break;
                case 7: $('#search-by').html('No empieza por');
                    break;
                case 8: $('#search-by').html('No acaba en');
                    break;
                case 9: $('#search-by').html('No contiene');
                    break;
                default: console.log('search-by error');
            }

        });

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

    }
})();
