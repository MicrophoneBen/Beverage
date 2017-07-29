/*global require, requirejs */

'use strict';


define(['angular', 'ui_select', 'angular-sanitize', 'angular-animate','./rate/rate-controller', './navigation/login-controller', './navigation/main-controller'
    , './navigation/welcome-controller', 'angular-ui-bootstrap', 'infinit-scrolling',
    './rate/product-select.directive', './rate/product-details.directive', 'angular-route'],
        function (angular) {
            var app = angular.module('beverage', ['ngSanitize', 'ngAnimate','ui.select', 'beverage.rate', 'beverage.navigation', 'ui.bootstrap', 'ui-select-infinity', 'ngRoute']);

            app.config(function ($routeProvider, $httpProvider) {

                $routeProvider.when('/home', {
                    templateUrl: 'rate.html',
                    controller: 'rateCtrl'
                }).when('/login', {
                    templateUrl: 'login.html',
                    controller: 'login'
                }).when('/welcome', {
                    templateUrl: 'welcome.html',
                    controller: 'welcome'
                }).otherwise('/welcome');

                $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
            });

            var $html = angular.element(document.getElementsByTagName('html')[0]);

            angular.element().ready(function () {
                $html.addClass('ng-app');
                angular.bootstrap($html, [app['name']]);
            });
        });


