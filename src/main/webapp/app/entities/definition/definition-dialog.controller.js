(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('DefinitionDialogController', DefinitionDialogController);

    DefinitionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Definition', 'MottoDefinition', 'Motto'];

    function DefinitionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Definition, MottoDefinition, Motto) {
        var vm = this;
        vm.definition = entity;
        vm.mottodefinitions = MottoDefinition.query();
        vm.mottos = Motto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('diesliApp:definitionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.definition.id !== null) {
                Definition.update(vm.definition, onSaveSuccess, onSaveError);
            } else {
                Definition.save(vm.definition, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
