(function() {
    'use strict';

    angular
        .module('diesliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('buscador', {
            parent: 'app',
            url: '/buscador',
            data: {
                authorities: [],
                pageTitle: 'buscador'
            },
            views: {
                'content@': {
                    templateUrl: 'app/buscador/buscador.html',
                    controller: 'BuscadorController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();

