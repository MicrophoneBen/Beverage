
/*global define */

'use strict';

define(['angular', './rate.module', './product-select.directive', './product-details.directive'
    , '../utility/beverage-utility', './rate-dao', './product-dao'], function (angular) {
    angular.module('beverage.rate').controller('rateCtrl', ['beverage-utility','rateDao','productDao',
        function (util, rateDao, productDao) {
            var vm = this;

            vm.beverages = [];
            vm.rate = {};
            vm.showBeverageDetails = showBeverageDetails;
            vm.createRate = createRate;
            vm.deleteRate = deleteRate;
            vm.updateRate = updateRate;
            vm.filterRates = util.throttle(filterRates, 300);       
            vm.rates = [];
            vm.loadingRates = false;
            
            var allLoaded = false;
            var currentPage = 0;
            
            activate();
            

            vm.loadMore = function () {
                if (allLoaded || vm.loadingRates) {
                    return;
                }

                vm.loadingRates = true;
                currentPage += 1;
                rateDao.getRatesWithPageAndQuery(currentPage, vm.queryString)
                        .then(checkIfAllLoaded)
                        .then(addRatesToList)
                        .finally(isNotLoadingRates);
            };

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
            
            function isNotLoadingRates() {
                vm.loadingRates = false;
            }

            function activate() {
                rateDao.getRates()
                        .then(refreshRates)
                        .then(util.givePositiveFeedback(), util.displayErrorInformation('Could not load the reviews.'));
            }


            function refreshRates(rates) {
                vm.rates = rates;
            }

            function filterRates() {
                currentPage = 0;
                allLoaded = false;
                rateDao.getRatesWithPageAndQuery(currentPage, vm.queryString).then(refreshRates);
            }

            function showBeverageDetails(rate) {
                productDao.getProduct(rate.productId)
                        .then(selectProduct)
                        .then(selectTab(2))
                        .then(util.givePositiveFeedback(), util.displayErrorInformation('Could not get the beverage details.'));

            }
            
            function selectProduct(product) {
                vm.selectedProduct = product;
                return product;
            }

            function selectTab(tab) {
                return function () {
                    vm.activeTab = tab;
                };
            }

            function createRate() {
                currentPage = 0;
                allLoaded = false;
                vm.rate.productId = vm.productToRate.productId;
                rateDao.createRate(vm.rate)
                        .then(rateDao.getRates)
                        .then(refreshRates)
                        .then(selectTab(1))
                        .then(util.givePositiveFeedback('Beverage reviewed!'), util.displayErrorInformation('Could not review the beverage.'));
            }

            function deleteRate(rate, index) {
                rateDao.deleteRate(rate.rateId)
                        .then(removeRate(index))
                        .then(util.givePositiveFeedback('Review deleted.'), util.displayErrorInformation('Could not delete the review.'));
            }
            
            function removeRate(index) {
                return function () {
                    vm.rates.splice(index, 1);
                };
            }

            function updateRate(rate, index) {
                rateDao.updateRate(rate.rateId, rate)
                        .then(util.givePositiveFeedback('Review updated.'), util.displayErrorInformation('Could not update the review.'));
            }



        }]);

});



