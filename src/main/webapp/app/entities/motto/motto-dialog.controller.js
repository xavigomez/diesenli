(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('MottoDialogController', MottoDialogController);

    MottoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Motto', 'MottoDefinition', 'Definition', 'MottoFav', 'User'];

    function MottoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Motto, MottoDefinition, Definition, MottoFav, User) {
        var vm = this;
        vm.motto = entity;
        vm.mottodefinitions = MottoDefinition.query();
        vm.definitions = Definition.query();
        vm.mottofavs = MottoFav.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('diesliApp:mottoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.motto.id !== null) {
                Motto.update(vm.motto, onSaveSuccess, onSaveError);
            } else {
                Motto.save(vm.motto, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
