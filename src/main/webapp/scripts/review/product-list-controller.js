
/*global define */

'use strict';

define(['./review.module', './product-select.directive',
    , '../utility/beverage-utility', './product-dao','ng-infinite-scroll'], function (module) {
    module.controller('listProductCtrl', ['$rootScope','beverage-utility','productDao',
        function ($rootScope, util, productDao) {
            var vm = this;
            vm.products = [];
            vm.filterProducts = util.throttle(filterProducts, 300);  ;
            vm.productSelected = productSelected;
            vm.loadMore = loadMore;
            vm.getRate = getRate;
            
            vm.loadingProducts = false;
            var allLoaded = false;
            var currentPage = 0;
                  
            
            activate();
            
            function activate() {
                currentPage = 0;
                allLoaded = false;
                productDao.getProducts(0,'')
                        .then(setProducts)
                        .then(util.givePositiveFeedback(), util.displayErrorInformation('Could not load the products.')); ;
            }
            
            
            function filterProducts() {
                currentPage = 0;
                allLoaded = false;
                productDao.getProducts(0,vm.queryString)
                        .then(setProducts)
                        .then(util.givePositiveFeedback(), util.displayErrorInformation('Could not load the products.')); ;
            }
            
            function setProducts(products) {
                vm.products = products;
            }
            
            
            function productSelected(product) {
                $rootScope.$broadcast("productSelected", product);
            }
            
            function loadMore() {
                if (allLoaded || vm.loadingProducts) {
                    return;
                }

                vm.loadingProducts = true;
                currentPage += 1;
                productDao.getProducts(currentPage,vm.queryString?vm.queryString:'')
                        .then(checkIfAllLoaded)
                        .then(addProductsToList)
                        .finally(isNotLoadingProducts)
                        .then(util.givePositiveFeedback(), util.displayErrorInformation('Could not load the products.'));                
            }
            
            function checkIfAllLoaded(products) {
                if (products.length === 0) {
                    allLoaded = true;
                } else {
                    allLoaded = false;
                }
                return products;
            }

            function addProductsToList(products) {
                //vm.rates = vm.rates.concat(rates); 
                for (var i = 0; i < products.length; i++) {
                    vm.products.push(products[i]);
                }
            }
            
            function isNotLoadingProducts() {
                vm.loadingProducts = false;
            }            

            //TODO dupplicated code refactory needed
            function getRate(rate) {
                if(!rate) return "unrated";
                if(rate > 7) {
                    return "good";
                } else if(rate > 3) {
                    return "medium";
                } else {
                    return "bad";
                }
            }

        }]);

});



