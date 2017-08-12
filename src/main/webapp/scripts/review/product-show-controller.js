
/*global define */

'use strict';

define(['angular', './review.module', './product-select.directive', './product-details.directive'
    , '../utility/beverage-utility', './product-dao'], function (angular) {
    angular.module('beverage.review').controller('showProductCtrl', ['$scope', 'beverage-utility', 'productDao',
        function ($scope, util, productDao) {
            var vm = this;
         
            $scope.$on('productSelected', function (event, review) {
                productDao.getProduct(review.productId)
                        .then(selectProduct)
                        .then(util.givePositiveFeedback(), util.displayErrorInformation('Could not get the beverage details.'));
            });
           
            function selectProduct(product) {
                vm.selectedProduct = product;
                return product;
            }

        }]);

});



