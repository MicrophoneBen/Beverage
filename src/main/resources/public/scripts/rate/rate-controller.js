
/*global define */

'use strict';

define(['angular', './rate.module', './product-select.directive', './product-details.directive'], function (angular) {
    angular.module('beverage.rate').controller('rateCtrl', ['$http', '$timeout',
        function ($http, $timeout) {
            var vm = this;

            vm.beverages = [];
            vm.rate = {};
            vm.showBeverageDetails = showBeverageDetails;
            vm.rateIt = rateIt;
            vm.deleteRate = deleteRate;
            vm.updateRate = updateRate;
            vm.filterRates = filterRates;
            vm.currentPage = 0;
            vm.rates = [];

            activate();

            ////////////////////////////////////private

            vm.loadingRates = false;
            vm.allLoaded = false;

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
                            throttled = false;
                            if (currentArguments) {
                                var args = Array.prototype.slice.call(currentArguments, 0);
                                currentArguments = undefined;
                                return fun.apply(this, args);
                            }
                        }, timeout);

                        var args = Array.prototype.slice.call(currentArguments, 0);
                        currentArguments = undefined;
                        return fun.apply(this, args);
                    }
                };
            }

            function getRates() {
                vm.loadingRates = true;
                return $http.get('/v1/rate', {
                    params: {
                        page: vm.currentPage,
                        query: vm.queryString
                    }
                }).then(function (result) {
                    vm.loadingRates = false;
                    return result.data;
                }, function () {
                    vm.loadingRates = false;
                });
            }

            vm.loadMore = function () {
                if (vm.allLoaded || vm.loadingRates) {
                    return;
                }

                vm.currentPage += 1;
                getRates().then(function (rates) {
                    //vm.rates = vm.rates.concat(rates); 
                    for (var i = 0; i < rates.length; i++) {
                        vm.rates.push(rates[i]);
                    }

                    if (rates.length === 0) {
                        vm.allLoaded = true;
                    }
                });
            };


            function activate() {
                getRates().then(refreshRates);
            }

            function createRate(_rate) {
                var rate = {
                    description: _rate.description,
                    rate: _rate.score?_rate.score:0,
                    productId: _rate.product.productId
                };
                return $http.post('/v1/rate', rate);
            }


            function refreshRates(rates) {
                vm.rates = rates;
            }

            function selectTab(tab) {
                return function () {
                    vm.activeTab = tab;
                };
            }

            ////////////////////////////////public

            function filterRates() {
                vm.currentPage = 0;
                vm.allLoaded = false;
                getRates().then(refreshRates);
            }

            function showBeverageDetails(rate) {
                $http.get('/v1/product_catalog/' + rate.productId).then(function(result) {
                    vm.rate.product = result.data;
                    vm.activeTab = 2;
                });
                
            }

            function rateIt() {
                vm.currentPage = 0;
                vm.allLoaded = false;
                createRate(vm.rate).then(getRates).then(refreshRates).then(selectTab(1));
            }

            function removeRate(index) {
                return function () {
                    vm.rates.splice(index, 1);
                };
            }

            function deleteRate(rate, index) {
                $http.delete('/v1/rate/' + rate.rateId).then(removeRate(index));
            }

            function updateRate(_rate, index) {
                var rate = {
                    rateId: _rate.rateId,
                    description: _rate.description,
                    rate: _rate.rate,
                    productId: _rate.product.productId
                };
                $http.put('/v1/rate/' + _rate.rateId, rate);
            }



        }]);

});



