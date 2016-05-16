(function() {
    'use strict';

    angular
        .module('diesliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('motto-fav', {
            parent: 'entity',
            url: '/motto-fav',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MottoFavs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/motto-fav/motto-favs.html',
                    controller: 'MottoFavController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('motto-fav-detail', {
            parent: 'entity',
            url: '/motto-fav/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MottoFav'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/motto-fav/motto-fav-detail.html',
                    controller: 'MottoFavDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'MottoFav', function($stateParams, MottoFav) {
                    return MottoFav.get({id : $stateParams.id});
                }]
            }
        })
        .state('motto-fav.new', {
            parent: 'motto-fav',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/motto-fav/motto-fav-dialog.html',
                    controller: 'MottoFavDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('motto-fav', null, { reload: true });
                }, function() {
                    $state.go('motto-fav');
                });
            }]
        })
        .state('motto-fav.edit', {
            parent: 'motto-fav',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/motto-fav/motto-fav-dialog.html',
                    controller: 'MottoFavDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MottoFav', function(MottoFav) {
                            return MottoFav.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('motto-fav', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('motto-fav.delete', {
            parent: 'motto-fav',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/motto-fav/motto-fav-delete-dialog.html',
                    controller: 'MottoFavDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MottoFav', function(MottoFav) {
                            return MottoFav.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('motto-fav', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
