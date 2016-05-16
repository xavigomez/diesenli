(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('DefinitionController', DefinitionController);

    DefinitionController.$inject = ['$scope', '$state', 'Definition'];

    function DefinitionController ($scope, $state, Definition) {
        var vm = this;
        vm.definitions = [];
        vm.loadAll = function() {
            Definition.query(function(result) {
                vm.definitions = result;
            });
        };

        vm.loadAll();
        
    }
})();
