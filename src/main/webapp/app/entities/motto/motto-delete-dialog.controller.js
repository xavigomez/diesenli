(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('MottoDeleteController',MottoDeleteController);

    MottoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Motto'];

    function MottoDeleteController($uibModalInstance, entity, Motto) {
        var vm = this;
        vm.motto = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Motto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
