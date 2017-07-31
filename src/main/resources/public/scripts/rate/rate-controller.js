
/*global define */

'use strict';

define(['angular', './rate.module', './product-select.directive', './product-details.directive'], function (angular) {
    angular.module('beverage.rate').controller('rateCtrl', ['$scope', '$http',
        function ($scope, $http) {
            var vm = this;

            vm.beverages = [];
            vm.rate = {};
            vm.showBeverageDetails = showBeverageDetails;
            vm.rateIt = rateIt;
            vm.deleteRate = deleteRate;
            vm.updateRate = updateRate;
            vm.currentPage = 0;
            vm.rates = [];

            activate();

            ////////////////////////////////////private

            vm.loadingRates = false;
            vm.allLoaded = false;

            function getRates(page) {
                vm.loadingRates = true;
                return $http.get('/v1/rate', {
                    params: {page: page}
                }).then(function (result) {
                    return result.data;
                }, function () {
                    
                });
            }

            vm.loadMore = function () {
                console.log("Call to load more");
                if(vm.allLoaded || vm.loadingRates) return;
                console.log("load more!!!!!!");
                vm.currentPage += 1;
                getRates(vm.currentPage).then(function(rates) {
                   vm.rates = vm.rates.concat(rates); 
                   if(rates.length === 0) vm.allLoaded = true;
                   vm.loadingRates = false;
                });
            };


            function activate() {
                getRates(0).then(refreshRates);
            }

            function createRate(_rate) {
                var rate = {
                    description: _rate.description,
                    rate: _rate.score,
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

            function showBeverageDetails(rate) {
                vm.rate.product = rate.product;
                vm.activeTab = 2;
            }

            function rateIt() {
                createRate(vm.rate).then(getRates).then(refreshRates).then(selectTab(1));
            }

            function removeRate(index) {
                return function() {
                    vm.rates.splice(index,1);
                }
            }

            function deleteRate(rate, index) {
                console.log(index);
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



