(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('BoardDetailController', BoardDetailController);

    BoardDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Board', 'Card'];

    function BoardDetailController($scope, $rootScope, $stateParams, entity, Board, Card) {
        var vm = this;
        vm.board = entity;
        
        var unsubscribe = $rootScope.$on('diesliApp:boardUpdate', function(event, result) {
            vm.board = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
