(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('MottoController', MottoController);

    MottoController.$inject = ['$scope', '$state', 'Motto'];

    function MottoController ($scope, $state, Motto) {
        var vm = this;
        vm.mottos = [];
        vm.loadAll = function() {
            Motto.query(function(result) {
                vm.mottos = result;
            });
        };

        vm.loadAll();
        
    }
})();
