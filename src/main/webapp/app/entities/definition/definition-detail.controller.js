(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('DefinitionDetailController', DefinitionDetailController);

    DefinitionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Definition', 'MottoDefinition', 'Motto'];

    function DefinitionDetailController($scope, $rootScope, $stateParams, entity, Definition, MottoDefinition, Motto) {
        var vm = this;
        vm.definition = entity;
        
        var unsubscribe = $rootScope.$on('diesliApp:definitionUpdate', function(event, result) {
            vm.definition = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
