/*global require, requirejs */

'use strict';


define(['angular','ui_select','angular-sanitize','./rate/rate-controller','angular-ui-bootstrap', 'infinit-scrolling'],
        function (angular) {
            var app = angular.module('beverage', ['ngSanitize','ui.select','beverage.rate','ui.bootstrap','ui-select-infinity']);

            var $html = angular.element(document.getElementsByTagName('html')[0]);

            angular.element().ready(function () {
                $html.addClass('ng-app');
                angular.bootstrap($html, [app['name']]);
            });
        });


