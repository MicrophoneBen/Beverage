/*global require, requirejs */

'use strict';
requirejs.config({
    paths: {
        'ui_select': ['../webjars/angular-ui-select/select']
    },
    shim: {
        'ui_select': {
            exports: 'ui_select',
            deps: ['angular','angular-sanitize']
        }
    }
});

require(['angular', 'jquery','ui_select','angular-sanitize', './beverage'], function () {});  


