
/*global define */

'use strict';

define(['angular', './navigation.module', , './authentication-service'], function (angular, module) {
    module.controller('login', ['$rootScope','$scope', '$http', '$location','authentication','beverage-utility','toastr',
        function ($rootScope,$scope, $http, $location, authentication, util,toastr) {
                    
            activate();
            
            function activate() {
                $scope.signIn = true;
            }
            
            $scope.createUser = function (userDetails) {
                return $http.post('/v1/user', userDetails)
                        .then(util.givePositiveFeedback("User created."),util.displayErrorInformation('Could not create user.'));
            };   
            
            $scope.login = function (credentials) {
                return authentication
                        .isAuthenticated(credentials)
                        .then(setErrorStatus)
                        .then(broadcast).then(route);
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



