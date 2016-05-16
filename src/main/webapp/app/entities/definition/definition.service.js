(function() {
    'use strict';
    angular
        .module('diesliApp')
        .factory('Definition', Definition);

    Definition.$inject = ['$resource'];

    function Definition ($resource) {
        var resourceUrl =  'api/definitions/:id';

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
