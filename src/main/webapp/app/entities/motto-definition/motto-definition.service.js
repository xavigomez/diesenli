(function () {
    'use strict';
    angular
        .module('diesliApp')
        .factory('MottoDefinition', MottoDefinition);

    MottoDefinition.$inject = ['$resource'];

    function MottoDefinition($resource) {
        var resourceUrl = 'api/motto-definitions/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'byFilters': {
                method: 'GET',
                isArray: true,
                url: 'api/motto-definitions/byfilters'
            },
            'update': {method: 'PUT'},
            'selectedMottoId': {
                method: 'GET', isArray: true, url: 'api/motto-definitions/search/:selectedMottoId',
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
