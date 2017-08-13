/*global require, requirejs */

'use strict';


define(['angular', 'ui_select', 'angular-sanitize', 'angular-animate','./review/main-controller', './navigation/login-controller', './navigation/app-controller'
    , './navigation/welcome-controller', 'angular-ui-bootstrap', 'infinit-scrolling', 'ng-infinite-scroll','toastr',
     'angular-route', './utility/utility.module'],
        function (angular) {
            var app = angular.module('beverage', ['ngSanitize', 'ngAnimate','ui.select', 'beverage.review', 'beverage.navigation', 
                'ui.bootstrap', 'ui-select-infinity', 'ngRoute', 'infinite-scroll', 'beverage.utility', 'toastr']);

            app.config(function ($routeProvider, $httpProvider) {

                $routeProvider.when('/home', {
                    templateUrl: '/scripts/review/main.html',
                    controller: 'mainCtrl'
                }).when('/login', {
                    templateUrl: '/scripts/navigation/login.html',
                    controller: 'login'
                }).when('/welcome', {
                    templateUrl: '/scripts/navigation/welcome.html',
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


