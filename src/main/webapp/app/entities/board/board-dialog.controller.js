(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('BoardDialogController', BoardDialogController);

    BoardDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Board', 'Card'];

    function BoardDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Board, Card) {
        var vm = this;
        vm.board = entity;
        vm.cards = Card.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('diesliApp:boardUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.board.id !== null) {
                Board.update(vm.board, onSaveSuccess, onSaveError);
            } else {
                Board.save(vm.board, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
