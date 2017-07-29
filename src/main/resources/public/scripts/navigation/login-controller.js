
/*global define */

'use strict';

define(['angular', './navigation.module', , './authentication-service'], function (angular, module) {
    module.controller('login', ['$rootScope','$scope', '$http', '$location','authentication',
        function ($rootScope,$scope, $http, $location, authentication) {
                    
            activate();
            
            function activate() {
                $scope.signIn = true;
                $scope.signUp = false;
            }
            
            $scope.createUser = function (userDetails) {
                $scope.error = false;
                return $http.post('/v1/user', userDetails).then(function () {
                    $scope.signIn = true;
                    $scope.signUp = false;
                }, function () {
                    $scope.error = true;
                });
            };   
            
            $scope.login = function (credentials) {
                $scope.error = false;
                return authentication.isAuthenticated(credentials).then(setErrorStatus).then(broadcast).then(route);
            };

            function setErrorStatus(result) {
                $scope.error = !result;
                return result;
            }
            
            function broadcast(result) {
                $rootScope.$broadcast("authenticated", result);
                return result;
            }            

            function route(result) {
                if (result === true) {
                    $location.path('/home');                
                } else {
                    $location.path('/login');
                }
                return result;
            }

        }]);

});



