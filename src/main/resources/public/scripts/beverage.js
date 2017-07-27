/*global require, requirejs */

'use strict';


define(['angular', 'ui_select', 'angular-sanitize', './rate/rate-controller', 'angular-ui-bootstrap', 'infinit-scrolling',
    './rate/product-select.directive', './rate/product-details.directive', 'angular-route'],
        function (angular) {
            var app = angular.module('beverage', ['ngSanitize', 'ui.select', 'beverage.rate', 'ui.bootstrap', 'ui-select-infinity', 'ngRoute']);

            app.config(function ($routeProvider, $httpProvider) {

                $routeProvider.when('/home', {
                    templateUrl: 'home.html',
                    controller: 'rateCtrl'
                }).when('/login', {
                    templateUrl: 'login.html',
                    controller: 'navigation'
                }).otherwise('/login');

                $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
            })
                    .controller('navigation', function ($rootScope, $scope, $http, $location) {

                        var vm = this;

                        function isAuthenticated(credentials) {

                            var headers = credentials ? {authorization: "Basic "
                                        + btoa(credentials.username + ":" + credentials.password)
                            } : {};

                            return $http.get('v1/user', {headers: headers}).then(function (result) {
                                return true;
                            },function() {
                                return false;
                            });
                        }
                        
                        function route(result) {
                            if(result === true) $location.path('/home');
                            else $location.path('/login');
                            return result;
                        }
                        
                        function setErrorStatus(result) {
                            $scope.error = !result;
                            return result;
                        }
                       
 
                        $scope.login = function(credentials) {
                            $scope.error = false;
                            return isAuthenticated(credentials).then(setErrorStatus).then(route);
                        }
                        
                        isAuthenticated().then(route);

                    });

            var $html = angular.element(document.getElementsByTagName('html')[0]);

            angular.element().ready(function () {
                $html.addClass('ng-app');
                angular.bootstrap($html, [app['name']]);
            });
        });


