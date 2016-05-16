(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('MottoDefinitionDetailController', MottoDefinitionDetailController);

    MottoDefinitionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'MottoDefinition', 'Motto', 'Definition'];

    function MottoDefinitionDetailController($scope, $rootScope, $stateParams, entity, MottoDefinition, Motto, Definition) {
        var vm = this;
        vm.mottoDefinition = entity;
        
        var unsubscribe = $rootScope.$on('diesliApp:mottoDefinitionUpdate', function(event, result) {
            vm.mottoDefinition = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
