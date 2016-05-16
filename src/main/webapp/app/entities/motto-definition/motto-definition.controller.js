(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('MottoDefinitionController', MottoDefinitionController);

    MottoDefinitionController.$inject = ['$scope', '$state', 'MottoDefinition'];

    function MottoDefinitionController ($scope, $state, MottoDefinition) {
        var vm = this;
        vm.mottoDefinitions = [];
        vm.loadAll = function() {
            MottoDefinition.query(function(result) {
                vm.mottoDefinitions = result;
            });
        };

        vm.loadAll();
        
    }
})();
