
/*global define */

'use strict';

define(['./utility.module'], function (module) {
    module.service('beverage-utility', ['$timeout',
        function ($timeout) {
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

            return {
                throttle: throttle
            };

        }]);

});






