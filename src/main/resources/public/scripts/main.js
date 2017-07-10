/*global require, requirejs */

'use strict';

requirejs.config({
  paths: {
    'angular': ['../webjars/angularjs/1.6.4/angular'],
    'jquery': ['../webjars/jquery/2.1.1/jquery.min']
  },
  shim: {
    'angular': {
	exports : 'angular',		
    },
    'jquery': {
        exports : 'jquery',
        deps: ['angular']
     }
  }
});

require(['angular','jquery'],
  function(angular) {


});


