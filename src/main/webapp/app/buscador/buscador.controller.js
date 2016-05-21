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
        vm.relatedMottos = 1;
        vm.withExamples = true;
        vm.orderedBy = true;


        vm.searchByControl = function() {
            console.log('click');
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

    }
})();
