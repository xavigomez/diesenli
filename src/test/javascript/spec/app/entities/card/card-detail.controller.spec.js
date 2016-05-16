'use strict';

describe('Controller Tests', function() {

    describe('Card Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCard, MockCardReply, MockBoard, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCard = jasmine.createSpy('MockCard');
            MockCardReply = jasmine.createSpy('MockCardReply');
            MockBoard = jasmine.createSpy('MockBoard');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Card': MockCard,
                'CardReply': MockCardReply,
                'Board': MockBoard,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("CardDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'diesliApp:cardUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
