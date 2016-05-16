'use strict';

describe('Controller Tests', function() {

    describe('Motto Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockMotto, MockMottoDefinition, MockDefinition, MockMottoFav, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockMotto = jasmine.createSpy('MockMotto');
            MockMottoDefinition = jasmine.createSpy('MockMottoDefinition');
            MockDefinition = jasmine.createSpy('MockDefinition');
            MockMottoFav = jasmine.createSpy('MockMottoFav');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Motto': MockMotto,
                'MottoDefinition': MockMottoDefinition,
                'Definition': MockDefinition,
                'MottoFav': MockMottoFav,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("MottoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'diesliApp:mottoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
