/*global require, requirejs */

'use strict';
requirejs.config({
    paths: {
        'infinit-scrolling': ['./lib/infinit-scrolling'],
        'ui_select': ['../webjars/angular-ui-select/select'],
        'angular-ui-bootstrap': ['../webjars/angular-ui-bootstrap/ui-bootstrap-tpls'],
        'ng-infinite-scroll': ['./lib/ng-infinite-scroll']
    },
    shim: {
        'ui_select': {
            exports: 'ui_select',
            deps: ['angular','angular-sanitize']
        },'infinit-scrolling': {
            exports: 'infinit-scrolling',
            deps: ['ui_select']
        },
        'angular-ui-bootstrap': {
            exports: 'angular-ui-bootstrap',
            deps: ['angular']
        },
        'ng-infinite-scroll': {
            exports: 'ng-infinite-scroll',
            deps: ['angular']
        }
    }
});

require(['angular', 'jquery', './beverage'], function () {});  


