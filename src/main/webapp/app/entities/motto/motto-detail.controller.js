(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('MottoDetailController', MottoDetailController);

    MottoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Motto', 'MottoDefinition', 'Definition', 'MottoFav', 'User'];

    function MottoDetailController($scope, $rootScope, $stateParams, entity, Motto, MottoDefinition, Definition, MottoFav, User) {
        var vm = this;
        vm.motto = entity;
        
        var unsubscribe = $rootScope.$on('diesliApp:mottoUpdate', function(event, result) {
            vm.motto = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
