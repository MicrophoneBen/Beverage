
/*global define */

'use strict';

define(['angular', './review.module', './product-select.directive', './product-details.directive'
    , '../utility/beverage-utility', './product-dao'], function (angular) {
    angular.module('beverage.review').controller('showProductCtrl', ['$scope', 'beverage-utility', 'productDao','reviewDao',
        function ($scope, util, productDao, reviewDao) {
            var vm = this;
            
            vm.getRate = getRate;//TODO this code is duuplicated, refactory
            vm.loadMore = loadMore;
            
            vm.loadingReview = false;
            var allLoaded = false;
            var currentPage = 0;
            
         
            $scope.$on('productSelected', function (event, review) {
                allLoaded = false;
                currentPage = 0;
                productDao.getProduct(review.productId)
                        .then(selectProduct)
                        .then(util.givePositiveFeedback(), util.displayErrorInformation('Could not get the beverage details.'));
                
                reviewDao.getReviewForTheProduct(review.productId).then(setReviews);
            });
           
            function selectProduct(product) {
                vm.selectedProduct = product;
                return product;
            }
            
            function setReviews(reviews) {
                vm.reviews = reviews;
            }
            
            function getRate(review) {
                if(review.rate > 7) {
                    return "good";
                } else if(review.rate > 3) {
                    return "medium";
                } else {
                    return "bad";
                }
            }
            
            function loadMore() {
                if (allLoaded || vm.loadingReview || !vm.selectedProduct) {
                    return;
                }

                vm.loadingReview = true;
                currentPage += 1;
                reviewDao.getReviewForTheProduct(vm.selectedProduct.productId, currentPage)
                        .then(checkIfAllLoaded)
                        .then(addReviewsToList)
                        .finally(isNotLoadingReviews)
                        .then(util.givePositiveFeedback(), util.displayErrorInformation('Could not load the reviews for the product.'));  
            }
            
                        
            function checkIfAllLoaded(reviews) {
                allLoaded = (reviews.length === 0);
                return reviews;
            }

            function addReviewsToList(reviews) {
                //vm.rates = vm.rates.concat(rates); 
                for (var i = 0; i < reviews.length; i++) {
                    vm.reviews.push(reviews[i]);
                }
            }
            
            function isNotLoadingReviews() {
                vm.loadingReview = false;
            } 

        }]);

});



