
/*global define */

'use strict';

define(['angular', './rate.module', 'rate/product-select.directive'], function (angular) {
    angular.module('beverage.rate').controller('rateCtrl', ['$scope', '$http',
        function ($scope, $http) {
            var vm = this;

            vm.beverages = [];
            vm.rate = {};
            vm.getDisplayName = getDisplayName;
            vm.showBeverageDetails = showBeverageDetails;
            vm.rateIt = rateIt;
            vm.getBeverages = getBeverages;
            vm.deleteRate = deleteRate;
            vm.saveRate = saveRate;
            
            activate();
    
            ////////////////////////////////////private
            function getRates() {
                return $http.get('/v1/rate').then(function(result) {
                    return result.data;
                });
            } 
            
            function activate() {
                getRates().then(refreshRates); 
            }
            
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
            
            ////////////////////////////////public
            
            function showBeverageDetails(rate) {
                console.log(rate);
                vm.selectedRate = rate;
            }
            
            //var windowWidth = $(window).innerWidth();
            function getDisplayName(beverage) {
                if (!beverage)
                    return "";
                return beverage.name + ', ' + beverage.producer + ',' + beverage.originCountry + (beverage.vintage ? (', ' + beverage.vintage) : "");
            };



            function rateIt() {
                createRate(vm.rate).then(getRates).then(refreshRates);   
            };
            
            function deleteRate(rate) {
                $http.delete('/v1/rate/' + rate.rateId).then(getRates).then(refreshRates);
            }
            
            function saveRate(_rate) {
                var rate = {
                    rateId: _rate.rateId,
                    description: _rate.description,
                    rate: _rate.rate,
                    productId: _rate.product.productId
                };
                console.log(_rate);
                $http.put('/v1/rate/' + _rate.rateId,rate).then(getRates).then(refreshRates);
            }

            vm.page = 0;

            function getBeverages($select, $event) {
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



