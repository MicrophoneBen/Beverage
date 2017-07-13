/*global require, requirejs */

'use strict';
requirejs.config({
    paths: {
        'ui_select': ['../webjars/angular-ui-select/select'],
        'angular-ui-bootstrap': ['../webjars/angular-ui-bootstrap/ui-bootstrap-tpls']
    },
    shim: {
        'ui_select': {
            exports: 'ui_select',
            deps: ['angular','angular-sanitize']
        },
        'angular-ui-bootstrap': {
            exports: 'angular-ui-bootstrap',
            deps: ['angular']
        }
    }
});

require(['angular', 'jquery', './beverage'], function () {});  


