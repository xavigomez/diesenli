(function () {
    angular
        .module('diesliApp')
        .controller('buscadorCtrl', autoCompleteController);
    function autoCompleteController($timeout, $q, $log, Motto,MottoDefinition) {
        var self = this;
        self.simulateQuery = true;
        self.isDisabled = false;
        self.querySearch = querySearch;
        self.selectedItemChange = selectedItemChange;
        self.searchTextChange = searchTextChange;
        self.newState = newState;

        self.searchText="";
        self.items=[];
        self.mottoDefinitions=[];

        function newState(state) {
            alert("La función no se ha implementado");
        }

        function querySearch(query) {
            var results = query ? self.states.filter(createFilterFor(query)) : self.states, deferred;
            if (self.simulateQuery) {
                deferred = $q.defer();
                $timeout(function () {
                    deferred.resolve(results);
                },
                    Math.random() * 1000, false);
                return deferred.promise;
            } else {
                return results;
            }
        }

        function searchTextChange(text) {
            $log.info('Text changed to ' + text);
            Motto.searchTerm({searchTerm: text}, function(result){
                self.items=result;
                }
            );
        }

        function selectedItemChange(item) {
            $log.info('Item changed to ' + JSON.stringify(item.id));
            MottoDefinition.selectedMottoId({selectedMottoId: item.id}, function (result){
                self.mottoDefinitions=result;
            });
        }

       //filter function for search query
        function createFilterFor(query) {
            var lowercaseQuery = angular.lowercase(query);
            return function filterFn(state) {
                return (state.value.indexOf(lowercaseQuery) === 0);
            };
        }
    }
})();

