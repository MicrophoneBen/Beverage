
/*global define */

'use strict';

define(['angular', './rate.module', './product-select.directive', './product-details.directive', '../utility/beverage-utility'], function (angular) {
    angular.module('beverage.rate').controller('rateCtrl', ['$http', 'beverage-utility',
        function ($http, util) {
            var vm = this;

            vm.beverages = [];
            vm.rate = {};
            vm.showBeverageDetails = showBeverageDetails;
            vm.rateIt = rateIt;
            vm.deleteRate = deleteRate;
            vm.updateRate = updateRate;
            vm.filterRates = util.throttle(filterRates, 300);
            
            vm.rates = [];

            activate();


            vm.loadingRates = false;

            var allLoaded = false;
            var currentPage = 0;

            vm.loadMore = function () {
                if (allLoaded || vm.loadingRates) {
                    return;
                }

                currentPage += 1;
                getRates()
                        .then(checkIfAllLoaded)
                        .then(addRatesToList);
            };

            function getRates() {
                vm.loadingRates = true;
                return $http.get('/v1/rate', {
                    params: {
                        page: currentPage,
                        query: vm.queryString
                    }
                }).then(function (result) {
                    return result.data;
                }).finally(function () {
                    vm.loadingRates = false;
                });
            }

            function checkIfAllLoaded(rates) {
                if (rates.length === 0) {
                    allLoaded = true;
                } else {
                    allLoaded = false;
                }
                return rates;
            }

            function addRatesToList(rates) {
                //vm.rates = vm.rates.concat(rates); 
                for (var i = 0; i < rates.length; i++) {
                    vm.rates.push(rates[i]);
                }
            }

            function activate() {
                getRates()
                        .then(refreshRates)
                        .then(util.givePositiveFeedback(), util.displayErrorInformation('Could not load the rates.'));
            }


            function refreshRates(rates) {
                vm.rates = rates;
            }

            function selectTab(tab) {
                return function () {
                    vm.activeTab = tab;
                };
            }


            function filterRates() {
                currentPage = 0;
                allLoaded = false;
                getRates().then(refreshRates);
            }

            function showBeverageDetails(rate) {
                $http.get('/v1/product_catalog/' + rate.productId)
                        .then(function (result) {
                            vm.rate.product = result.data;
                        })
                        .then(selectTab(2))
                        .then(util.givePositiveFeedback(), util.displayErrorInformation('Could not get the beverage details.'));

            }

            function rateIt() {
                currentPage = 0;
                allLoaded = false;
                var rate = {
                    description: vm.rate.description,
                    rate: vm.rate.score ? vm.rate.score : 0,
                    productId: vm.rate.product.productId
                };
                $http.post('/v1/rate', rate)
                        .then(getRates)
                        .then(refreshRates)
                        .then(selectTab(1))
                        .then(util.givePositiveFeedback('Beverage rated!'), util.displayErrorInformation('Could not rate the beverage.'));
            }



            function removeRate(index) {
                return function () {
                    vm.rates.splice(index, 1);
                };
            }

            function deleteRate(rate, index) {
                $http.delete('/v1/rate/' + rate.rateId)
                        .then(removeRate(index))
                        .then(util.givePositiveFeedback('Rate deleted.'), util.displayErrorInformation('Could not delete the rate.'));
            }

            function updateRate(rate, index) {
                $http.put('/v1/rate/' + rate.rateId, rate)
                        .then(util.givePositiveFeedback('Rate updated.'), util.displayErrorInformation('Could not update the rate.'));
            }



        }]);

});



