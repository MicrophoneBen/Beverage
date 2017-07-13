
/*global define */

'use strict';

define(['angular', './rate.module'], function (angular) {
    angular.module('beverage.rate').controller('rateCtrl', ['$scope', '$http',
        function ($scope, $http) {
            var vm = this;

            vm.rate = {};
            vm.refreshBeverages = function (query) {
                var params = {query: query};
                return $http.get(
                        '/v1/product_catalog',
                        {params: params}
                ).then(function (response) {
                    vm.beverages = response.data;
                });
            };

            vm.getDisplayName = function (beverage) {
                if(!beverage) return "";
                return beverage.name + ', ' + beverage.producer + ',' + beverage.originCountry + (beverage.vintage ? (', ' + beverage.vintage) : "");
            };
            
            vm.rate = function() {
                console.log("hepp");
                console.log(vm.rate);
            };

        }]);

});



