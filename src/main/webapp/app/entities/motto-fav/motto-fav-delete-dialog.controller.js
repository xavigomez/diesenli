(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('MottoFavDeleteController',MottoFavDeleteController);

    MottoFavDeleteController.$inject = ['$uibModalInstance', 'entity', 'MottoFav'];

    function MottoFavDeleteController($uibModalInstance, entity, MottoFav) {
        var vm = this;
        vm.mottoFav = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            MottoFav.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
