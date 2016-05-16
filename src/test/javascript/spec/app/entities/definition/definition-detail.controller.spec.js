'use strict';

describe('Controller Tests', function() {

    describe('Definition Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDefinition, MockMottoDefinition, MockMotto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDefinition = jasmine.createSpy('MockDefinition');
            MockMottoDefinition = jasmine.createSpy('MockMottoDefinition');
            MockMotto = jasmine.createSpy('MockMotto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Definition': MockDefinition,
                'MottoDefinition': MockMottoDefinition,
                'Motto': MockMotto
            };
            createController = function() {
                $injector.get('$controller')("DefinitionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'diesliApp:definitionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
