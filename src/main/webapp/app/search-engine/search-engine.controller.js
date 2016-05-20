/**
 * Created by xavi on 11/5/16.
 */
(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('SearchengineController', SearchengineController);


    SearchengineController.$inject = [];

    function SearchengineController () {
        var vm = this;


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
