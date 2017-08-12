
/*global define */

'use strict';

define(['angular', './review.module', './product-select.directive', './product-details.directive'
    , '../utility/beverage-utility', './product-dao'], function (angular) {
    angular.module('beverage.review').controller('showProductCtrl', ['$scope', 'beverage-utility', 'productDao','reviewDao',
        function ($scope, util, productDao, reviewDao) {
            var vm = this;
            
            vm.getRate = getRate;//TODO this code is duuplicated, refactory
            
         
            $scope.$on('productSelected', function (event, review) {
                productDao.getProduct(review.productId)
                        .then(selectProduct)
                        .then(util.givePositiveFeedback(), util.displayErrorInformation('Could not get the beverage details.'));
                
                reviewDao.getReviewForTheProduct(review.productId).then(function(reviews) {
                    vm.reviews = reviews;
                });
            });
           
            function selectProduct(product) {
                vm.selectedProduct = product;
                return product;
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

        }]);

});



