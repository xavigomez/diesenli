(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('DefinitionDeleteController',DefinitionDeleteController);

    DefinitionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Definition'];

    function DefinitionDeleteController($uibModalInstance, entity, Definition) {
        var vm = this;
        vm.definition = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Definition.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
