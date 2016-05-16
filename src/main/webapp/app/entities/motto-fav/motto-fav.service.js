(function() {
    'use strict';
    angular
        .module('diesliApp')
        .factory('MottoFav', MottoFav);

    MottoFav.$inject = ['$resource'];

    function MottoFav ($resource) {
        var resourceUrl =  'api/motto-favs/:id';

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
