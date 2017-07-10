
/*global define */

'use strict';

require(['angular'], function () {

    var controllers = angular.module('beverage.rate-controller', []);

    controllers.controller('rateCtrl', ['$scope',
        function ($scope) {
            $scope.hello="Hello World!";


        }]);


    return controllers;

});



