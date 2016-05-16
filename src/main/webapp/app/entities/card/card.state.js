(function() {
    'use strict';

    angular
        .module('diesliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('card', {
            parent: 'entity',
            url: '/card',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Cards'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/card/cards.html',
                    controller: 'CardController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('card-detail', {
            parent: 'entity',
            url: '/card/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Card'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/card/card-detail.html',
                    controller: 'CardDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Card', function($stateParams, Card) {
                    return Card.get({id : $stateParams.id});
                }]
            }
        })
        .state('card.new', {
            parent: 'card',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/card/card-dialog.html',
                    controller: 'CardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                date: null,
                                content: null,
                                title: null,
                                customExcerpt: null,
                                featuredImage: null,
                                featuredImageContentType: null,
                                views: null,
                                popularity: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('card', null, { reload: true });
                }, function() {
                    $state.go('card');
                });
            }]
        })
        .state('card.edit', {
            parent: 'card',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/card/card-dialog.html',
                    controller: 'CardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Card', function(Card) {
                            return Card.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('card', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('card.delete', {
            parent: 'card',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/card/card-delete-dialog.html',
                    controller: 'CardDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Card', function(Card) {
                            return Card.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('card', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
