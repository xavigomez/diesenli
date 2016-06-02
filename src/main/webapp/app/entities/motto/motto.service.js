(function() {
    'use strict';
    angular
        .module('diesliApp')
        .factory('Motto', Motto);

    Motto.$inject = ['$resource'];

    function Motto ($resource) {
        var resourceUrl =  'api/mottos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' },
            'byFilters': {
                method: 'GET',
                isArray: true,
                url: 'api/motto/byfilters'
            },
            'searchTerm': {
                method: 'GET', isArray: true, url: 'api/motto/search/:searchTerm',
                interceptor: {
                    response: function (response) {
                        response.resource.$httpHeaders = response.headers;
                        return response.resource;
                    }
                }
            }
        });
    }
})();
