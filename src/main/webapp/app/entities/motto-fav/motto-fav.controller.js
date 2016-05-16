(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('MottoFavController', MottoFavController);

    MottoFavController.$inject = ['$scope', '$state', 'MottoFav'];

    function MottoFavController ($scope, $state, MottoFav) {
        var vm = this;
        vm.mottoFavs = [];
        vm.loadAll = function() {
            MottoFav.query(function(result) {
                vm.mottoFavs = result;
            });
        };

        vm.loadAll();
        
    }
})();
