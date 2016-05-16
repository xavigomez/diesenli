(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('CardController', CardController);

    CardController.$inject = ['$scope', '$state', 'DataUtils', 'Card'];

    function CardController ($scope, $state, DataUtils, Card) {
        var vm = this;
        vm.cards = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.loadAll = function() {
            Card.query(function(result) {
                vm.cards = result;
            });
        };

        vm.loadAll();
        
    }
})();
