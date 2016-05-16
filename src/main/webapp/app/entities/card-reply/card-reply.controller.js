(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('CardReplyController', CardReplyController);

    CardReplyController.$inject = ['$scope', '$state', 'CardReply'];

    function CardReplyController ($scope, $state, CardReply) {
        var vm = this;
        vm.cardReplies = [];
        vm.loadAll = function() {
            CardReply.query(function(result) {
                vm.cardReplies = result;
            });
        };

        vm.loadAll();
        
    }
})();
