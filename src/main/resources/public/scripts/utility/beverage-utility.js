
/*global define */

'use strict';

define(['./utility.module'], function (module) {
    module.service('beverage-utility', ['$timeout','toastr',
        function ($timeout, toastr) {
            function throttle(fun, timeout) {

                var throttled = false;
                var currentArguments;

                return function (/*arguments*/) {
                    currentArguments = arguments;
                    if (throttled) {
                        return;
                    } else {
                        throttled = true;

                        $timeout(function () {
                            var args = Array.prototype.slice.call(currentArguments, 0);
                            fun.apply(this, args);
                            currentArguments = undefined;
                            throttled = false;
                        }, timeout);
                    }
                };
            }

            function curry(fun) {
                var numberOfParameters = fun.length;

                return function go(/*arguments*/) {
                    var args1 = Array.prototype.slice.call(arguments, 0);
                    if (args1.length >= numberOfParameters)
                        return fun.apply(this, args1);

                    return function f2(/*arguments*/) {
                        var args2 = Array.prototype.slice.call(arguments, 0);
                        return go.apply(this, args1.concat(args2));
                    };
                };
            }
            
            function givePositiveFeedback(message) {
                return function () {
                    if(message) toastr.success(message);      
                }; 
            }
            
            function displayErrorInformation(message) {
                return function(error) {
                   toastr.error(message, 'Error'); 
                };                
            }            

            return {
                throttle: throttle,
                curry: curry,
                givePositiveFeedback: givePositiveFeedback,
                displayErrorInformation: displayErrorInformation
            };

        }]);

});






