
/*global define */

'use strict';

define(['angular', './navigation.module'], function (angular) {
    angular.module('beverage.navigation').controller('navigation', ['$scope', '$http', '$location',
        function ($scope, $http, $location) {
            function isAuthenticated(credentials) {

                var headers = credentials ? {authorization: "Basic "
                            + btoa(credentials.username + ":" + credentials.password)
                } : {};

                return $http.get('v1/user', {headers: headers}).then(function (result) {
                    return true;
                }, function () {
                    return false;
                });
            }

            function route(result) {
                if (result === true)
                    $location.path('/home');
                else
                    $location.path('/login');
                return result;
            }

            function setErrorStatus(result) {
                $scope.error = !result;
                return result;
            }


            $scope.login = function (credentials) {
                $scope.error = false;
                return isAuthenticated(credentials).then(setErrorStatus).then(route);
            }

            isAuthenticated().then(route);



        }]);

});



