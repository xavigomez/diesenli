(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('CardDeleteController',CardDeleteController);

    CardDeleteController.$inject = ['$uibModalInstance', 'entity', 'Card'];

    function CardDeleteController($uibModalInstance, entity, Card) {
        var vm = this;
        vm.card = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Card.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
