(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('MottoFavDialogController', MottoFavDialogController);

    MottoFavDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MottoFav', 'Motto', 'User'];

    function MottoFavDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MottoFav, Motto, User) {
        var vm = this;
        vm.mottoFav = entity;
        vm.mottos = Motto.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('diesliApp:mottoFavUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.mottoFav.id !== null) {
                MottoFav.update(vm.mottoFav, onSaveSuccess, onSaveError);
            } else {
                MottoFav.save(vm.mottoFav, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
