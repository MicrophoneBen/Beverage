
/*global define */

'use strict';

define(['angular','./rate.module'], function (angular) {
    angular.module('beverage.rate').controller('rateCtrl', ['$scope',
        function ($scope) {
            $scope.hello="Hello World!";


        }]);    

});



