(function() {
    'use strict';

    angular
        .module('diesliApp')
        .controller('MottoFavDetailController', MottoFavDetailController);

    MottoFavDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'MottoFav', 'Motto', 'User'];

    function MottoFavDetailController($scope, $rootScope, $stateParams, entity, MottoFav, Motto, User) {
        var vm = this;
        vm.mottoFav = entity;
        
        var unsubscribe = $rootScope.$on('diesliApp:mottoFavUpdate', function(event, result) {
            vm.mottoFav = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
