(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('MottoDefinitionDialogController', MottoDefinitionDialogController);

    MottoDefinitionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MottoDefinition', 'Motto', 'Definition'];

    function MottoDefinitionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MottoDefinition, Motto, Definition) {
        var vm = this;
        vm.mottoDefinition = entity;
        vm.mottodefinitions = MottoDefinition.query();
        vm.mottos = Motto.query();
        vm.definitions = Definition.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('diesliApp:mottoDefinitionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.mottoDefinition.id !== null) {
                MottoDefinition.update(vm.mottoDefinition, onSaveSuccess, onSaveError);
            } else {
                MottoDefinition.save(vm.mottoDefinition, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
