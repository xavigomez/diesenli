(function() {
    'use strict';
    angular
        .module('diesliApp')
        .factory('Board', Board);

    Board.$inject = ['$resource'];

    function Board ($resource) {
        var resourceUrl =  'api/boards/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
