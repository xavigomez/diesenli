(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('BoardController', BoardController);

    BoardController.$inject = ['$scope', '$state', 'Board'];

    function BoardController ($scope, $state, Board) {
        var vm = this;
        vm.boards = [];
        vm.loadAll = function() {
            Board.query(function(result) {
                vm.boards = result;
            });
        };

        vm.loadAll();
        
    }
})();
