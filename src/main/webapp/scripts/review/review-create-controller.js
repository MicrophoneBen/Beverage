
/*global define */

'use strict';

define(['./review.module', './product-select.directive',
    , '../utility/beverage-utility', './review-dao'], function (module) {
    module.controller('createReviewCtrl', ['$rootScope','beverage-utility','reviewDao',
        function ($rootScope, util, reviewDao) {
            var vm = this;
            vm.createReview = createReview;   
            
            activate();
            
            function activate() {

            }
            
            function notifyThatAReviewWasCreated() {
                $rootScope.$broadcast("reviewCreated");
            }

            function createReview() {
                vm.review.productId = vm.productToRate.productId;
                reviewDao.createReview(vm.review)
                        .then(notifyThatAReviewWasCreated)
                        .then(util.givePositiveFeedback('Beverage reviewed!'), util.displayErrorInformation('Could not review the beverage.'));
            }


        }]);

});



