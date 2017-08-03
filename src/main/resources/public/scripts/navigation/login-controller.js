
/*global define */

'use strict';

define(['angular', './navigation.module', , './authentication-service'], function (angular, module) {
    module.controller('login', ['$rootScope','$scope', '$http', '$location','authentication','toastr',
        function ($rootScope,$scope, $http, $location, authentication, toastr) {
                    
            activate();
            
            function activate() {
                $scope.signIn = true;
                $scope.signUp = false;
            }
            
            $scope.createUser = function (userDetails) {
                return $http.post('/v1/user', userDetails).then(function () {
                    toastr.success('User created, welcome!');
                    $scope.signIn = true;
                    $scope.signUp = false;
                }, function (error) {
                    if(error.status === 400) {
                        toastr.error('Could not create a user due to bad input.','Error.');
                    } else {
                        toastr.error('Because of unknown server error.','Error.');
                    }
                    
                });
            };   
            
            $scope.login = function (credentials) {
                return authentication.isAuthenticated(credentials).then(setErrorStatus).then(broadcast).then(route);
            };

            function setErrorStatus(result) {
                if(result === false) {
                    toastr.error('Could not log in, please try again.','Error');
                }             
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



