(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('CardReplyDeleteController',CardReplyDeleteController);

    CardReplyDeleteController.$inject = ['$uibModalInstance', 'entity', 'CardReply'];

    function CardReplyDeleteController($uibModalInstance, entity, CardReply) {
        var vm = this;
        vm.cardReply = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            CardReply.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
