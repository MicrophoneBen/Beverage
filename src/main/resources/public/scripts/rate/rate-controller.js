
/*global define */

'use strict';

define(['angular', './rate.module'], function (angular) {
    angular.module('beverage.rate').controller('rateCtrl', ['$scope', '$http',
        function ($scope, $http) {
            var vm = this;

            vm.beverages = [];
            vm.rate = {};

            //var windowWidth = $(window).innerWidth();
            vm.getDisplayName = function (beverage) {
                if (!beverage)
                    return "";
                return beverage.name + ', ' + beverage.producer + ',' + beverage.originCountry + (beverage.vintage ? (', ' + beverage.vintage) : "");
            };

            vm.rateIt = function () {
                console.log(vm.rate);
            };

            vm.page = 0;

            vm.getBeverages = function ($select, $event) {
                if (vm.loading) {
                    return;
                }
                // no event means first load!
                if (!$event) {
                    vm.beverages = [];
                    vm.page = 0;
                } else {;
                    vm.page += 1;
                }

                vm.loading = true;
                $http({
                    method: 'GET',
                    url: '/v1/product_catalog',
                    params: {
                        query: $select.search,
                        page: vm.page
                    }
                }).then(function (resp) {
                    vm.beverages = vm.beverages.concat(resp.data);
                })['finally'](function () {
                    vm.loading = false;
                });
            };

        }]);

});



