
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
                    productId: _rate.product.productId
                };
                return $http.post('/v1/rate',rate);
            }
            


            function refreshRates(rates) {
                vm.rates = rates;
            }            
            
            ////////////////////////////////public
            
            function showBeverageDetails(rate) {
                vm.rate.product = rate.product;
                vm.activeTab = 2;
            }

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
          

            
        }]);

});



