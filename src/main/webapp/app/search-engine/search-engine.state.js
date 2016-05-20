/**
 * Created by xavi on 11/5/16.
 */
(function() {
    'use strict';

    angular
        .module('diesliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('search-engine', {
            parent: 'app',
            url: '/search',
            data: {
                authorities: [],
                pageTitle: 'Search'
            },
            views: {
                'content@': {
                    templateUrl: 'app/search-engine/search-engine.html',
                    controller: 'SearchengineController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
