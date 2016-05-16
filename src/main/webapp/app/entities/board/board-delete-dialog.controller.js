(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('BoardDeleteController',BoardDeleteController);

    BoardDeleteController.$inject = ['$uibModalInstance', 'entity', 'Board'];

    function BoardDeleteController($uibModalInstance, entity, Board) {
        var vm = this;
        vm.board = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Board.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
