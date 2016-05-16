(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('CardReplyDetailController', CardReplyDetailController);

    CardReplyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'CardReply', 'Card'];

    function CardReplyDetailController($scope, $rootScope, $stateParams, entity, CardReply, Card) {
        var vm = this;
        vm.cardReply = entity;
        
        var unsubscribe = $rootScope.$on('diesliApp:cardReplyUpdate', function(event, result) {
            vm.cardReply = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
