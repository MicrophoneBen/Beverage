
/*global define */

'use strict';

define(['angular', './rate.module', './product-select.directive', './product-details.directive', '../utility/beverage-utility'], function (angular) {
    angular.module('beverage.rate').controller('rateCtrl', ['$http','beverage-utility','toastr',
        function ($http, util, toastr) {
            var vm = this;

            vm.beverages = [];
            vm.rate = {};
            vm.showBeverageDetails = showBeverageDetails;
            vm.rateIt = rateIt;
            vm.deleteRate = deleteRate;
            vm.updateRate = updateRate;
            vm.filterRates = util.throttle(filterRates,500);
            vm.currentPage = 0;
            vm.rates = [];

            activate();

            ////////////////////////////////////private

            vm.loadingRates = false;
            vm.allLoaded = false;

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
                return $http.post('/v1/rate', rate).then(function(result) {
                   return result; 
                });
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
                createRate(vm.rate).then(getRates).then(refreshRates).then(selectTab(1)).then(function() {
                    toastr.success('Beverage rated!');
                },function(error) {
                    toastr.error('Could not rate the beverage.', 'Error');
                });
            }

            function removeRate(index) {
                return function () {
                    vm.rates.splice(index, 1);
                };
            }

            function deleteRate(rate, index) {
                $http.delete('/v1/rate/' + rate.rateId).then(removeRate(index)).then(function() {
                    toastr.success('Rate deleted.');
                }, function(error) {
                    toastr.error('Could not delete the rate.', 'Error.');
                });
            }

            function updateRate(_rate, index) {
                var rate = {
                    rateId: _rate.rateId,
                    description: _rate.description,
                    rate: _rate.rate,
                    productId: _rate.productId
                };
                $http.put('/v1/rate/' + _rate.rateId, rate).then(function() {
                    toastr.success('Rate updated.');
                }, function () {
                    toastr.error('Could not update the rate.', 'Error.');
                });
            }



        }]);

});



