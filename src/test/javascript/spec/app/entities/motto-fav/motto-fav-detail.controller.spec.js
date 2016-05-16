'use strict';

describe('Controller Tests', function() {

    describe('MottoFav Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockMottoFav, MockMotto, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockMottoFav = jasmine.createSpy('MockMottoFav');
            MockMotto = jasmine.createSpy('MockMotto');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'MottoFav': MockMottoFav,
                'Motto': MockMotto,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("MottoFavDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'diesliApp:mottoFavUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
