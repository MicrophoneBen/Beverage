
/*global define */

'use strict';

define(['angular', './rate.module'], function (angular) {
    angular.module('beverage.rate').controller('rateCtrl', ['$scope', '$http',
        function ($scope, $http) {
            var vm = this;

            vm.beverages = [];
            vm.rate = {};
            
            activate();
    
            function getRates() {
                return $http.get('/v1/rate').then(function(result) {
                    return result.data;
                });
            }            
            
            function activate() {
                getRates().then(refreshRates); 
            }

            //var windowWidth = $(window).innerWidth();
            vm.getDisplayName = function (beverage) {
                if (!beverage)
                    return "";
                return beverage.name + ', ' + beverage.producer + ',' + beverage.originCountry + (beverage.vintage ? (', ' + beverage.vintage) : "");
            };

            function createRate(_rate) {
                console.log(_rate);
                var rate = {
                    description: _rate.description,
                    rate: _rate.score,
                    productId: _rate.selected.productId
                };
                return $http.post('/v1/rate',rate);
            }
            


            function refreshRates(rates) {
                vm.rates = rates;
            }

            vm.rateIt = function () {
                createRate(vm.rate).then(getRates).then(refreshRates);   
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



