/*global require, requirejs */

'use strict';

requirejs.config({
    paths: {
        'angular': ['../webjars/angularjs/1.6.4/angular'],
        'jquery': ['../webjars/jquery/2.1.1/jquery.min']
    },
    shim: {
        'angular': {
            exports: 'angular',
        },
        'jquery': {
            exports: 'jquery',
            deps: ['angular']
        }
    }
});

require(['angular', 'jquery','./rate/rate.module', './rate/rate-controller'],
        function (angular) {
            
            var app = angular.module('beverage', ['beverage.rate']);

            var $html = angular.element(document.getElementsByTagName('html')[0]);

            angular.element().ready(function () {
                $html.addClass('ng-app');
                angular.bootstrap($html, [app['name']]);
            });

        });


