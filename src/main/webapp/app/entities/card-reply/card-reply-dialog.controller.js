(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('CardReplyDialogController', CardReplyDialogController);

    CardReplyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CardReply', 'Card'];

    function CardReplyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CardReply, Card) {
        var vm = this;
        vm.cardReply = entity;
        vm.cards = Card.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('diesliApp:cardReplyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.cardReply.id !== null) {
                CardReply.update(vm.cardReply, onSaveSuccess, onSaveError);
            } else {
                CardReply.save(vm.cardReply, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
