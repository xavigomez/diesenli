(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('MottoDefinitionDeleteController',MottoDefinitionDeleteController);

    MottoDefinitionDeleteController.$inject = ['$uibModalInstance', 'entity', 'MottoDefinition'];

    function MottoDefinitionDeleteController($uibModalInstance, entity, MottoDefinition) {
        var vm = this;
        vm.mottoDefinition = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            MottoDefinition.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
