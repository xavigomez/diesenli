(function() {
    'use strict';

    angular
        .module('diesliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('motto', {
            parent: 'entity',
            url: '/motto',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Mottos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/motto/mottos.html',
                    controller: 'MottoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('motto-detail', {
            parent: 'entity',
            url: '/motto/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Motto'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/motto/motto-detail.html',
                    controller: 'MottoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Motto', function($stateParams, Motto) {
                    return Motto.get({id : $stateParams.id});
                }]
            }
        })
        .state('motto.new', {
            parent: 'motto',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/motto/motto-dialog.html',
                    controller: 'MottoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                informacionEtimologica: null,
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('motto', null, { reload: true });
                }, function() {
                    $state.go('motto');
                });
            }]
        })
        .state('motto.edit', {
            parent: 'motto',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/motto/motto-dialog.html',
                    controller: 'MottoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Motto', function(Motto) {
                            return Motto.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('motto', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('motto.delete', {
            parent: 'motto',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/motto/motto-delete-dialog.html',
                    controller: 'MottoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Motto', function(Motto) {
                            return Motto.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('motto', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
