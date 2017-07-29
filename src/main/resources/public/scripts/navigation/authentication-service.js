
/*global define */

'use strict';

define(['angular', './navigation.module'], function (angular, module) {
    module.service('authentication', ['$http',
        function ($http) {
            function loggedInUser(credentials) {
                var headers = credentials ? {authorization: "Basic "
                            + btoa(credentials.username + ":" + credentials.password)
                } : {};

                return $http.get('user', {headers: headers});
            }

            function isAuthenticated(credentials) {
                return loggedInUser(credentials).then(function (result) {
                    return true;
                }, function () {
                    return false;
                });
            }
            
            return {
                isAuthenticated: isAuthenticated,
                loggedInUser:loggedInUser
            };

        }]);

});



