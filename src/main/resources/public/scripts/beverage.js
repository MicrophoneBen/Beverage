/*global require, requirejs */

'use strict';


define(['angular','./rate/rate-controller'],
        function (angular) {
            var app = angular.module('beverage', ['beverage.rate']);

            var $html = angular.element(document.getElementsByTagName('html')[0]);

            angular.element().ready(function () {
                $html.addClass('ng-app');
                angular.bootstrap($html, [app['name']]);
            });
        });


