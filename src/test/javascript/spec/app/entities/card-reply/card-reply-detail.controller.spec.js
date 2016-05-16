'use strict';

describe('Controller Tests', function() {

    describe('CardReply Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCardReply, MockCard;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCardReply = jasmine.createSpy('MockCardReply');
            MockCard = jasmine.createSpy('MockCard');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'CardReply': MockCardReply,
                'Card': MockCard
            };
            createController = function() {
                $injector.get('$controller')("CardReplyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'diesliApp:cardReplyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
