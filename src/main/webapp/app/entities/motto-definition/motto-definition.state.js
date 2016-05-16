(function() {
    'use strict';

    angular
        .module('diesliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('motto-definition', {
            parent: 'entity',
            url: '/motto-definition',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MottoDefinitions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/motto-definition/motto-definitions.html',
                    controller: 'MottoDefinitionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })

        .state('motto-definition.new', {
            parent: 'motto-definition',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/motto-definition/motto-definition-dialog.html',
                    controller: 'MottoDefinitionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                materia: null,
                                registro: null,
                                region: null,
                                ejemplo: null,
                                categoria: null,
                                popularity: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('motto-definition', null, { reload: true });
                }, function() {
                    $state.go('motto-definition');
                });
            }]
        })
        .state('motto-definition.edit', {
            parent: 'motto-definition',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/motto-definition/motto-definition-dialog.html',
                    controller: 'MottoDefinitionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MottoDefinition', function(MottoDefinition) {
                            return MottoDefinition.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('motto-definition', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('motto-definition.delete', {
            parent: 'motto-definition',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/motto-definition/motto-definition-delete-dialog.html',
                    controller: 'MottoDefinitionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MottoDefinition', function(MottoDefinition) {
                            return MottoDefinition.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('motto-definition', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
