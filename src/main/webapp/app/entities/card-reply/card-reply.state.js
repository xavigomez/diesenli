(function() {
    'use strict';

    angular
        .module('diesliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('card-reply', {
            parent: 'entity',
            url: '/card-reply',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CardReplies'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/card-reply/card-replies.html',
                    controller: 'CardReplyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('card-reply-detail', {
            parent: 'entity',
            url: '/card-reply/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CardReply'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/card-reply/card-reply-detail.html',
                    controller: 'CardReplyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CardReply', function($stateParams, CardReply) {
                    return CardReply.get({id : $stateParams.id});
                }]
            }
        })
        .state('card-reply.new', {
            parent: 'card-reply',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/card-reply/card-reply-dialog.html',
                    controller: 'CardReplyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                date: null,
                                content: null,
                                popularity: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('card-reply', null, { reload: true });
                }, function() {
                    $state.go('card-reply');
                });
            }]
        })
        .state('card-reply.edit', {
            parent: 'card-reply',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/card-reply/card-reply-dialog.html',
                    controller: 'CardReplyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CardReply', function(CardReply) {
                            return CardReply.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('card-reply', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('card-reply.delete', {
            parent: 'card-reply',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/card-reply/card-reply-delete-dialog.html',
                    controller: 'CardReplyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CardReply', function(CardReply) {
                            return CardReply.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('card-reply', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
