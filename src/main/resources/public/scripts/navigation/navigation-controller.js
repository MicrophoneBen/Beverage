
/*global define */

'use strict';

define(['angular', './navigation.module'], function (angular) {
    angular.module('beverage.navigation').controller('navigation', ['$scope', '$http', '$location',
        function ($scope, $http, $location) {
            function isAuthenticated(credentials) {

                $scope.signIn = true;

                var headers = credentials ? {authorization: "Basic "
                            + btoa(credentials.username + ":" + credentials.password)
                } : {};

                return $http.get('user', {headers: headers}).then(function (result) {
                    return true;
                }, function () {
                    return false;
                });
            }

            function route(result) {
                if (result === true) {
                    $location.path('/home');
                    $scope.authenticated = true;
                } else {
                    $location.path('/login');
                }
                return result;
            }

            function setErrorStatus(result) {
                $scope.error = !result;
                return result;
            }


            $scope.login = function (credentials) {
                $scope.error = false;
                return isAuthenticated(credentials).then(setErrorStatus).then(route);
            };

            isAuthenticated().then(route);

            $scope.createUser = function (userDetails) {
                $scope.error = false;
                return $http.post('/v1/user', userDetails).then(function () {
                    $scope.signIn = true;
                    $scope.signUp = false;
                }, function () {
                    $scope.error = true;
                });
            };

            $scope.logout = function () {
                $http.post('logout', {}).then(function () {
                    $location.path("/login");
                }).error(function (data) {
                    
                });
            }





        }]);

});



