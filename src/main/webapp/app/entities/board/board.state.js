(function() {
    'use strict';

    angular
        .module('diesliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('board', {
            parent: 'entity',
            url: '/board',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Boards'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/board/boards.html',
                    controller: 'BoardController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('board-detail', {
            parent: 'entity',
            url: '/board/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Board'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/board/board-detail.html',
                    controller: 'BoardDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Board', function($stateParams, Board) {
                    return Board.get({id : $stateParams.id});
                }]
            }
        })
        .state('board.new', {
            parent: 'board',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/board/board-dialog.html',
                    controller: 'BoardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                boardName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('board', null, { reload: true });
                }, function() {
                    $state.go('board');
                });
            }]
        })
        .state('board.edit', {
            parent: 'board',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/board/board-dialog.html',
                    controller: 'BoardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Board', function(Board) {
                            return Board.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('board', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('board.delete', {
            parent: 'board',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/board/board-delete-dialog.html',
                    controller: 'BoardDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Board', function(Board) {
                            return Board.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('board', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
