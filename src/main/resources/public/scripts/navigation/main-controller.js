
/*global define */

'use strict';

define(['angular', './navigation.module', './authentication-service'], function (angular, module) {
    module.controller('main', ['$rootScope' ,'$scope', '$location' , '$http', 'authentication',
        function ($rootScope, $scope, $location, $http, authentication) {

            var vm = this;
            vm.login = login;
            vm.logout = logout;
            vm.goToWelcome = goToWelcome;

            activate();

            function activate() {
                authentication.isAuthenticated().then(function (result) {
                    $rootScope.$broadcast("authenticated", result);
                    if (result === true) {
                        $location.path('/home');
                    } else {
                        goToWelcome();
                    }
                });
            }
            
            function goToWelcome(){
                $location.path("/welcome");
            }
            
            $scope.$on('authenticated', function (event, data) {
                vm.isAuthenticated = data;
                if(vm.isAuthenticated) {
                    authentication.loggedInUser().then(function(result) {
                        vm.loggedInUser = result.data.username;
                    });
                }
            });
            
            function login() {
                $location.path('/login');
            }
            
            function logout() {
                $http.post('logout', {}).then(function () {
                    $rootScope.$broadcast("authenticated", false);
                    $location.path("/welcome");
                });
            }




        }]);

});



