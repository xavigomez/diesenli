(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('CardDetailController', CardDetailController);

    CardDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'Card', 'CardReply', 'Board', 'User'];

    function CardDetailController($scope, $rootScope, $stateParams, DataUtils, entity, Card, CardReply, Board, User) {
        var vm = this;
        vm.card = entity;
        
        var unsubscribe = $rootScope.$on('diesliApp:cardUpdate', function(event, result) {
            vm.card = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
    }
})();
